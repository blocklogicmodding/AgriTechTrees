package com.blocklogic.agritechtrees.config;

import com.blocklogic.agritechtrees.util.RegistryHelper;
import com.mojang.logging.LogUtils;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.loading.FMLPaths;
import org.slf4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AgritechTreesOverrideConfig {
    private static final Logger MAIN_LOGGER = LogUtils.getLogger();
    private static org.apache.logging.log4j.Logger ERROR_LOGGER = null;
    private static boolean HAS_LOGGED_ERRORS = false;
    private static Path ERROR_LOG_PATH = null;
    private static final String OVERRIDE_FILE_NAME = "agritech_trees_config_overrides.toml";

    private static final Pattern TABLE_PATTERN = Pattern.compile("\\[(\\w+)\\.([\\w]+)\\]");
    private static final Pattern KEY_VALUE_PATTERN = Pattern.compile("(\\w+)\\s*=\\s*(.+)");
    private static final Pattern ARRAY_PATTERN = Pattern.compile("\\[\\s*(.*)\\s*\\]");
    private static final Pattern STRING_PATTERN = Pattern.compile("\"([^\"]*)\"");

    private static final Map<String, Integer> treeLineNumbers = new HashMap<>();
    private static final Map<String, Integer> soilLineNumbers = new HashMap<>();

    private static void setupErrorLogger() {
        ERROR_LOGGER = LogManager.getLogger(AgritechTreesOverrideConfig.class);
    }

    private static synchronized void createLogFileIfNeeded() {
        if (HAS_LOGGED_ERRORS) {
            return;
        }

        try {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            String logFileName = "agritechtrees_config_overrides_errors_" + timestamp + ".log";
            ERROR_LOG_PATH = FMLPaths.CONFIGDIR.get().resolve("agritechtrees").resolve("config_logs").resolve(logFileName);

            Files.createDirectories(ERROR_LOG_PATH.getParent());

            LoggerContext context = (LoggerContext) LogManager.getContext(false);
            Configuration config = context.getConfiguration();

            LoggerConfig existingLogger = config.getLoggerConfig("AgritechTreesOverrideErrorLogger");
            if (existingLogger != null) {
                existingLogger.getAppenders().forEach((name, appender) -> {
                    existingLogger.removeAppender(name);
                    appender.stop();
                });
                config.removeLogger("AgritechTreesOverrideErrorLogger");
            }

            PatternLayout layout = PatternLayout.newBuilder()
                    .withPattern("%d{yyyy-MM-dd HH:mm:ss} [%p] %m%n")
                    .build();

            FileAppender appender = FileAppender.newBuilder()
                    .setName("AgritechTreesOverrideErrorAppender")
                    .withFileName(ERROR_LOG_PATH.toString())
                    .setLayout(layout)
                    .setConfiguration(config)
                    .build();

            appender.start();

            config.addAppender(appender);

            LoggerConfig loggerConfig = new LoggerConfig("AgritechTreesOverrideErrorLogger",
                    org.apache.logging.log4j.Level.INFO, false);
            loggerConfig.addAppender(appender, org.apache.logging.log4j.Level.INFO, null);

            config.addLogger("AgritechTreesOverrideErrorLogger", loggerConfig);
            context.updateLoggers();

            ERROR_LOGGER = LogManager.getLogger("AgritechTreesOverrideErrorLogger");

            MAIN_LOGGER.info("Created override config error log file: {}", ERROR_LOG_PATH);
            HAS_LOGGED_ERRORS = true;
        } catch (Exception e) {
            MAIN_LOGGER.error("Failed to set up dedicated error logger: {}", e.getMessage());
        }
    }

    private static void logError(String message, Object... params) {
        createLogFileIfNeeded();
        ERROR_LOGGER.error(message, params);
    }

    private static void logWarning(String message, Object... params) {
        createLogFileIfNeeded();
        ERROR_LOGGER.warn(message, params);
    }

    public static void loadOverrides(Map<String, AgritechTreesConfig.TreeInfo> trees,
                                     Map<String, AgritechTreesConfig.SoilInfo> soils) {
        Path configDir = FMLPaths.CONFIGDIR.get().resolve("agritechtrees");
        Path overridePath = configDir.resolve(OVERRIDE_FILE_NAME);

        setupErrorLogger();

        if (!Files.exists(overridePath)) {
            createDefaultOverrideFile(configDir, overridePath);
        }

        try {
            MAIN_LOGGER.info("Loading tree and soil overrides from {}", overridePath);

            treeLineNumbers.clear();
            soilLineNumbers.clear();

            Map<String, Map<String, Map<String, Object>>> tables = parseTomlFile(overridePath);

            int treeCount = processTreeEntries(tables.getOrDefault("trees", Collections.emptyMap()), trees);

            int soilCount = processSoilEntries(tables.getOrDefault("soils", Collections.emptyMap()), soils);

            MAIN_LOGGER.info("Successfully loaded {} tree overrides and {} soil overrides", treeCount, soilCount);
        } catch (Exception e) {
            MAIN_LOGGER.error("Failed to load override.toml file: {}", e.getMessage());
            logError("Failed to load override.toml file: {}", e.getMessage());
            logError("The override file will be ignored, but the mod will continue to function");
        }
    }

    private static Map<String, Map<String, Map<String, Object>>> parseTomlFile(Path filePath) throws IOException {
        Map<String, Map<String, Map<String, Object>>> result = new HashMap<>();

        String currentSection = null;
        String currentTable = null;
        Map<String, Map<String, Object>> currentSectionMap = null;
        Map<String, Object> currentTableMap = null;

        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            StringBuilder multilineValue = null;
            String pendingKey = null;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                int commentPos = findUnquotedChar(line, '#');
                if (commentPos >= 0) {
                    line = line.substring(0, commentPos);
                }

                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                if (multilineValue != null) {
                    multilineValue.append(line);
                    if (countOccurrences(multilineValue.toString(), '[') == countOccurrences(multilineValue.toString(), ']') &&
                            countOccurrences(multilineValue.toString(), '{') == countOccurrences(multilineValue.toString(), '}')) {
                        currentTableMap.put(pendingKey, parseValue(multilineValue.toString()));
                        multilineValue = null;
                        pendingKey = null;
                    }
                    continue;
                }

                Matcher tableMatcher = TABLE_PATTERN.matcher(line);
                if (tableMatcher.matches()) {
                    currentSection = tableMatcher.group(1);
                    currentTable = tableMatcher.group(2);

                    if ("trees".equals(currentSection)) {
                        treeLineNumbers.put(currentTable, lineNumber);
                    } else if ("soils".equals(currentSection)) {
                        soilLineNumbers.put(currentTable, lineNumber);
                    }

                    currentSectionMap = result.computeIfAbsent(currentSection, k -> new HashMap<>());
                    currentTableMap = currentSectionMap.computeIfAbsent(currentTable, k -> new HashMap<>());
                    continue;
                }

                if (currentTableMap != null) {
                    Matcher keyValueMatcher = KEY_VALUE_PATTERN.matcher(line);
                    if (keyValueMatcher.matches()) {
                        String key = keyValueMatcher.group(1);
                        String valueStr = keyValueMatcher.group(2).trim();

                        if ((valueStr.startsWith("[") && !valueStr.endsWith("]")) ||
                                countOccurrences(valueStr, '[') != countOccurrences(valueStr, ']') ||
                                countOccurrences(valueStr, '{') != countOccurrences(valueStr, '}')) {
                            multilineValue = new StringBuilder(valueStr);
                            pendingKey = key;
                        } else {
                            Object value = parseValue(valueStr);
                            currentTableMap.put(key, value);
                        }
                    }
                }
            }
        }

        return result;
    }

    private static int findUnquotedChar(String str, char target) {
        boolean inQuotes = false;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == target && !inQuotes) {
                return i;
            }
        }
        return -1;
    }

    private static int countOccurrences(String str, char target) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == target) {
                count++;
            }
        }
        return count;
    }

    private static Object parseValue(String valueStr) {
        if (valueStr.startsWith("[") && valueStr.endsWith("]") && valueStr.contains("{")) {
            List<Map<String, Object>> items = new ArrayList<>();

            String content = valueStr.substring(1, valueStr.length() - 1).trim();

            int startIdx = 0;
            while (startIdx < content.length()) {
                int openBrace = content.indexOf('{', startIdx);
                if (openBrace == -1) break;

                int closeBrace = findMatchingCloseBrace(content, openBrace);
                if (closeBrace == -1) break;

                String objectStr = content.substring(openBrace + 1, closeBrace).trim();
                Map<String, Object> objectMap = parseObject(objectStr);
                items.add(objectMap);

                startIdx = closeBrace + 1;
            }

            return items;
        }

        if (valueStr.startsWith("[") && valueStr.endsWith("]")) {
            Matcher arrayMatcher = ARRAY_PATTERN.matcher(valueStr);
            if (arrayMatcher.matches()) {
                String arrayContent = arrayMatcher.group(1);
                List<String> items = new ArrayList<>();

                Matcher stringMatcher = STRING_PATTERN.matcher(arrayContent);
                while (stringMatcher.find()) {
                    items.add(stringMatcher.group(1));
                }

                return items;
            }
        }

        if (valueStr.startsWith("\"") && valueStr.endsWith("\"")) {
            return valueStr.substring(1, valueStr.length() - 1);
        }

        try {
            if (valueStr.contains(".")) {
                return Double.parseDouble(valueStr);
            } else {
                return Integer.parseInt(valueStr);
            }
        } catch (NumberFormatException ignored) {

        }

        if (valueStr.equalsIgnoreCase("true")) {
            return true;
        } else if (valueStr.equalsIgnoreCase("false")) {
            return false;
        }

        return valueStr;
    }

    private static int findMatchingCloseBrace(String content, int openBracePos) {
        int depth = 0;
        for (int i = openBracePos; i < content.length(); i++) {
            char c = content.charAt(i);
            if (c == '{') {
                depth++;
            } else if (c == '}') {
                depth--;
                if (depth == 0) {
                    return i;
                }
            }
        }
        return -1;
    }

    private static Map<String, Object> parseObject(String objectStr) {
        Map<String, Object> result = new HashMap<>();
        String[] parts = objectStr.split(",");

        for (String part : parts) {
            part = part.trim();
            if (part.isEmpty()) continue;

            String[] keyValue = part.split("=", 2);
            if (keyValue.length != 2) continue;

            String key = keyValue[0].trim();
            String valueStr = keyValue[1].trim();

            Object value;
            if (valueStr.startsWith("\"") && valueStr.endsWith("\"")) {
                value = valueStr.substring(1, valueStr.length() - 1);
            } else if (valueStr.equals("true")) {
                value = true;
            } else if (valueStr.equals("false")) {
                value = false;
            } else {
                try {
                    if (valueStr.contains(".")) {
                        value = Double.parseDouble(valueStr);
                    } else {
                        value = Integer.parseInt(valueStr);
                    }
                } catch (NumberFormatException e) {
                    value = valueStr;
                }
            }

            result.put(key, value);
        }

        return result;
    }

    private static int processTreeEntries(Map<String, Map<String, Object>> treeEntries,
                                          Map<String, AgritechTreesConfig.TreeInfo> trees) {
        int count = 0;

        for (Map.Entry<String, Map<String, Object>> entry : treeEntries.entrySet()) {
            String treeName = entry.getKey();
            Map<String, Object> treeConfig = entry.getValue();

            try {
                Object saplingObj = treeConfig.get("sapling");
                if (saplingObj == null) {
                    int lineNum = treeLineNumbers.getOrDefault(treeName, -1);
                    String lineInfo = lineNum > 0 ? " (line " + lineNum + ")" : "";
                    MAIN_LOGGER.warn("Tree override '{}'{} is missing a sapling ID, skipping", treeName, lineInfo);
                    logWarning("Tree override '{}'{} is missing a sapling ID, skipping", treeName, lineInfo);
                    continue;
                }

                String saplingId = saplingObj.toString();

                Item saplingItem = RegistryHelper.getItem(saplingId);
                if (saplingItem == null) {
                    int lineNum = treeLineNumbers.getOrDefault(treeName, -1);
                    String lineInfo = lineNum > 0 ? " (line " + lineNum + ")" : "";
                    MAIN_LOGGER.warn("Tree override '{}'{} uses non-existent sapling item: {}, skipping", treeName, lineInfo, saplingId);
                    logWarning("Tree override '{}'{} uses non-existent sapling item: {}, skipping", treeName, lineInfo, saplingId);
                    continue;
                }

                List<String> validSoils = new ArrayList<>();
                Object soilsObj = treeConfig.get("soil");
                if (soilsObj instanceof List<?> soilsList) {
                    for (Object soilObj : soilsList) {
                        String soilId = soilObj.toString();
                        Block soilBlock = RegistryHelper.getBlock(soilId);
                        if (soilBlock == null) {
                            int lineNum = treeLineNumbers.getOrDefault(treeName, -1);
                            String lineInfo = lineNum > 0 ? " (line " + lineNum + ")" : "";
                            MAIN_LOGGER.warn("Tree override '{}'{} references non-existent soil block: {}, skipping this soil",
                                    treeName, lineInfo, soilId);
                            logWarning("Tree override '{}'{} references non-existent soil block: {}, skipping this soil",
                                    treeName, lineInfo, soilId);
                            continue;
                        }
                        validSoils.add(soilId);
                    }
                }

                if (validSoils.isEmpty()) {
                    int lineNum = treeLineNumbers.getOrDefault(treeName, -1);
                    String lineInfo = lineNum > 0 ? " (line " + lineNum + ")" : "";
                    MAIN_LOGGER.warn("Tree override '{}'{} has no valid soils, skipping", treeName, lineInfo);
                    logWarning("Tree override '{}'{} has no valid soils, skipping", treeName, lineInfo);
                    continue;
                }

                List<AgritechTreesConfig.DropInfo> drops = new ArrayList<>();
                Object dropsObj = treeConfig.get("drops");
                if (dropsObj instanceof List<?> dropsList) {
                    int defaultMinCount = 1;
                    int defaultMaxCount = 1;
                    float defaultChance = 1.0f;

                    if (treeConfig.containsKey("min_count") && treeConfig.get("min_count") instanceof Number) {
                        defaultMinCount = ((Number) treeConfig.get("min_count")).intValue();
                    }
                    if (treeConfig.containsKey("max_count") && treeConfig.get("max_count") instanceof Number) {
                        defaultMaxCount = ((Number) treeConfig.get("max_count")).intValue();
                    }
                    if (treeConfig.containsKey("chance") && treeConfig.get("chance") instanceof Number) {
                        defaultChance = ((Number) treeConfig.get("chance")).floatValue();
                    }

                    for (Object dropObj : dropsList) {
                        String dropId;
                        int minCount = defaultMinCount;
                        int maxCount = defaultMaxCount;
                        float chance = defaultChance;

                        if (dropObj instanceof Map) {
                            @SuppressWarnings("unchecked")
                            Map<String, Object> dropMap = (Map<String, Object>) dropObj;

                            Object itemObj = dropMap.get("item");
                            if (itemObj == null) {
                                int lineNum = treeLineNumbers.getOrDefault(treeName, -1);
                                String lineInfo = lineNum > 0 ? " (line " + lineNum + ")" : "";
                                MAIN_LOGGER.warn("Tree override '{}'{} has drop without item ID, skipping", treeName, lineInfo);
                                logWarning("Tree override '{}'{} has drop without item ID, skipping", treeName, lineInfo);
                                continue;
                            }

                            dropId = itemObj.toString();

                            if (dropMap.containsKey("min_count") && dropMap.get("min_count") instanceof Number) {
                                minCount = ((Number) dropMap.get("min_count")).intValue();
                            }
                            if (dropMap.containsKey("max_count") && dropMap.get("max_count") instanceof Number) {
                                maxCount = ((Number) dropMap.get("max_count")).intValue();
                            }
                            if (dropMap.containsKey("chance") && dropMap.get("chance") instanceof Number) {
                                chance = ((Number) dropMap.get("chance")).floatValue();
                            }
                        }
                        else {
                            dropId = dropObj.toString();
                        }

                        Item dropItem = RegistryHelper.getItem(dropId);
                        if (dropItem == null) {
                            int lineNum = treeLineNumbers.getOrDefault(treeName, -1);
                            String lineInfo = lineNum > 0 ? " (line " + lineNum + ")" : "";
                            MAIN_LOGGER.warn("Tree override '{}'{} references non-existent drop item: {}, skipping this drop",
                                    treeName, lineInfo, dropId);
                            logWarning("Tree override '{}'{} references non-existent drop item: {}, skipping this drop",
                                    treeName, lineInfo, dropId);
                            continue;
                        }

                        drops.add(new AgritechTreesConfig.DropInfo(dropId, minCount, maxCount, chance));
                    }
                }

                if (drops.isEmpty()) {
                    int lineNum = treeLineNumbers.getOrDefault(treeName, -1);
                    String lineInfo = lineNum > 0 ? " (line " + lineNum + ")" : "";
                    MAIN_LOGGER.warn("Tree override '{}'{} has no valid drops, skipping", treeName, lineInfo);
                    logWarning("Tree override '{}'{} has no valid drops, skipping", treeName, lineInfo);
                    continue;
                }

                AgritechTreesConfig.TreeInfo treeInfo = new AgritechTreesConfig.TreeInfo();
                treeInfo.validSoils = validSoils;
                treeInfo.drops = drops;

                trees.put(saplingId, treeInfo);
                count++;
                MAIN_LOGGER.info("Added tree override for '{}' with sapling {}", treeName, saplingId);

            } catch (Exception e) {
                int lineNum = treeLineNumbers.getOrDefault(treeName, -1);
                String lineInfo = lineNum > 0 ? " (line " + lineNum + ")" : "";
                MAIN_LOGGER.error("Error processing tree override '{}'{}: {}", treeName, lineInfo, e.getMessage());
                logError("Error processing tree override '{}'{}: {}", treeName, lineInfo, e.getMessage());
            }
        }

        return count;
    }

    private static int processSoilEntries(Map<String, Map<String, Object>> soilEntries,
                                          Map<String, AgritechTreesConfig.SoilInfo> soils) {
        int count = 0;

        for (Map.Entry<String, Map<String, Object>> entry : soilEntries.entrySet()) {
            String soilName = entry.getKey();
            Map<String, Object> soilConfig = entry.getValue();

            try {
                Object blockObj = soilConfig.get("block");
                if (blockObj == null) {
                    int lineNum = soilLineNumbers.getOrDefault(soilName, -1);
                    String lineInfo = lineNum > 0 ? " (line " + lineNum + ")" : "";
                    MAIN_LOGGER.warn("Soil override '{}'{} is missing a block ID, skipping", soilName, lineInfo);
                    logWarning("Soil override '{}'{} is missing a block ID, skipping", soilName, lineInfo);
                    continue;
                }

                String soilId = blockObj.toString();

                Block soilBlock = RegistryHelper.getBlock(soilId);
                if (soilBlock == null) {
                    int lineNum = soilLineNumbers.getOrDefault(soilName, -1);
                    String lineInfo = lineNum > 0 ? " (line " + lineNum + ")" : "";
                    MAIN_LOGGER.warn("Soil override '{}'{} uses non-existent block: {}, skipping", soilName, lineInfo, soilId);
                    logWarning("Soil override '{}'{} uses non-existent block: {}, skipping", soilName, lineInfo, soilId);
                    continue;
                }

                float growthModifier = 1.0f;
                Object modifierObj = soilConfig.get("growth_modifier");
                if (modifierObj instanceof Number) {
                    growthModifier = ((Number) modifierObj).floatValue();
                }

                AgritechTreesConfig.SoilInfo soilInfo = new AgritechTreesConfig.SoilInfo(growthModifier);
                soils.put(soilId, soilInfo);
                count++;

                MAIN_LOGGER.info("Added soil override for '{}' with block {}", soilName, soilId);

            } catch (Exception e) {
                int lineNum = soilLineNumbers.getOrDefault(soilName, -1);
                String lineInfo = lineNum > 0 ? " (line " + lineNum + ")" : "";
                MAIN_LOGGER.error("Error processing soil override '{}'{}: {}", soilName, lineInfo, e.getMessage());
                logError("Error processing soil override '{}'{}: {}", soilName, lineInfo, e.getMessage());
            }
        }

        return count;
    }

    private static void createDefaultOverrideFile(Path configDir, Path overridePath) {
        try {
            if (!Files.exists(configDir)) {
                Files.createDirectories(configDir);
            }

            try (FileWriter writer = new FileWriter(overridePath.toFile())) {
                writer.write(createBasicTemplate());
            }

            MAIN_LOGGER.info("Created default override.toml file with examples at {}", overridePath);

        } catch (IOException e) {
            MAIN_LOGGER.error("Failed to create default override.toml file: {}", e.getMessage());
            if (HAS_LOGGED_ERRORS) {
                ERROR_LOGGER.error("Failed to create default override.toml file: {}", e.getMessage());
            }
        }
    }

    public static void resetErrorFlag() {
        HAS_LOGGED_ERRORS = false;
        ERROR_LOGGER = null;
        ERROR_LOG_PATH = null;
    }

    private static String createBasicTemplate() {
        return """
                # AgriTech Trees Override Configuration
                # This file allows you to add custom trees and soils without modifying the core configuration.
                # Any entries here will override existing configurations for the same items/blocks.
                
                # How to use:
                # 1. Add [trees.your_tree_name] sections for new trees or to override existing ones
                # 2. Add [soils.your_soil_name] sections for new soils or to override existing ones
                # 3. Save the file and restart your game
                
                # IMPORTANT: Make sure to verify the exact item and block IDs from your mods
                # Incorrect IDs will be skipped with a warning message in the log
                # The mod uses resource location format (e.g., "minecraft:dirt" not just "dirt")
                # The easiest way to check IDs is with F3+H enabled (shows tooltip IDs) or via JEI/REI
                
                # Example trees:
                
                # [trees.example_oak]
                # sapling = "examplemod:oak_sapling"
                # soil = [
                #   "minecraft:dirt",
                #   "examplemod:rich_soil"
                # ]
                # # There are two ways to specify drops:
                # # 1. Simple drops with default settings:
                # drops = [
                #   "examplemod:oak_log",
                #   "examplemod:oak_sapling"
                # ]
                # min_count = 1  # Minimum drop count for all simple drops (default: 1)
                # max_count = 3  # Maximum drop count for all simple drops (default: 1)
                # chance = 0.75  # Drop chance for all simple drops (default: 1.0)
                
                # # 2. Detailed drops with individual settings (use this for multiple drops with different chances):
                # # [trees.example_oak_advanced]
                # # sapling = "examplemod:oak_sapling"
                # # soil = ["minecraft:dirt"]
                # # drops = [
                # #   { item = "examplemod:oak_log", min_count = 2, max_count = 6, chance = 1.0 },
                # #   { item = "examplemod:oak_sapling", min_count = 1, max_count = 2, chance = 0.5 },
                # #   { item = "minecraft:apple", min_count = 1, max_count = 2, chance = 0.2 }
                # # ]
                
                # Example soils:
                
                # [soils.example_rich_soil]
                # block = "examplemod:rich_soil"
                # growth_modifier = 1.5  # Growth speed multiplier (default: 1.0)
                
                # REAL EXAMPLE - Uncomment to use
                # This example adds support for a mod's tree
                
                # [trees.modded_tree]
                # sapling = "awesomemod:magical_sapling"
                # soil = [
                #   "minecraft:dirt",
                #   "minecraft:grass_block",
                #   "minecraft:podzol"
                # ]
                # drops = [
                #   { item = "awesomemod:magical_log", min_count = 3, max_count = 7, chance = 1.0 },
                #   { item = "awesomemod:magical_sapling", min_count = 1, max_count = 2, chance = 0.5 },
                #   { item = "awesomemod:magical_fruit", min_count = 1, max_count = 3, chance = 0.4 }
                # ]
                
                # [soils.magical_soil]
                # block = "awesomemod:magical_soil"
                # growth_modifier = 1.75
                """;
    }
}