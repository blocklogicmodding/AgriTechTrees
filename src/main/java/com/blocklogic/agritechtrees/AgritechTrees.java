package com.blocklogic.agritechtrees;

import com.blocklogic.agritechtrees.block.ModBlocks;
import com.blocklogic.agritechtrees.block.entity.AgritechTreesPlanterBlockEntity;
import com.blocklogic.agritechtrees.block.entity.ModBlockEntities;
import com.blocklogic.agritechtrees.block.entity.renderer.AgritechTreesPlanterBlockEntityRenderer;
import com.blocklogic.agritechtrees.command.AgritechTreesCommands;
import com.blocklogic.agritechtrees.screen.custom.AgritechTreesPlanterScreen;
import com.blocklogic.agritechtrees.item.ModCreativeModeTabs;
import com.blocklogic.agritechtrees.item.ModItems;
import com.blocklogic.agritechtrees.screen.ModMenuTypes;
import net.minecraft.core.Direction;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(AgritechTrees.MODID)
public class AgritechTrees
{
    public static final String MODID = "agritechtrees";

    private static final Logger LOGGER = LogUtils.getLogger();

    public AgritechTrees(IEventBus modEventBus, ModContainer modContainer)
    {

        NeoForge.EVENT_BUS.register(this);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModCreativeModeTabs.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);

        NeoForge.EVENT_BUS.addListener(this::onRegisterCommands);

        modEventBus.addListener(this::addCreative);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }

    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {

    }

    public void onRegisterCommands(RegisterCommandsEvent event) {
        AgritechTreesCommands.register(event.getDispatcher());
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {

        }

        @SubscribeEvent
        public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(ModBlockEntities.AGRITECH_TREES_PLANTER_BLOCK_ENTITY.get(), AgritechTreesPlanterBlockEntityRenderer::new);
        }

        @SubscribeEvent
        public static void registerScreens(RegisterMenuScreensEvent event) {
            event.register(ModMenuTypes.AGRITECH_TREES_PLANTER_MENU.get(), AgritechTreesPlanterScreen::new);
        }

        @SubscribeEvent
        public static void registerCapabilities(RegisterCapabilitiesEvent event) {
            event.registerBlock(
                    Capabilities.ItemHandler.BLOCK,
                    (level, pos, state, blockEntity, side) -> {
                        if (state.is(ModBlocks.AGRITECH_TREES_HOPPING_PLANTER_BLOCK.get())||
                                state.is(ModBlocks.ACACIA_HOPPING_PLANTER_BLOCK.get()) ||
                                state.is(ModBlocks.BAMBOO_HOPPING_PLANTER_BLOCK.get()) ||
                                state.is(ModBlocks.BIRCH_HOPPING_PLANTER_BLOCK.get()) ||
                                state.is(ModBlocks.CHERRY_HOPPING_PLANTER_BLOCK.get()) ||
                                state.is(ModBlocks.CRIMSON_HOPPING_PLANTER_BLOCK.get()) ||
                                state.is(ModBlocks.DARK_OAK_HOPPING_PLANTER_BLOCK.get()) ||
                                state.is(ModBlocks.JUNGLE_HOPPING_PLANTER_BLOCK.get()) ||
                                state.is(ModBlocks.MANGROVE_HOPPING_PLANTER_BLOCK.get()) ||
                                state.is(ModBlocks.SPRUCE_HOPPING_PLANTER_BLOCK.get()) ||
                                state.is(ModBlocks.WARPED_HOPPING_PLANTER_BLOCK.get())
                        ) {
                            if (blockEntity instanceof AgritechTreesPlanterBlockEntity planter) {
                                if (side == Direction.UP) {
                                    return null;
                                } else if (side != null) {
                                    return planter.getOutputHandler();
                                } else {
                                    return planter.inventory;
                                }
                            }
                        }
                        return null;
                    },
                    ModBlocks.AGRITECH_TREES_HOPPING_PLANTER_BLOCK.get(),
                    ModBlocks.ACACIA_HOPPING_PLANTER_BLOCK.get(),
                    ModBlocks.BAMBOO_HOPPING_PLANTER_BLOCK.get(),
                    ModBlocks.BIRCH_HOPPING_PLANTER_BLOCK.get(),
                    ModBlocks.CHERRY_HOPPING_PLANTER_BLOCK.get(),
                    ModBlocks.CRIMSON_HOPPING_PLANTER_BLOCK.get(),
                    ModBlocks.DARK_OAK_HOPPING_PLANTER_BLOCK.get(),
                    ModBlocks.JUNGLE_HOPPING_PLANTER_BLOCK.get(),
                    ModBlocks.MANGROVE_HOPPING_PLANTER_BLOCK.get(),
                    ModBlocks.SPRUCE_HOPPING_PLANTER_BLOCK.get(),
                    ModBlocks.WARPED_HOPPING_PLANTER_BLOCK.get()
            );
        }
    }
}
