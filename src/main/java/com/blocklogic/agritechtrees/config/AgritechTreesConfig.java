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
        Path configPath = FMLPaths.CONFIGDIR.get().resolve("agritechtrees/saplings_and_soil.json");
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

        if (Config.enableArsElemental) {
            LOGGER.info("Adding Ars Nouveau Archwood trees to AgriTech Trees config");
            addArsElementalTrees(defaultTrees);
        }

        if (Config.enableArsNouveau) {
            LOGGER.info("Adding Ars Nouveau Archwood trees to AgriTech Trees config");
            addArsNouveauTrees(defaultTrees);
        }

        if (Config.enableTwilightForest) {
            LOGGER.info("Adding Twilight Forest trees to AgriTech Trees config");
            addTwilightForestTrees(defaultTrees);
        }

        if (Config.enableEvilCraft) {
            LOGGER.info("Adding Evilcraft trees to AgriTech Trees config");
            addEvilCraftTrees(defaultTrees);
        }

        if (Config.enableForbiddenArcanus) {
            LOGGER.info("Adding Forbidden Arcanus trees to AgriTech Trees config");
            addForbiddenArcanusTrees(defaultTrees);
        }

        if (Config.enableIntegratedDynamics) {
            LOGGER.info("Adding Menril trees to AgriTech Trees config");
            addIntegratedDynamicsTrees(defaultTrees);
        }

        if (Config.enableOccultism) {
            LOGGER.info("Adding Occultism trees to AgriTech Trees config");
            addOccultismTrees(defaultTrees);
        }

        if (Config.enableHexerei) {
            LOGGER.info("Adding Hexerei trees to AgriTech Trees config");
            addHexereiTrees(defaultTrees);
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
                "minecraft:mycelium"
        );
        oak.drops = new ArrayList<>();

        DropEntry oakLogDrop = new DropEntry();
        oakLogDrop.item = "minecraft:oak_log";
        oakLogDrop.count = new CountRange(2, 6);
        oak.drops.add(oakLogDrop);

        DropEntry oakSaplingDrop = new DropEntry();
        oakSaplingDrop.item = "minecraft:oak_sapling";
        oakSaplingDrop.count = new CountRange(1, 2);
        oakSaplingDrop.chance = 0.5f;
        oak.drops.add(oakSaplingDrop);

        DropEntry stickDrop = new DropEntry();
        stickDrop.item = "minecraft:stick";
        stickDrop.count = new CountRange(1, 2);
        stickDrop.chance = 0.5f;
        oak.drops.add(stickDrop);

        DropEntry appleDrop = new DropEntry();
        appleDrop.item = "minecraft:apple";
        appleDrop.count = new CountRange(1, 1);
        appleDrop.chance = 0.4f;
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
                "minecraft:mycelium"
        );
        birch.drops = new ArrayList<>();

        DropEntry birchLogDrop = new DropEntry();
        birchLogDrop.item = "minecraft:birch_log";
        birchLogDrop.count = new CountRange(2, 6);
        birch.drops.add(birchLogDrop);

        DropEntry birchSaplingDrop = new DropEntry();
        birchSaplingDrop.item = "minecraft:birch_sapling";
        birchSaplingDrop.count = new CountRange(1, 2);
        birchSaplingDrop.chance = 0.5f;
        birch.drops.add(birchSaplingDrop);

        DropEntry birchStickDrop = new DropEntry();
        birchStickDrop.item = "minecraft:stick";
        birchStickDrop.count = new CountRange(1, 2);
        birchStickDrop.chance = 0.5f;
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
                "minecraft:mycelium"
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
        spruceStickDrop.count = new CountRange(1, 2);
        spruceStickDrop.chance = 0.5f;
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
                "minecraft:mycelium"
        );
        jungle.drops = new ArrayList<>();

        DropEntry jungleLogDrop = new DropEntry();
        jungleLogDrop.item = "minecraft:jungle_log";
        jungleLogDrop.count = new CountRange(2, 6);
        jungle.drops.add(jungleLogDrop);

        DropEntry jungleSaplingDrop = new DropEntry();
        jungleSaplingDrop.item = "minecraft:jungle_sapling";
        jungleSaplingDrop.count = new CountRange(1, 2);
        jungleSaplingDrop.chance = 0.4f;
        jungle.drops.add(jungleSaplingDrop);

        DropEntry jungleStickDrop = new DropEntry();
        jungleStickDrop.item = "minecraft:stick";
        jungleStickDrop.count = new CountRange(1, 2);
        jungleStickDrop.chance = 0.5f;
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
                "minecraft:mycelium"
        );
        acacia.drops = new ArrayList<>();

        DropEntry acaciaLogDrop = new DropEntry();
        acaciaLogDrop.item = "minecraft:acacia_log";
        acaciaLogDrop.count = new CountRange(2, 6);
        acacia.drops.add(acaciaLogDrop);

        DropEntry acaciaSaplingDrop = new DropEntry();
        acaciaSaplingDrop.item = "minecraft:acacia_sapling";
        acaciaSaplingDrop.count = new CountRange(1, 2);
        acaciaSaplingDrop.chance = 0.5f;
        acacia.drops.add(acaciaSaplingDrop);

        DropEntry acaciaStickDrop = new DropEntry();
        acaciaStickDrop.item = "minecraft:stick";
        acaciaStickDrop.count = new CountRange(1, 2);
        acaciaStickDrop.chance = 0.5f;
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
                "minecraft:mycelium"
        );
        darkOak.drops = new ArrayList<>();

        DropEntry darkOakLogDrop = new DropEntry();
        darkOakLogDrop.item = "minecraft:dark_oak_log";
        darkOakLogDrop.count = new CountRange(4, 8);
        darkOak.drops.add(darkOakLogDrop);

        DropEntry darkOakSaplingDrop = new DropEntry();
        darkOakSaplingDrop.item = "minecraft:dark_oak_sapling";
        darkOakSaplingDrop.count = new CountRange(1, 2);
        darkOakSaplingDrop.chance = 0.5f;
        darkOak.drops.add(darkOakSaplingDrop);

        DropEntry darkOakStickDrop = new DropEntry();
        darkOakStickDrop.item = "minecraft:stick";
        darkOakStickDrop.count = new CountRange(1, 2);
        darkOakStickDrop.chance = 0.5f;
        darkOak.drops.add(darkOakStickDrop);

        DropEntry appleDarkOakDrop = new DropEntry();
        appleDarkOakDrop.item = "minecraft:apple";
        appleDarkOakDrop.count = new CountRange(1, 2);
        appleDarkOakDrop.chance = 0.3f;
        darkOak.drops.add(appleDarkOakDrop);

        trees.add(darkOak);

        // Mangrove Propagule
        TreeEntry mangrove = new TreeEntry();
        mangrove.sapling = "minecraft:mangrove_propagule";
        mangrove.validSoils = List.of(
                "minecraft:mud",
                "minecraft:muddy_mangrove_roots",
                "minecraft:dirt",
                "minecraft:coarse_dirt",
                "minecraft:grass_block",
                "minecraft:podzol",
                "minecraft:mycelium"
        );
        mangrove.drops = new ArrayList<>();

        DropEntry mangroveLogDrop = new DropEntry();
        mangroveLogDrop.item = "minecraft:mangrove_log";
        mangroveLogDrop.count = new CountRange(2, 6);
        mangrove.drops.add(mangroveLogDrop);

        DropEntry mangrovePropaguleDrop = new DropEntry();
        mangrovePropaguleDrop.item = "minecraft:mangrove_propagule";
        mangrovePropaguleDrop.count = new CountRange(1, 2);
        mangrovePropaguleDrop.chance = 0.5f;
        mangrove.drops.add(mangrovePropaguleDrop);

        DropEntry mangroveRootsDrop = new DropEntry();
        mangroveRootsDrop.item = "minecraft:mangrove_roots";
        mangroveRootsDrop.count = new CountRange(1, 3);
        mangroveRootsDrop.chance = 0.3f;
        mangrove.drops.add(mangroveRootsDrop);

        DropEntry mangroveStickDrop = new DropEntry();
        mangroveStickDrop.item = "minecraft:mangrove_roots";
        mangroveStickDrop.count = new CountRange(1, 2);
        mangroveStickDrop.chance = 0.5f;
        mangrove.drops.add(mangroveStickDrop);

        trees.add(mangrove);

        // Cherry Sapling
        TreeEntry cherry = new TreeEntry();
        cherry.sapling = "minecraft:cherry_sapling";
        cherry.validSoils = List.of(
                "minecraft:dirt",
                "minecraft:grass_block",
                "minecraft:podzol",
                "minecraft:coarse_dirt",
                "minecraft:mycelium"
        );
        cherry.drops = new ArrayList<>();

        DropEntry cherryLogDrop = new DropEntry();
        cherryLogDrop.item = "minecraft:cherry_log";
        cherryLogDrop.count = new CountRange(2, 6);
        cherry.drops.add(cherryLogDrop);

        DropEntry cherrySaplingDrop = new DropEntry();
        cherrySaplingDrop.item = "minecraft:cherry_sapling";
        cherrySaplingDrop.count = new CountRange(1, 2);
        cherrySaplingDrop.chance = 0.5f;
        cherry.drops.add(cherrySaplingDrop);

        DropEntry cherryStickDrop = new DropEntry();
        cherryStickDrop.item = "minecraft:stick";
        cherryStickDrop.count = new CountRange(1, 2);
        cherryStickDrop.chance = 0.5f;
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
                "minecraft:moss_block",
                "minecraft:mycelium"
        );
        azalea.drops = new ArrayList<>();

        DropEntry azaleaOakLogDrop = new DropEntry();
        azaleaOakLogDrop.item = "minecraft:oak_log";
        azaleaOakLogDrop.count = new CountRange(2, 6);
        azalea.drops.add(azaleaOakLogDrop);

        DropEntry azaleaDrop = new DropEntry();
        azaleaDrop.item = "minecraft:azalea";
        azaleaDrop.count = new CountRange(1, 1);
        azaleaDrop.chance = 0.5f;
        azalea.drops.add(azaleaDrop);

        DropEntry azaleaStickDrop = new DropEntry();
        azaleaStickDrop.item = "minecraft:stick";
        azaleaStickDrop.count = new CountRange(1, 2);
        azaleaStickDrop.chance = 0.5f;
        azalea.drops.add(azaleaStickDrop);

        DropEntry mossBlockDrop = new DropEntry();
        mossBlockDrop.item = "minecraft:moss_block";
        mossBlockDrop.count = new CountRange(1, 2);
        mossBlockDrop.chance = 0.2f;
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
                "minecraft:moss_block",
                "minecraft:mycelium"
        );
        floweringAzalea.drops = new ArrayList<>();

        DropEntry floweringAzaleaOakLogDrop = new DropEntry();
        floweringAzaleaOakLogDrop.item = "minecraft:oak_log";
        floweringAzaleaOakLogDrop.count = new CountRange(2, 6);
        floweringAzalea.drops.add(floweringAzaleaOakLogDrop);

        DropEntry floweringAzaleaDrop = new DropEntry();
        floweringAzaleaDrop.item = "minecraft:flowering_azalea";
        floweringAzaleaDrop.count = new CountRange(1, 1);
        floweringAzaleaDrop.chance = 0.5f;
        floweringAzalea.drops.add(floweringAzaleaDrop);

        DropEntry floweringAzaleaStickDrop = new DropEntry();
        floweringAzaleaStickDrop.item = "minecraft:stick";
        floweringAzaleaStickDrop.count = new CountRange(1, 2);
        floweringAzaleaStickDrop.chance = 0.5f;
        floweringAzalea.drops.add(floweringAzaleaStickDrop);

        DropEntry floweringAzaleaMossBlockDrop = new DropEntry();
        floweringAzaleaMossBlockDrop.item = "minecraft:moss_block";
        floweringAzaleaMossBlockDrop.count = new CountRange(1, 1);
        floweringAzaleaMossBlockDrop.chance = 0.2f;
        floweringAzalea.drops.add(floweringAzaleaMossBlockDrop);

        trees.add(floweringAzalea);

        // Crimson Stem
        TreeEntry crimson = new TreeEntry();
        crimson.sapling = "minecraft:crimson_fungus";
        crimson.validSoils = List.of(
                "minecraft:crimson_nylium",
                "minecraft:warped_nylium"
        );
        crimson.drops = new ArrayList<>();

        DropEntry crimsonLogDrop = new DropEntry();
        crimsonLogDrop.item = "minecraft:crimson_stem";
        crimsonLogDrop.count = new CountRange(2, 6);
        crimson.drops.add(crimsonLogDrop);

        DropEntry crimsonWartDrop = new DropEntry();
        crimsonWartDrop.item = "minecraft:nether_wart_block";
        crimsonWartDrop.count = new CountRange(4, 8);
        crimson.drops.add(crimsonWartDrop);

        DropEntry crimsonVinesDrop = new DropEntry();
        crimsonVinesDrop.item = "minecraft:weeping_vines";
        crimsonVinesDrop.count = new CountRange(1, 2);
        crimson.drops.add(crimsonVinesDrop);

        DropEntry crimsonShroomDrop = new DropEntry();
        crimsonShroomDrop.item = "minecraft:shroomlight";
        crimsonShroomDrop.count = new CountRange(2, 4);
        crimson.drops.add(crimsonShroomDrop);

        trees.add(crimson);

        // Warped Stem
        TreeEntry warped = new TreeEntry();
        warped.sapling = "minecraft:warped_fungus";
        warped.validSoils = List.of(
                "minecraft:crimson_nylium",
                "minecraft:warped_nylium"
        );
        warped.drops = new ArrayList<>();

        DropEntry warpedLogDrop = new DropEntry();
        crimsonLogDrop.item = "minecraft:warped_stem";
        crimsonLogDrop.count = new CountRange(2, 6);
        warped.drops.add(warpedLogDrop);

        DropEntry warpedWartDrop = new DropEntry();
        crimsonWartDrop.item = "minecraft:warped_wart_block";
        crimsonWartDrop.count = new CountRange(4, 8);
        warped.drops.add(warpedWartDrop);

        DropEntry warpedVinesDrop = new DropEntry();
        crimsonVinesDrop.item = "minecraft:twisting_vines";
        crimsonVinesDrop.count = new CountRange(1, 2);
        warped.drops.add(warpedVinesDrop);

        DropEntry warpedShroomDrop = new DropEntry();
        crimsonShroomDrop.item = "minecraft:shroomlight";
        crimsonShroomDrop.count = new CountRange(2, 4);
        warped.drops.add(warpedShroomDrop);

        trees.add(warped);
    }

    private static void addArsElementalTrees(List<TreeEntry> trees) {
        // Yellow Archwood Tree
        TreeEntry yellowArchwood = new TreeEntry();
        yellowArchwood.sapling = "ars_elemental:yellow_archwood_sapling";
        yellowArchwood.validSoils = List.of(
                "minecraft:dirt",
                "minecraft:grass_block",
                "minecraft:podzol",
                "minecraft:coarse_dirt",
                "minecraft:mycelium"
        );
        yellowArchwood.drops = new ArrayList<>();

        DropEntry yellowArchwoodLogDrop = new DropEntry();
        yellowArchwoodLogDrop.item = "ars_elemental:yellow_archwood_log";
        yellowArchwoodLogDrop.count = new CountRange(4, 8);
        yellowArchwood.drops.add(yellowArchwoodLogDrop);

        DropEntry yellowArchwoodSaplingDrop = new DropEntry();
        yellowArchwoodSaplingDrop.item = "ars_elemental:yellow_archwood_sapling";
        yellowArchwoodSaplingDrop.count = new CountRange(1, 1);
        yellowArchwoodSaplingDrop.chance = 0.3f;
        yellowArchwood.drops.add(yellowArchwoodSaplingDrop);

        DropEntry yellowArchwoodPodDrop = new DropEntry();
        yellowArchwoodPodDrop.item = "ars_elemental:flashpine_pod";
        yellowArchwoodPodDrop.count = new CountRange(1, 1);
        yellowArchwoodPodDrop.chance = 0.2f;
        yellowArchwood.drops.add(yellowArchwoodPodDrop);

        DropEntry yellowArchwoodStickDrop = new DropEntry();
        yellowArchwoodStickDrop.item = "minecraft:stick";
        yellowArchwoodStickDrop.count = new CountRange(1, 2);
        yellowArchwoodStickDrop.chance = 0.5f;
        yellowArchwood.drops.add(yellowArchwoodStickDrop);

        trees.add(yellowArchwood);
    }

    private static void addArsNouveauTrees(List<TreeEntry> trees) {
        // Blue Archwood Tree
        TreeEntry blueArchwood = new TreeEntry();
        blueArchwood.sapling = "ars_nouveau:blue_archwood_sapling";
        blueArchwood.validSoils = List.of(
                "minecraft:dirt",
                "minecraft:grass_block",
                "minecraft:podzol",
                "minecraft:coarse_dirt",
                "minecraft:mycelium"
        );
        blueArchwood.drops = new ArrayList<>();

        DropEntry blueArchwoodLogDrop = new DropEntry();
        blueArchwoodLogDrop.item = "ars_nouveau:blue_archwood_log";
        blueArchwoodLogDrop.count = new CountRange(4, 8);
        blueArchwood.drops.add(blueArchwoodLogDrop);

        DropEntry blueArchwoodSaplingDrop = new DropEntry();
        blueArchwoodSaplingDrop.item = "ars_nouveau:blue_archwood_sapling";
        blueArchwoodSaplingDrop.count = new CountRange(1, 1);
        blueArchwoodSaplingDrop.chance = 0.3f;
        blueArchwood.drops.add(blueArchwoodSaplingDrop);

        DropEntry blueArchwoodPodDrop = new DropEntry();
        blueArchwoodPodDrop.item = "ars_nouveau:frostaya_pod";
        blueArchwoodPodDrop.count = new CountRange(1, 1);
        blueArchwoodPodDrop.chance = 0.2f;
        blueArchwood.drops.add(blueArchwoodPodDrop);

        DropEntry blueArchwoodStickDrop = new DropEntry();
        blueArchwoodStickDrop.item = "minecraft:stick";
        blueArchwoodStickDrop.count = new CountRange(1, 2);
        blueArchwoodStickDrop.chance = 0.5f;
        blueArchwood.drops.add(blueArchwoodStickDrop);

        trees.add(blueArchwood);

        // Red Archwood Tree
        TreeEntry redArchwood = new TreeEntry();
        redArchwood.sapling = "ars_nouveau:red_archwood_sapling";
        redArchwood.validSoils = List.of(
                "minecraft:dirt",
                "minecraft:grass_block",
                "minecraft:podzol",
                "minecraft:coarse_dirt",
                "minecraft:mycelium"
        );
        redArchwood.drops = new ArrayList<>();

        DropEntry redArchwoodLogDrop = new DropEntry();
        redArchwoodLogDrop.item = "ars_nouveau:red_archwood_log";
        redArchwoodLogDrop.count = new CountRange(4, 8);
        redArchwood.drops.add(redArchwoodLogDrop);

        DropEntry redArchwoodSaplingDrop = new DropEntry();
        redArchwoodSaplingDrop.item = "ars_nouveau:red_archwood_sapling";
        redArchwoodSaplingDrop.count = new CountRange(1, 1);
        redArchwoodSaplingDrop.chance = 0.3f;
        redArchwood.drops.add(redArchwoodSaplingDrop);

        DropEntry redArchwoodPodDrop = new DropEntry();
        redArchwoodPodDrop.item = "ars_nouveau:bombegranate_pod";
        redArchwoodPodDrop.count = new CountRange(1, 1);
        redArchwoodPodDrop.chance = 0.2f;
        redArchwood.drops.add(redArchwoodPodDrop);

        DropEntry redArchwoodStickDrop = new DropEntry();
        redArchwoodStickDrop.item = "minecraft:stick";
        redArchwoodStickDrop.count = new CountRange(1, 2);
        redArchwoodStickDrop.chance = 0.5f;
        redArchwood.drops.add(redArchwoodStickDrop);

        trees.add(redArchwood);

        // Purple Archwood Tree
        TreeEntry purpleArchwood = new TreeEntry();
        purpleArchwood.sapling = "ars_nouveau:purple_archwood_sapling";
        purpleArchwood.validSoils = List.of(
                "minecraft:dirt",
                "minecraft:grass_block",
                "minecraft:podzol",
                "minecraft:coarse_dirt",
                "minecraft:mycelium"
        );
        purpleArchwood.drops = new ArrayList<>();

        DropEntry purpleArchwoodLogDrop = new DropEntry();
        purpleArchwoodLogDrop.item = "ars_nouveau:purple_archwood_log";
        purpleArchwoodLogDrop.count = new CountRange(4, 8);
        purpleArchwood.drops.add(purpleArchwoodLogDrop);

        DropEntry purpleArchwoodSaplingDrop = new DropEntry();
        purpleArchwoodSaplingDrop.item = "ars_nouveau:purple_archwood_sapling";
        purpleArchwoodSaplingDrop.count = new CountRange(1, 1);
        purpleArchwoodSaplingDrop.chance = 0.3f;
        purpleArchwood.drops.add(purpleArchwoodSaplingDrop);

        DropEntry purpleArchwoodPodDrop = new DropEntry();
        purpleArchwoodPodDrop.item = "ars_nouveau:bastion_pod";
        purpleArchwoodPodDrop.count = new CountRange(1, 1);
        purpleArchwoodPodDrop.chance = 0.2f;
        purpleArchwood.drops.add(purpleArchwoodPodDrop);

        DropEntry purpleArchwoodStickDrop = new DropEntry();
        purpleArchwoodStickDrop.item = "minecraft:stick";
        purpleArchwoodStickDrop.count = new CountRange(1, 2);
        purpleArchwoodStickDrop.chance = 0.5f;
        purpleArchwood.drops.add(purpleArchwoodStickDrop);

        trees.add(purpleArchwood);

        // Green Archwood Tree
        TreeEntry greenArchwood = new TreeEntry();
        greenArchwood.sapling = "ars_nouveau:green_archwood_sapling";
        greenArchwood.validSoils = List.of(
                "minecraft:dirt",
                "minecraft:grass_block",
                "minecraft:podzol",
                "minecraft:coarse_dirt",
                "minecraft:mycelium"
        );
        greenArchwood.drops = new ArrayList<>();

        DropEntry greenArchwoodLogDrop = new DropEntry();
        greenArchwoodLogDrop.item = "ars_nouveau:green_archwood_log";
        greenArchwoodLogDrop.count = new CountRange(4, 8);
        greenArchwood.drops.add(greenArchwoodLogDrop);

        DropEntry greenArchwoodSaplingDrop = new DropEntry();
        greenArchwoodSaplingDrop.item = "ars_nouveau:green_archwood_sapling";
        greenArchwoodSaplingDrop.count = new CountRange(1, 1);
        greenArchwoodSaplingDrop.chance = 0.3f;
        greenArchwood.drops.add(greenArchwoodSaplingDrop);

        DropEntry greenArchwoodPodDrop = new DropEntry();
        greenArchwoodPodDrop.item = "ars_nouveau:mendosteen_pod";
        greenArchwoodPodDrop.count = new CountRange(1, 1);
        greenArchwoodPodDrop.chance = 0.2f;
        greenArchwood.drops.add(greenArchwoodPodDrop);

        DropEntry greenArchwoodStickDrop = new DropEntry();
        greenArchwoodStickDrop.item = "minecraft:stick";
        greenArchwoodStickDrop.count = new CountRange(1, 2);
        greenArchwoodStickDrop.chance = 0.5f;
        greenArchwood.drops.add(greenArchwoodStickDrop);

        trees.add(greenArchwood);
    }

    private static void addTwilightForestTrees(List<TreeEntry> trees) {
        // Twilight Oak Tree
        TreeEntry twilightOak = new TreeEntry();
        twilightOak.sapling = "twilightforest:twilight_oak_sapling";
        twilightOak.validSoils = List.of(
                "minecraft:dirt",
                "minecraft:grass_block",
                "minecraft:podzol",
                "minecraft:coarse_dirt",
                "minecraft:mycelium"
        );
        twilightOak.drops = new ArrayList<>();

        DropEntry twilightOakLogDrop = new DropEntry();
        twilightOakLogDrop.item = "twilightforest:twilight_oak_log";
        twilightOakLogDrop.count = new CountRange(2, 6);
        twilightOak.drops.add(twilightOakLogDrop);

        DropEntry twilightOakSaplingDrop = new DropEntry();
        twilightOakSaplingDrop.item = "twilightforest:twilight_oak_sapling";
        twilightOakSaplingDrop.count = new CountRange(1, 2);
        twilightOak.drops.add(twilightOakSaplingDrop);

        DropEntry twilightOakStickDrop = new DropEntry();
        twilightOakStickDrop.item = "minecraft:stick";
        twilightOakStickDrop.count = new CountRange(1, 2);
        twilightOakStickDrop.chance = 0.5f;
        twilightOak.drops.add(twilightOakStickDrop);

        trees.add(twilightOak);

        // Canopy Tree
        TreeEntry canopy = new TreeEntry();
        canopy.sapling = "twilightforest:canopy_sapling";
        canopy.validSoils = List.of(
                "minecraft:dirt",
                "minecraft:grass_block",
                "minecraft:podzol",
                "minecraft:coarse_dirt",
                "minecraft:mycelium"
        );
        canopy.drops = new ArrayList<>();

        DropEntry canopyLogDrop = new DropEntry();
        canopyLogDrop.item = "twilightforest:canopy_log";
        canopyLogDrop.count = new CountRange(4, 8);
        canopy.drops.add(canopyLogDrop);

        DropEntry canopySaplingDrop = new DropEntry();
        canopySaplingDrop.item = "twilightforest:canopy_sapling";
        canopySaplingDrop.count = new CountRange(1, 2);
        canopy.drops.add(canopySaplingDrop);

        DropEntry canopyStickDrop = new DropEntry();
        canopyStickDrop.item = "minecraft:stick";
        canopyStickDrop.count = new CountRange(1, 2);
        canopyStickDrop.chance = 0.5f;
        canopy.drops.add(canopyStickDrop);

        trees.add(canopy);

        // Mangrove Tree
        TreeEntry twilightMangrove = new TreeEntry();
        twilightMangrove.sapling = "twilightforest:mangrove_sapling";
        twilightMangrove.validSoils = List.of(
                "minecraft:dirt",
                "minecraft:grass_block",
                "minecraft:podzol",
                "minecraft:coarse_dirt",
                "minecraft:mycelium"
        );
        twilightMangrove.drops = new ArrayList<>();

        DropEntry twilightMangroveLogDrop = new DropEntry();
        twilightMangroveLogDrop.item = "twilightforest:mangrove_log";
        twilightMangroveLogDrop.count = new CountRange(4, 8);
        twilightMangrove.drops.add(twilightMangroveLogDrop);

        DropEntry twilightMangroveSaplingDrop = new DropEntry();
        twilightMangroveSaplingDrop.item = "twilightforest:mangrove_sapling";
        twilightMangroveSaplingDrop.count = new CountRange(1, 2);
        twilightMangrove.drops.add(twilightMangroveSaplingDrop);

        DropEntry twilightMangroveStickDrop = new DropEntry();
        twilightMangroveStickDrop.item = "minecraft:stick";
        twilightMangroveStickDrop.count = new CountRange(1, 2);
        twilightMangroveStickDrop.chance = 0.5f;
        twilightMangrove.drops.add(twilightMangroveStickDrop);

        trees.add(twilightMangrove);

        // Darkwood Tree
        TreeEntry darkWood = new TreeEntry();
        darkWood.sapling = "twilightforest:darkwood_sapling";
        darkWood.validSoils = List.of(
                "minecraft:dirt",
                "minecraft:grass_block",
                "minecraft:podzol",
                "minecraft:coarse_dirt",
                "minecraft:mycelium"
        );
        darkWood.drops = new ArrayList<>();

        DropEntry darkWoodLogDrop = new DropEntry();
        darkWoodLogDrop.item = "twilightforest:dark_log";
        darkWoodLogDrop.count = new CountRange(4, 8);
        darkWood.drops.add(darkWoodLogDrop);

        DropEntry darkWoodSaplingDrop = new DropEntry();
        darkWoodSaplingDrop.item = "twilightforest:darkwood_sapling";
        darkWoodSaplingDrop.count = new CountRange(1, 2);
        darkWood.drops.add(darkWoodSaplingDrop);

        DropEntry darkWoodStickDrop = new DropEntry();
        darkWoodStickDrop.item = "minecraft:stick";
        darkWoodStickDrop.count = new CountRange(1, 2);
        darkWoodStickDrop.chance = 0.5f;
        darkWood.drops.add(darkWoodStickDrop);

        trees.add(darkWood);

        // Rainbow Oak Tree
        TreeEntry rainbowOak = new TreeEntry();
        rainbowOak.sapling = "twilightforest:rainbow_oak_sapling";
        rainbowOak.validSoils = List.of(
                "minecraft:dirt",
                "minecraft:grass_block",
                "minecraft:podzol",
                "minecraft:coarse_dirt",
                "minecraft:mycelium"
        );
        rainbowOak.drops = new ArrayList<>();

        DropEntry rainbowOakLogDrop = new DropEntry();
        rainbowOakLogDrop.item = "twilightforest:twilight_oak_log";
        rainbowOakLogDrop.count = new CountRange(2, 6);
        rainbowOak.drops.add(rainbowOakLogDrop);

        DropEntry rainbowOakSaplingDrop = new DropEntry();
        rainbowOakSaplingDrop.item = "twilightforest:rainbow_oak_sapling";
        rainbowOakSaplingDrop.count = new CountRange(1, 2);
        rainbowOak.drops.add(rainbowOakSaplingDrop);

        DropEntry rainbowOakStickDrop = new DropEntry();
        rainbowOakStickDrop.item = "minecraft:stick";
        rainbowOakStickDrop.count = new CountRange(1, 2);
        rainbowOakStickDrop.chance = 0.5f;
        rainbowOak.drops.add(rainbowOakStickDrop);

        trees.add(rainbowOak);
    }

    private static void addEvilCraftTrees(List<TreeEntry> trees) {
        // Undead Tree
        TreeEntry undead = new TreeEntry();
        undead.sapling = "evilcraft:undead_sapling";
        undead.validSoils = List.of(
                "minecraft:dirt",
                "minecraft:grass_block",
                "minecraft:podzol",
                "minecraft:coarse_dirt",
                "minecraft:mycelium"
        );
        undead.drops = new ArrayList<>();

        DropEntry undeadLogDrop = new DropEntry();
        undeadLogDrop.item = "evilcraft:undead_log";
        undeadLogDrop.count = new CountRange(2, 6);
        undead.drops.add(undeadLogDrop);

        DropEntry undeadDeadbushDrop = new DropEntry();
        undeadDeadbushDrop.item = "minecraft:dead_bush";
        undeadDeadbushDrop.count = new CountRange(1, 2);
        undead.drops.add(undeadDeadbushDrop);

        trees.add(undead);
    }

    private static void addForbiddenArcanusTrees(List<TreeEntry> trees) {
        // Forbidden Arcanus
        TreeEntry aurum = new TreeEntry();
        aurum.sapling = "forbidden_arcanus:aurum_sapling";
        aurum.validSoils = List.of(
                "minecraft:dirt",
                "minecraft:grass_block",
                "minecraft:podzol",
                "minecraft:coarse_dirt",
                "minecraft:mycelium"
        );
        aurum.drops = new ArrayList<>();

        DropEntry aurumLogDrop = new DropEntry();
        aurumLogDrop.item = "forbidden_arcanus:aurum_log";
        aurumLogDrop.count = new CountRange(2, 6);
        aurum.drops.add(aurumLogDrop);

        DropEntry aurumSaplingDrop = new DropEntry();
        aurumSaplingDrop.item = "forbidden_arcanus:aurum_sapling";
        aurumSaplingDrop.count = new CountRange(1, 3);
        aurum.drops.add(aurumSaplingDrop);

        DropEntry aurumStickDrop = new DropEntry();
        aurumStickDrop.item = "minecraft:stick";
        aurumStickDrop.count = new CountRange(1, 2);
        aurumStickDrop.chance = 0.5f;
        aurum.drops.add(aurumStickDrop);

        DropEntry aurumNuggetDrop = new DropEntry();
        aurumNuggetDrop.item = "minecraft:gold_nugget";
        aurumNuggetDrop.count = new CountRange(1, 2);
        aurumNuggetDrop.chance = 0.1f;
        aurum.drops.add(aurumNuggetDrop);

        trees.add(aurum);

        // Edelwood
        TreeEntry edelwood = new TreeEntry();
        edelwood.sapling = "forbidden_arcanus:growing_edelwood";
        edelwood.validSoils = List.of(
                "minecraft:dirt",
                "minecraft:grass_block",
                "minecraft:podzol",
                "minecraft:coarse_dirt",
                "minecraft:mycelium"
        );
        edelwood.drops = new ArrayList<>();

        DropEntry edelwoodLogDrop = new DropEntry();
        edelwoodLogDrop.item = "forbidden_arcanus:edelwood_log";
        edelwoodLogDrop.count = new CountRange(2, 6);
        edelwood.drops.add(edelwoodLogDrop);

        DropEntry edelwoodCarvedLogDrop = new DropEntry();
        edelwoodCarvedLogDrop.item = "forbidden_arcanus:carved_edelwood_log";
        edelwoodCarvedLogDrop.count = new CountRange(1, 1);
        aurumNuggetDrop.chance = 0.4f;
        edelwood.drops.add(edelwoodCarvedLogDrop);

        trees.add(edelwood);
    }

    private static void addIntegratedDynamicsTrees(List<TreeEntry> trees) {
        // Menril Trees
        TreeEntry menril = new TreeEntry();
        menril.sapling = "integrateddynamics:menril_sapling";
        menril.validSoils = List.of(
                "minecraft:dirt",
                "minecraft:grass_block",
                "minecraft:podzol",
                "minecraft:coarse_dirt",
                "minecraft:mycelium"
        );
        menril.drops = new ArrayList<>();

        DropEntry menrilLogDrop = new DropEntry();
        menrilLogDrop.item = "integrateddynamics:menril_log";
        menrilLogDrop.count = new CountRange(2, 6);
        menril.drops.add(menrilLogDrop);

        DropEntry menrilSaplingDrop = new DropEntry();
        menrilSaplingDrop.item = "integrateddynamics:menril_sapling";
        menrilSaplingDrop.count = new CountRange(1, 2);
        menril.drops.add(menrilSaplingDrop);

        DropEntry menrilChunkDrop = new DropEntry();
        menrilChunkDrop.item = "integrateddynamics:crystalized_menril_chunk";
        menrilChunkDrop.count = new CountRange(1, 2);
        menrilChunkDrop.chance = 0.5f;
        menril.drops.add(menrilChunkDrop);

        DropEntry menrilBerriesDrop = new DropEntry();
        menrilBerriesDrop.item = "integrateddynamics:menril_berries";
        menrilBerriesDrop.count = new CountRange(2, 4);
        menrilBerriesDrop.chance = 0.5f;
        menril.drops.add(menrilBerriesDrop);

        DropEntry menrilStickDrop = new DropEntry();
        menrilStickDrop.item = "minecraft:stick";
        menrilStickDrop.count = new CountRange(1, 2);
        menrilStickDrop.chance = 0.5f;
        menril.drops.add(menrilStickDrop);

        trees.add(menril);
    }

    private static void addHexereiTrees(List<TreeEntry> trees) {
        // Mahogany
        TreeEntry mahogany = new TreeEntry();
        mahogany.sapling = "hexerei:mahogany_sapling";
        mahogany.validSoils = List.of(
                "minecraft:dirt",
                "minecraft:grass_block",
                "minecraft:podzol",
                "minecraft:coarse_dirt",
                "minecraft:mycelium"
        );
        mahogany.drops = new ArrayList<>();

        DropEntry mahoganyLogDrop = new DropEntry();
        mahoganyLogDrop.item = "hexerei:mahogany_log";
        mahoganyLogDrop.count = new CountRange(4, 8);
        mahogany.drops.add(mahoganyLogDrop);

        DropEntry mahoganySaplingDrop = new DropEntry();
        mahoganySaplingDrop.item = "hexerei:mahogany_sapling";
        mahoganySaplingDrop.count = new CountRange(1, 1);
        mahogany.drops.add(mahoganySaplingDrop);

        DropEntry mahoganyStickDrop = new DropEntry();
        mahoganyStickDrop.item = "minecraft:stick";
        mahoganyStickDrop.count = new CountRange(1, 2);
        mahoganyStickDrop.chance = 0.5f;
        mahogany.drops.add(mahoganyStickDrop);

        trees.add(mahogany);

        // Willow
        TreeEntry willow = new TreeEntry();
        willow.sapling = "hexerei:willow_sapling";
        willow.validSoils = List.of(
                "minecraft:dirt",
                "minecraft:grass_block",
                "minecraft:podzol",
                "minecraft:coarse_dirt",
                "minecraft:mycelium"
        );
        willow.drops = new ArrayList<>();

        DropEntry willowLogDrop = new DropEntry();
        willowLogDrop.item = "hexerei:willow_log";
        willowLogDrop.count = new CountRange(4, 8);
        willow.drops.add(willowLogDrop);

        DropEntry willowSaplingDrop = new DropEntry();
        willowSaplingDrop.item = "hexerei:willow_sapling";
        willowSaplingDrop.count = new CountRange(1, 1);
        willow.drops.add(willowSaplingDrop);

        DropEntry willowStickDrop = new DropEntry();
        willowStickDrop.item = "minecraft:stick";
        willowStickDrop.count = new CountRange(1, 2);
        willowStickDrop.chance = 0.5f;
        willow.drops.add(willowStickDrop);

        trees.add(willow);

        // Witch Hazel
        TreeEntry witchHazel = new TreeEntry();
        witchHazel.sapling = "hexerei:witch_hazel_sapling";
        witchHazel.validSoils = List.of(
                "minecraft:dirt",
                "minecraft:grass_block",
                "minecraft:podzol",
                "minecraft:coarse_dirt",
                "minecraft:mycelium"
        );
        witchHazel.drops = new ArrayList<>();

        DropEntry witchHazelLogDrop = new DropEntry();
        witchHazelLogDrop.item = "hexerei:witch_hazel_log";
        witchHazelLogDrop.count = new CountRange(2, 6);
        witchHazel.drops.add(witchHazelLogDrop);

        DropEntry witchHazelSaplingDrop = new DropEntry();
        witchHazelSaplingDrop.item = "hexerei:witch_hazel_sapling";
        witchHazelSaplingDrop.count = new CountRange(1, 1);
        witchHazel.drops.add(witchHazelSaplingDrop);

        DropEntry witchHazelStickDrop = new DropEntry();
        witchHazelStickDrop.item = "minecraft:stick";
        witchHazelStickDrop.count = new CountRange(1, 2);
        witchHazelStickDrop.chance = 0.5f;
        witchHazel.drops.add(witchHazelStickDrop);

        trees.add(witchHazel);
    }

    private static void addOccultismTrees(List<TreeEntry> trees) {
        // Otherworld
        TreeEntry otherworld = new TreeEntry();
        otherworld.sapling = "occultism:otherworld_sapling";
        otherworld.validSoils = List.of(
                "minecraft:dirt",
                "minecraft:grass_block",
                "minecraft:podzol",
                "minecraft:coarse_dirt",
                "minecraft:mycelium"
        );
        otherworld.drops = new ArrayList<>();

        DropEntry otherworldLogDrop = new DropEntry();
        otherworldLogDrop.item = "occultism:otherworld_log";
        otherworldLogDrop.count = new CountRange(2, 6);
        otherworld.drops.add(otherworldLogDrop);

        DropEntry otherworldSaplingDrop = new DropEntry();
        otherworldSaplingDrop.item = "occultism:otherworld_sapling";
        otherworldSaplingDrop.count = new CountRange(1, 3);
        otherworld.drops.add(otherworldSaplingDrop);

        trees.add(otherworld);
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
        podzol.growthModifier = 0.6f;
        soils.add(podzol);

        SoilEntry mud = new SoilEntry();
        mud.soil = "minecraft:mud";
        mud.growthModifier = 0.525f;
        soils.add(mud);

        SoilEntry muddyMangroveRoots = new SoilEntry();
        muddyMangroveRoots.soil = "minecraft:muddy_mangrove_roots";
        muddyMangroveRoots.growthModifier = 0.65f;
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
        grass.growthModifier = 0.55f;
        soils.add(grass);

        SoilEntry mycelium = new SoilEntry();
        mycelium.soil = "minecraft:mycelium";
        mycelium.growthModifier = 0.55f;
        soils.add(mycelium);

        SoilEntry crimsonNylium = new SoilEntry();
        crimsonNylium.soil = "minecraft:crimson_nylium";
        crimsonNylium.growthModifier = 0.6f;
        soils.add(crimsonNylium);

        SoilEntry warpedNylium = new SoilEntry();
        warpedNylium.soil = "minecraft:warped_nylium";
        warpedNylium.growthModifier = 0.6f;
        soils.add(warpedNylium);
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