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
    private static final ModConfigSpec.BooleanValue ENABLE_ARS_ELEMENTAL = BUILDER
            .comment("Enable Ars Elemental Archwood Trees. Default: true")
            .define("addArsElementalTrees", true);

    private static final ModConfigSpec.BooleanValue ENABLE_ARS_NOUVEAU = BUILDER
            .comment("Enable Ars Nouveau Archwood Trees. Default: true")
            .define("addArsNouveauTrees", true);

    private static final ModConfigSpec.BooleanValue ENABLE_TWILIGHT_FOREST = BUILDER
            .comment("Enable Twilight Forest Trees (Not including magic trees). Default: true")
            .define("enableTwilightForest", true);

    private static final ModConfigSpec.BooleanValue ENABLE_EVILCRAFT = BUILDER
            .comment("Enable Evilcraft Trees (Not including magic trees). Default: true")
            .define("enableEvilCraft", true);

    private static final ModConfigSpec.BooleanValue ENABLE_FORBIDDEN_ARCANUS = BUILDER
            .comment("Enable Forbidden Arcanus Trees. Default: true")
            .define("enableForbiddenArcanus", true);

    private static final ModConfigSpec.BooleanValue ENABLE_INTEGRATED_DYNAMICS = BUILDER
            .comment("Enable Integrated Dynamics Trees. Default: true")
            .define("enableIntegratedDynamics", true);

    private static final ModConfigSpec.BooleanValue ENABLE_OCCULTISM = BUILDER
            .comment("Enable Occultism Trees. Default: true")
            .define("enableOccultism", true);

    private static final ModConfigSpec.BooleanValue ENABLE_HEXEREI = BUILDER
            .comment("Enable Hexerei Trees. Default: true")
            .define("enableHexerei", true);


    static final ModConfigSpec SPEC = BUILDER.build();

    public static boolean enableArsElemental;
    public static boolean enableArsNouveau;
    public static boolean enableTwilightForest;
    public static boolean enableEvilCraft;
    public static boolean enableForbiddenArcanus;
    public static boolean enableIntegratedDynamics;
    public static boolean enableOccultism;
    public static boolean enableHexerei;

    private static boolean validateItemName(final Object obj)
    {
        return obj instanceof String itemName && BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse(itemName));
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        enableArsElemental = ENABLE_ARS_ELEMENTAL.get() && ModList.get().isLoaded("ars_elemental");
        enableArsNouveau = ENABLE_ARS_NOUVEAU.get() && ModList.get().isLoaded("ars_nouveau");
        enableTwilightForest = ENABLE_TWILIGHT_FOREST.get() && ModList.get().isLoaded("twilightforest");
        enableEvilCraft = ENABLE_EVILCRAFT.get() && ModList.get().isLoaded("evilcraft");
        enableForbiddenArcanus = ENABLE_FORBIDDEN_ARCANUS.get() && ModList.get().isLoaded("forbidden_arcanus");
        enableIntegratedDynamics = ENABLE_INTEGRATED_DYNAMICS.get() && ModList.get().isLoaded("integrateddynamics");
        enableOccultism = ENABLE_OCCULTISM.get() && ModList.get().isLoaded("occultism");
        enableHexerei = ENABLE_HEXEREI.get() && ModList.get().isLoaded("hexerei");

        AgritechTreesConfig.loadConfig();
    }
}