package com.blocklogic.agritechtrees.config;

import com.blocklogic.agritechtrees.Config;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import net.neoforged.fml.loading.FMLPaths;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class AgritechTreesConfig {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static Map<String, TreeInfo> trees = new HashMap<>();
    private static Map<String, SoilInfo> soils = new HashMap<>();

    public static void loadConfig() {
        LOGGER.info("AgritechTreesConfig.loadConfig() invoked.");
        Path configPath = FMLPaths.CONFIGDIR.get().resolve("agritech/saplings_and_soil.json");
        if (!Files.exists(configPath)) {
            createDefaultConfig(configPath);
        }

        try {
            String jsonString = Files.readString(configPath);
            TreeConfigData configData = GSON.fromJson(jsonString, TreeConfigData.class);
            processConfig(configData);
        } catch (IOException | JsonSyntaxException e) {
            LOGGER.error("Failed to load tree config file: {}", e.getMessage());
            LOGGER.info("Loading default tree configuration instead");
            processConfig(getDefaultConfig());
        }
    }

    private static void createDefaultConfig(Path configPath) {
        try {
            Files.createDirectories(configPath.getParent());
            TreeConfigData defaultConfig = getDefaultConfig();
            String json = GSON.toJson(defaultConfig);
            Files.writeString(configPath, json);
        } catch (IOException e) {
            LOGGER.error("Failed to create default tree config file: {}", e.getMessage());
        }
    }

    private static TreeConfigData getDefaultConfig() {
        LOGGER.info("Generating default tree config.");

        TreeConfigData config = new TreeConfigData();

        List<TreeEntry> defaultTrees = new ArrayList<>();

        addVanillaTrees(defaultTrees);

        if (Config.enableBiomesOPlenty) {
            LOGGER.info("Adding Biomes O' Plenty trees to AgriTech config");
            addBiomesOPlentyTrees(defaultTrees);
        }

        if (Config.enableTwilightForest) {
            LOGGER.info("Adding Twilight Forest trees to AgriTech config");
            addTwilightForestTrees(defaultTrees);
        }

        config.allowedTrees = defaultTrees;

        List<SoilEntry> defaultSoils = new ArrayList<>();
        addVanillaSoils(defaultSoils);

        config.allowedSoils = defaultSoils;

        return config;
    }

    private static void addVanillaTrees(List<TreeEntry> trees) {
        // Oak Sapling
        TreeEntry oak = new TreeEntry();
        oak.sapling = "minecraft:oak_sapling";
        oak.validSoils = List.of(
                "minecraft:dirt",
                "minecraft:grass_block",
                "minecraft:podzol",
                "minecraft:coarse_dirt",
                "minecraft:rooted_dirt",
                "minecraft:mud",
                "minecraft:moss_block"
        );
        oak.drops = new ArrayList<>();

        DropEntry oakLogDrop = new DropEntry();
        oakLogDrop.item = "minecraft:oak_log";
        oakLogDrop.count = new CountRange(3, 6);
        oak.drops.add(oakLogDrop);

        DropEntry oakSaplingDrop = new DropEntry();
        oakSaplingDrop.item = "minecraft:oak_sapling";
        oakSaplingDrop.count = new CountRange(1, 2);
        oakSaplingDrop.chance = 0.5f;
        oak.drops.add(oakSaplingDrop);

        DropEntry stickDrop = new DropEntry();
        stickDrop.item = "minecraft:stick";
        stickDrop.count = new CountRange(2, 4);
        stickDrop.chance = 0.8f;
        oak.drops.add(stickDrop);

        DropEntry appleDrop = new DropEntry();
        appleDrop.item = "minecraft:apple";
        appleDrop.count = new CountRange(1, 1);
        appleDrop.chance = 0.1f;
        oak.drops.add(appleDrop);

        trees.add(oak);

        // Birch Sapling
        TreeEntry birch = new TreeEntry();
        birch.sapling = "minecraft:birch_sapling";
        birch.validSoils = List.of(
                "minecraft:dirt",
                "minecraft:grass_block",
                "minecraft:podzol",
                "minecraft:coarse_dirt",
                "minecraft:rooted_dirt",
                "minecraft:mud",
                "minecraft:moss_block"
        );
        birch.drops = new ArrayList<>();

        DropEntry birchLogDrop = new DropEntry();
        birchLogDrop.item = "minecraft:birch_log";
        birchLogDrop.count = new CountRange(3, 6);
        birch.drops.add(birchLogDrop);

        DropEntry birchSaplingDrop = new DropEntry();
        birchSaplingDrop.item = "minecraft:birch_sapling";
        birchSaplingDrop.count = new CountRange(1, 2);
        birchSaplingDrop.chance = 0.5f;
        birch.drops.add(birchSaplingDrop);

        DropEntry birchStickDrop = new DropEntry();
        birchStickDrop.item = "minecraft:stick";
        birchStickDrop.count = new CountRange(2, 4);
        birchStickDrop.chance = 0.8f;
        birch.drops.add(birchStickDrop);

        trees.add(birch);

        // Spruce Sapling
        TreeEntry spruce = new TreeEntry();
        spruce.sapling = "minecraft:spruce_sapling";
        spruce.validSoils = List.of(
                "minecraft:dirt",
                "minecraft:grass_block",
                "minecraft:podzol",
                "minecraft:coarse_dirt",
                "minecraft:rooted_dirt",
                "minecraft:mud",
                "minecraft:moss_block"
        );
        spruce.drops = new ArrayList<>();

        DropEntry spruceLogDrop = new DropEntry();
        spruceLogDrop.item = "minecraft:spruce_log";
        spruceLogDrop.count = new CountRange(4, 8);
        spruce.drops.add(spruceLogDrop);

        DropEntry spruceSaplingDrop = new DropEntry();
        spruceSaplingDrop.item = "minecraft:spruce_sapling";
        spruceSaplingDrop.count = new CountRange(1, 2);
        spruceSaplingDrop.chance = 0.5f;
        spruce.drops.add(spruceSaplingDrop);

        DropEntry spruceStickDrop = new DropEntry();
        spruceStickDrop.item = "minecraft:stick";
        spruceStickDrop.count = new CountRange(2, 4);
        spruceStickDrop.chance = 0.8f;
        spruce.drops.add(spruceStickDrop);

        trees.add(spruce);

        // Jungle Sapling
        TreeEntry jungle = new TreeEntry();
        jungle.sapling = "minecraft:jungle_sapling";
        jungle.validSoils = List.of(
                "minecraft:dirt",
                "minecraft:grass_block",
                "minecraft:podzol",
                "minecraft:coarse_dirt",
                "minecraft:rooted_dirt",
                "minecraft:mud",
                "minecraft:moss_block"
        );
        jungle.drops = new ArrayList<>();

        DropEntry jungleLogDrop = new DropEntry();
        jungleLogDrop.item = "minecraft:jungle_log";
        jungleLogDrop.count = new CountRange(4, 7);
        jungle.drops.add(jungleLogDrop);

        DropEntry jungleSaplingDrop = new DropEntry();
        jungleSaplingDrop.item = "minecraft:jungle_sapling";
        jungleSaplingDrop.count = new CountRange(1, 2);
        jungleSaplingDrop.chance = 0.4f;
        jungle.drops.add(jungleSaplingDrop);

        DropEntry jungleStickDrop = new DropEntry();
        jungleStickDrop.item = "minecraft:stick";
        jungleStickDrop.count = new CountRange(2, 5);
        jungleStickDrop.chance = 0.8f;
        jungle.drops.add(jungleStickDrop);

        DropEntry cocoaBeanDrop = new DropEntry();
        cocoaBeanDrop.item = "minecraft:cocoa_beans";
        cocoaBeanDrop.count = new CountRange(1, 2);
        cocoaBeanDrop.chance = 0.2f;
        jungle.drops.add(cocoaBeanDrop);

        trees.add(jungle);

        // Acacia Sapling
        TreeEntry acacia = new TreeEntry();
        acacia.sapling = "minecraft:acacia_sapling";
        acacia.validSoils = List.of(
                "minecraft:dirt",
                "minecraft:grass_block",
                "minecraft:podzol",
                "minecraft:coarse_dirt",
                "minecraft:rooted_dirt",
                "minecraft:mud",
                "minecraft:moss_block",
                "minecraft:red_sand"
        );
        acacia.drops = new ArrayList<>();

        DropEntry acaciaLogDrop = new DropEntry();
        acaciaLogDrop.item = "minecraft:acacia_log";
        acaciaLogDrop.count = new CountRange(3, 6);
        acacia.drops.add(acaciaLogDrop);

        DropEntry acaciaSaplingDrop = new DropEntry();
        acaciaSaplingDrop.item = "minecraft:acacia_sapling";
        acaciaSaplingDrop.count = new CountRange(1, 2);
        acaciaSaplingDrop.chance = 0.5f;
        acacia.drops.add(acaciaSaplingDrop);

        DropEntry acaciaStickDrop = new DropEntry();
        acaciaStickDrop.item = "minecraft:stick";
        acaciaStickDrop.count = new CountRange(2, 4);
        acaciaStickDrop.chance = 0.8f;
        acacia.drops.add(acaciaStickDrop);

        trees.add(acacia);

        // Dark Oak Sapling
        TreeEntry darkOak = new TreeEntry();
        darkOak.sapling = "minecraft:dark_oak_sapling";
        darkOak.validSoils = List.of(
                "minecraft:dirt",
                "minecraft:grass_block",
                "minecraft:podzol",
                "minecraft:coarse_dirt",
                "minecraft:rooted_dirt",
                "minecraft:mud",
                "minecraft:moss_block"
        );
        darkOak.drops = new ArrayList<>();

        DropEntry darkOakLogDrop = new DropEntry();
        darkOakLogDrop.item = "minecraft:dark_oak_log";
        darkOakLogDrop.count = new CountRange(4, 7);
        darkOak.drops.add(darkOakLogDrop);

        DropEntry darkOakSaplingDrop = new DropEntry();
        darkOakSaplingDrop.item = "minecraft:dark_oak_sapling";
        darkOakSaplingDrop.count = new CountRange(1, 2);
        darkOakSaplingDrop.chance = 0.5f;
        darkOak.drops.add(darkOakSaplingDrop);

        DropEntry darkOakStickDrop = new DropEntry();
        darkOakStickDrop.item = "minecraft:stick";
        darkOakStickDrop.count = new CountRange(2, 4);
        darkOakStickDrop.chance = 0.8f;
        darkOak.drops.add(darkOakStickDrop);

        DropEntry appleDarkOakDrop = new DropEntry();
        appleDarkOakDrop.item = "minecraft:apple";
        appleDarkOakDrop.count = new CountRange(1, 2);
        appleDarkOakDrop.chance = 0.15f;
        darkOak.drops.add(appleDarkOakDrop);

        trees.add(darkOak);

        // Mangrove Propagule
        TreeEntry mangrove = new TreeEntry();
        mangrove.sapling = "minecraft:mangrove_propagule";
        mangrove.validSoils = List.of(
                "minecraft:dirt",
                "minecraft:grass_block",
                "minecraft:mud",
                "minecraft:muddy_mangrove_roots",
                "minecraft:moss_block"
        );
        mangrove.drops = new ArrayList<>();

        DropEntry mangroveLogDrop = new DropEntry();
        mangroveLogDrop.item = "minecraft:mangrove_log";
        mangroveLogDrop.count = new CountRange(3, 6);
        mangrove.drops.add(mangroveLogDrop);

        DropEntry mangrovePropaguleDrop = new DropEntry();
        mangrovePropaguleDrop.item = "minecraft:mangrove_propagule";
        mangrovePropaguleDrop.count = new CountRange(1, 2);
        mangrovePropaguleDrop.chance = 0.5f;
        mangrove.drops.add(mangrovePropaguleDrop);

        DropEntry mangroveRootsDrop = new DropEntry();
        mangroveRootsDrop.item = "minecraft:mangrove_roots";
        mangroveRootsDrop.count = new CountRange(1, 3);
        mangroveRootsDrop.chance = 0.6f;
        mangrove.drops.add(mangroveRootsDrop);

        trees.add(mangrove);

        // Cherry Sapling
        TreeEntry cherry = new TreeEntry();
        cherry.sapling = "minecraft:cherry_sapling";
        cherry.validSoils = List.of(
                "minecraft:dirt",
                "minecraft:grass_block",
                "minecraft:podzol",
                "minecraft:coarse_dirt",
                "minecraft:rooted_dirt",
                "minecraft:mud",
                "minecraft:moss_block"
        );
        cherry.drops = new ArrayList<>();

        DropEntry cherryLogDrop = new DropEntry();
        cherryLogDrop.item = "minecraft:cherry_log";
        cherryLogDrop.count = new CountRange(3, 6);
        cherry.drops.add(cherryLogDrop);

        DropEntry cherrySaplingDrop = new DropEntry();
        cherrySaplingDrop.item = "minecraft:cherry_sapling";
        cherrySaplingDrop.count = new CountRange(1, 2);
        cherrySaplingDrop.chance = 0.5f;
        cherry.drops.add(cherrySaplingDrop);

        DropEntry cherryStickDrop = new DropEntry();
        cherryStickDrop.item = "minecraft:stick";
        cherryStickDrop.count = new CountRange(2, 4);
        cherryStickDrop.chance = 0.8f;
        cherry.drops.add(cherryStickDrop);

        trees.add(cherry);

        // Azalea
        TreeEntry azalea = new TreeEntry();
        azalea.sapling = "minecraft:azalea";
        azalea.validSoils = List.of(
                "minecraft:dirt",
                "minecraft:grass_block",
                "minecraft:podzol",
                "minecraft:coarse_dirt",
                "minecraft:rooted_dirt",
                "minecraft:mud",
                "minecraft:moss_block"
        );
        azalea.drops = new ArrayList<>();

        DropEntry azaleaOakLogDrop = new DropEntry();
        azaleaOakLogDrop.item = "minecraft:oak_log";
        azaleaOakLogDrop.count = new CountRange(3, 5);
        azalea.drops.add(azaleaOakLogDrop);

        DropEntry azaleaDrop = new DropEntry();
        azaleaDrop.item = "minecraft:azalea";
        azaleaDrop.count = new CountRange(1, 1);
        azaleaDrop.chance = 0.5f;
        azalea.drops.add(azaleaDrop);

        DropEntry azaleaStickDrop = new DropEntry();
        azaleaStickDrop.item = "minecraft:stick";
        azaleaStickDrop.count = new CountRange(1, 3);
        azaleaStickDrop.chance = 0.8f;
        azalea.drops.add(azaleaStickDrop);

        DropEntry mossBlockDrop = new DropEntry();
        mossBlockDrop.item = "minecraft:moss_block";
        mossBlockDrop.count = new CountRange(1, 2);
        mossBlockDrop.chance = 0.3f;
        azalea.drops.add(mossBlockDrop);

        trees.add(azalea);

        // Flowering Azalea
        TreeEntry floweringAzalea = new TreeEntry();
        floweringAzalea.sapling = "minecraft:flowering_azalea";
        floweringAzalea.validSoils = List.of(
                "minecraft:dirt",
                "minecraft:grass_block",
                "minecraft:podzol",
                "minecraft:coarse_dirt",
                "minecraft:rooted_dirt",
                "minecraft:mud",
                "minecraft:moss_block"
        );
        floweringAzalea.drops = new ArrayList<>();

        DropEntry floweringAzaleaOakLogDrop = new DropEntry();
        floweringAzaleaOakLogDrop.item = "minecraft:oak_log";
        floweringAzaleaOakLogDrop.count = new CountRange(3, 5);
        floweringAzalea.drops.add(floweringAzaleaOakLogDrop);

        DropEntry floweringAzaleaDrop = new DropEntry();
        floweringAzaleaDrop.item = "minecraft:flowering_azalea";
        floweringAzaleaDrop.count = new CountRange(1, 1);
        floweringAzaleaDrop.chance = 0.5f;
        floweringAzalea.drops.add(floweringAzaleaDrop);

        DropEntry floweringAzaleaStickDrop = new DropEntry();
        floweringAzaleaStickDrop.item = "minecraft:stick";
        floweringAzaleaStickDrop.count = new CountRange(1, 3);
        floweringAzaleaStickDrop.chance = 0.8f;
        floweringAzalea.drops.add(floweringAzaleaStickDrop);

        DropEntry floweringAzaleaMossBlockDrop = new DropEntry();
        floweringAzaleaMossBlockDrop.item = "minecraft:moss_block";
        floweringAzaleaMossBlockDrop.count = new CountRange(1, 2);
        floweringAzaleaMossBlockDrop.chance = 0.3f;
        floweringAzalea.drops.add(floweringAzaleaMossBlockDrop);

        trees.add(floweringAzalea);
    }

    private static void addBiomesOPlentyTrees(List<TreeEntry> trees) {
        // Example implementation for BOP trees
        // This would need to be customized based on the actual mod's content

        // Fir Sapling
        TreeEntry fir = new TreeEntry();
        fir.sapling = "biomesoplenty:fir_sapling";
        fir.validSoils = List.of(
                "minecraft:dirt",
                "minecraft:grass_block",
                "minecraft:podzol",
                "minecraft:coarse_dirt"
        );
        fir.drops = new ArrayList<>();

        DropEntry firLogDrop = new DropEntry();
        firLogDrop.item = "biomesoplenty:fir_log";
        firLogDrop.count = new CountRange(4, 8);
        fir.drops.add(firLogDrop);

        DropEntry firSaplingDrop = new DropEntry();
        firSaplingDrop.item = "biomesoplenty:fir_sapling";
        firSaplingDrop.count = new CountRange(1, 2);
        firSaplingDrop.chance = 0.5f;
        fir.drops.add(firSaplingDrop);

        trees.add(fir);

        // Add more BOP trees as needed
    }

    private static void addTwilightForestTrees(List<TreeEntry> trees) {
        // Example implementation for Twilight Forest trees
        // This would need to be customized based on the actual mod's content

        // Canopy Tree Sapling
        TreeEntry canopy = new TreeEntry();
        canopy.sapling = "twilightforest:canopy_sapling";
        canopy.validSoils = List.of(
                "minecraft:dirt",
                "minecraft:grass_block",
                "minecraft:podzol"
        );
        canopy.drops = new ArrayList<>();

        DropEntry canopyLogDrop = new DropEntry();
        canopyLogDrop.item = "twilightforest:canopy_log";
        canopyLogDrop.count = new CountRange(5, 9);
        canopy.drops.add(canopyLogDrop);

        DropEntry canopySaplingDrop = new DropEntry();
        canopySaplingDrop.item = "twilightforest:canopy_sapling";
        canopySaplingDrop.count = new CountRange(1, 2);
        canopySaplingDrop.chance = 0.4f;
        canopy.drops.add(canopySaplingDrop);

        trees.add(canopy);

        // Add more Twilight Forest trees as needed
    }

    private static void addVanillaSoils(List<SoilEntry> soils) {
        SoilEntry dirt = new SoilEntry();
        dirt.soil = "minecraft:dirt";
        dirt.growthModifier = 0.5f;
        soils.add(dirt);

        SoilEntry coarseDirt = new SoilEntry();
        coarseDirt.soil = "minecraft:coarse_dirt";
        coarseDirt.growthModifier = 0.475f;
        soils.add(coarseDirt);

        SoilEntry podzol = new SoilEntry();
        podzol.soil = "minecraft:podzol";
        podzol.growthModifier = 0.6f; // Better for trees
        soils.add(podzol);

        SoilEntry mud = new SoilEntry();
        mud.soil = "minecraft:mud";
        mud.growthModifier = 0.525f;
        soils.add(mud);

        SoilEntry muddyMangroveRoots = new SoilEntry();
        muddyMangroveRoots.soil = "minecraft:muddy_mangrove_roots";
        muddyMangroveRoots.growthModifier = 0.65f; // Better for mangroves
        soils.add(muddyMangroveRoots);

        SoilEntry rootedDirt = new SoilEntry();
        rootedDirt.soil = "minecraft:rooted_dirt";
        rootedDirt.growthModifier = 0.55f;
        soils.add(rootedDirt);

        SoilEntry moss = new SoilEntry();
        moss.soil = "minecraft:moss_block";
        moss.growthModifier = 0.575f;
        soils.add(moss);

        SoilEntry grass = new SoilEntry();
        grass.soil = "minecraft:grass_block";
        grass.growthModifier = 0.55f; // Better for trees than for crops
        soils.add(grass);

        SoilEntry sand = new SoilEntry();
        sand.soil = "minecraft:sand";
        sand.growthModifier = 0.35f; // Worse for most trees
        soils.add(sand);

        SoilEntry redSand = new SoilEntry();
        redSand.soil = "minecraft:red_sand";
        redSand.growthModifier = 0.4f; // Better for acacia
        soils.add(redSand);
    }

    private static void processConfig(TreeConfigData configData) {
        trees.clear();
        soils.clear();

        if (configData.allowedTrees != null) {
            for (TreeEntry entry : configData.allowedTrees) {
                if (entry.sapling != null && !entry.sapling.isEmpty()) {
                    TreeInfo treeInfo = new TreeInfo();
                    treeInfo.drops = new ArrayList<>();

                    if (entry.validSoils != null && !entry.validSoils.isEmpty()) {
                        treeInfo.validSoils.addAll(entry.validSoils);
                    } else if (entry.soil != null && !entry.soil.isEmpty()) {
                        treeInfo.validSoils.add(entry.soil);
                    }

                    if (entry.drops != null) {
                        for (DropEntry dropEntry : entry.drops) {
                            DropInfo dropInfo = new DropInfo(
                                    dropEntry.item,
                                    dropEntry.count != null ? dropEntry.count.min : 1,
                                    dropEntry.count != null ? dropEntry.count.max : 1,
                                    dropEntry.chance
                            );
                            treeInfo.drops.add(dropInfo);
                        }
                    }

                    trees.put(entry.sapling, treeInfo);
                }
            }
        }

        if (configData.allowedSoils != null) {
            for (SoilEntry entry : configData.allowedSoils) {
                if (entry.soil != null && !entry.soil.isEmpty()) {
                    soils.put(entry.soil, new SoilInfo(entry.growthModifier));
                }
            }
        }

        LOGGER.info("Loaded {} trees and {} soils from config", trees.size(), soils.size());
    }

    public static boolean isSoilValidForSapling(String soilId, String saplingId) {
        TreeInfo treeInfo = trees.get(saplingId);
        if (treeInfo == null || treeInfo.validSoils.isEmpty()) {
            return false;
        }

        return treeInfo.validSoils.contains(soilId);
    }

    public static boolean isValidSapling(String itemId) {
        return trees.containsKey(itemId);
    }

    public static boolean isValidSoil(String blockId) {
        return soils.containsKey(blockId);
    }

    public static float getSoilGrowthModifier(String blockId) {
        SoilInfo info = soils.get(blockId);
        return info != null ? info.growthModifier : 1.0f;
    }

    public static List<DropInfo> getTreeDrops(String saplingId) {
        TreeInfo info = trees.get(saplingId);
        return info != null ? info.drops : Collections.emptyList();
    }

    public static int getBaseSaplingGrowthTime(String saplingId) {
        // Different tree types could have different base growth times
        // Default to 2000 ticks (100 seconds) if not specified
        return 2000;
    }

    public static class TreeConfigData {
        public List<TreeEntry> allowedTrees;
        public List<SoilEntry> allowedSoils;
    }

    public static class TreeEntry {
        public String sapling;
        public String soil;  // For backward compatibility
        public List<String> validSoils;
        public List<DropEntry> drops;
    }

    public static class DropEntry {
        public String item;
        public CountRange count;
        public float chance = 1.0f;
    }

    public static class CountRange {
        public int min;
        public int max;

        public CountRange() {
            this.min = 1;
            this.max = 1;
        }

        public CountRange(int min, int max) {
            this.min = min;
            this.max = max;
        }
    }

    public static class SoilEntry {
        public String soil;
        public float growthModifier;
    }

    public static class TreeInfo {
        public List<DropInfo> drops;
        public List<String> validSoils = new ArrayList<>();
    }

    public static class DropInfo {
        public final String item;
        public final int minCount;
        public final int maxCount;
        public final float chance;

        public DropInfo(String item, int minCount, int maxCount, float chance) {
            this.item = item;
            this.minCount = minCount;
            this.maxCount = maxCount;
            this.chance = chance;
        }
    }

    public static class SoilInfo {
        public final float growthModifier;

        public SoilInfo(float growthModifier) {
            this.growthModifier = growthModifier;
        }
    }

    public static Map<String, List<String>> getAllSaplingToSoilMappings() {
        Map<String, List<String>> saplingToSoilMap = new HashMap<>();

        for (Map.Entry<String, TreeInfo> entry : trees.entrySet()) {
            String saplingId = entry.getKey();
            TreeInfo treeInfo = entry.getValue();

            if (!treeInfo.validSoils.isEmpty()) {
                saplingToSoilMap.put(saplingId, new ArrayList<>(treeInfo.validSoils));
            }
        }

        return saplingToSoilMap;
    }
}