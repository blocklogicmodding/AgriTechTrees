package com.blocklogic.agritechtrees;

import com.blocklogic.agritechtrees.config.AgritechTreesConfig;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = AgritechTrees.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();


    private static final ModConfigSpec.BooleanValue MESSAGE = BUILDER
            .comment("INFO: If you change any of the values below, delete 'config/agritechtrees/saplings_and_soils.json' and restart your client to regenerate the crop config!")
            .define("infoIs", true);

    // Mod compatibility section
    private static final ModConfigSpec.BooleanValue ENABLE_BIOMES_O_PLENTY = BUILDER
            .comment("Enable Biomes 'O Plenty. Default: true")
            .define("enableTwilightForest", true);

    private static final ModConfigSpec.BooleanValue ENABLE_TWILIGHT_FOREST = BUILDER
            .comment("Enable Twilight Forest. Default: true")
            .define("enableBiomesOPlenty", true);


    static final ModConfigSpec SPEC = BUILDER.build();

    public static boolean enableBiomesOPlenty;
    public static boolean enableTwilightForest;

    private static boolean validateItemName(final Object obj)
    {
        return obj instanceof String itemName && BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse(itemName));
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        enableBiomesOPlenty = ENABLE_BIOMES_O_PLENTY.get() && ModList.get().isLoaded("biomesoplenty");
        enableTwilightForest = ENABLE_TWILIGHT_FOREST.get() && ModList.get().isLoaded("twilightforest");

        AgritechTreesConfig.loadConfig();
    }
}