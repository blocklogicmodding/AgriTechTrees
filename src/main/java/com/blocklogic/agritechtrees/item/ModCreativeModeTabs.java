package com.blocklogic.agritechtrees.item;

import com.blocklogic.agritechtrees.AgritechTrees;
import com.blocklogic.agritechtrees.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AgritechTrees.MODID);

    public static final Supplier<CreativeModeTab> AGRITECH_TREES = CREATIVE_MODE_TAB.register("agritech_trees",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.AGRITECH_TREES_PLANTER_BLOCK.get()))
                    .title(Component.translatable("creativetab.agritech_trees.agritech_trees"))
                    .displayItems((ItemDisplayParameters, output) -> {

                        output.accept(ModBlocks.ACACIA_PLANTER_BLOCK);
                        output.accept(ModBlocks.BAMBOO_PLANTER_BLOCK);
                        output.accept(ModBlocks.BIRCH_PLANTER_BLOCK);
                        output.accept(ModBlocks.CHERRY_PLANTER_BLOCK);
                        output.accept(ModBlocks.CRIMSON_PLANTER_BLOCK);
                        output.accept(ModBlocks.DARK_OAK_PLANTER_BLOCK);
                        output.accept(ModBlocks.JUNGLE_PLANTER_BLOCK);
                        output.accept(ModBlocks.MANGROVE_PLANTER_BLOCK);
                        output.accept(ModBlocks.AGRITECH_TREES_PLANTER_BLOCK);
                        output.accept(ModBlocks.SPRUCE_PLANTER_BLOCK);
                        output.accept(ModBlocks.WARPED_PLANTER_BLOCK);

                        output.accept(ModBlocks.ACACIA_HOPPING_PLANTER_BLOCK);
                        output.accept(ModBlocks.BAMBOO_HOPPING_PLANTER_BLOCK);
                        output.accept(ModBlocks.BIRCH_HOPPING_PLANTER_BLOCK);
                        output.accept(ModBlocks.CHERRY_HOPPING_PLANTER_BLOCK);
                        output.accept(ModBlocks.CRIMSON_HOPPING_PLANTER_BLOCK);
                        output.accept(ModBlocks.DARK_OAK_HOPPING_PLANTER_BLOCK);
                        output.accept(ModBlocks.JUNGLE_HOPPING_PLANTER_BLOCK);
                        output.accept(ModBlocks.MANGROVE_HOPPING_PLANTER_BLOCK);
                        output.accept(ModBlocks.AGRITECH_TREES_HOPPING_PLANTER_BLOCK);
                        output.accept(ModBlocks.SPRUCE_HOPPING_PLANTER_BLOCK);
                        output.accept(ModBlocks.WARPED_HOPPING_PLANTER_BLOCK);
                        output.accept(ModItems.HOPPING_UPGRADE.get());
                    })
                    .build());

    public static void register (IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
