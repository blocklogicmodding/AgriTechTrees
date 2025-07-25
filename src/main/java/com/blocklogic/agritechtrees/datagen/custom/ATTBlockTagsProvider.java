package com.blocklogic.agritechtrees.datagen.custom;

import com.blocklogic.agritechtrees.AgritechTrees;
import com.blocklogic.agritechtrees.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ATTBlockTagsProvider extends BlockTagsProvider {
    public ATTBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, AgritechTrees.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(ModBlocks.AGRITECH_TREES_PLANTER_BLOCK.get())
                .add(ModBlocks.ACACIA_PLANTER_BLOCK.get())
                .add(ModBlocks.BAMBOO_PLANTER_BLOCK.get())
                .add(ModBlocks.BIRCH_PLANTER_BLOCK.get())
                .add(ModBlocks.CHERRY_PLANTER_BLOCK.get())
                .add(ModBlocks.CRIMSON_PLANTER_BLOCK.get())
                .add(ModBlocks.DARK_OAK_PLANTER_BLOCK.get())
                .add(ModBlocks.JUNGLE_PLANTER_BLOCK.get())
                .add(ModBlocks.MANGROVE_PLANTER_BLOCK.get())
                .add(ModBlocks.SPRUCE_PLANTER_BLOCK.get())
                .add(ModBlocks.WARPED_PLANTER_BLOCK.get())

                .add(ModBlocks.AGRITECH_TREES_HOPPING_PLANTER_BLOCK.get())
                .add(ModBlocks.ACACIA_HOPPING_PLANTER_BLOCK.get())
                .add(ModBlocks.BAMBOO_HOPPING_PLANTER_BLOCK.get())
                .add(ModBlocks.BIRCH_HOPPING_PLANTER_BLOCK.get())
                .add(ModBlocks.CHERRY_HOPPING_PLANTER_BLOCK.get())
                .add(ModBlocks.CRIMSON_HOPPING_PLANTER_BLOCK.get())
                .add(ModBlocks.DARK_OAK_HOPPING_PLANTER_BLOCK.get())
                .add(ModBlocks.JUNGLE_HOPPING_PLANTER_BLOCK.get())
                .add(ModBlocks.MANGROVE_HOPPING_PLANTER_BLOCK.get())
                .add(ModBlocks.SPRUCE_HOPPING_PLANTER_BLOCK.get())
                .add(ModBlocks.WARPED_HOPPING_PLANTER_BLOCK.get());
    }
}
