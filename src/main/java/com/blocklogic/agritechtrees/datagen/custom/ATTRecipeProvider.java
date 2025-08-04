package com.blocklogic.agritechtrees.datagen.custom;

import com.blocklogic.agritechtrees.block.ModBlocks;
import com.blocklogic.agritechtrees.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class ATTRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ATTRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.AGRITECH_TREES_PLANTER_BLOCK.get())
                .pattern("LPL")
                .pattern("PDP")
                .pattern("LPL")
                .define('P', Items.OAK_PLANKS)
                .define('L', ItemTags.OAK_LOGS)
                .define('D', Items.DIRT)
                .unlockedBy("has_oak_logs", has(ItemTags.OAK_LOGS))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ACACIA_PLANTER_BLOCK.get())
                .pattern("LPL")
                .pattern("PDP")
                .pattern("LPL")
                .define('P', Items.ACACIA_PLANKS)
                .define('L', ItemTags.ACACIA_LOGS)
                .define('D', Items.DIRT)
                .unlockedBy("has_acacia_logs", has(ItemTags.ACACIA_LOGS))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.BAMBOO_PLANTER_BLOCK.get())
                .pattern("LPL")
                .pattern("PDP")
                .pattern("LPL")
                .define('P', Items.BAMBOO_PLANKS)
                .define('L', ItemTags.BAMBOO_BLOCKS)
                .define('D', Items.DIRT)
                .unlockedBy("has_bamboo_blocks", has(ItemTags.BAMBOO_BLOCKS))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.BIRCH_PLANTER_BLOCK.get())
                .pattern("LPL")
                .pattern("PDP")
                .pattern("LPL")
                .define('P', Items.BIRCH_PLANKS)
                .define('L', ItemTags.BIRCH_LOGS)
                .define('D', Items.DIRT)
                .unlockedBy("has_birch_logs", has(ItemTags.BIRCH_LOGS))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.CHERRY_PLANTER_BLOCK.get())
                .pattern("LPL")
                .pattern("PDP")
                .pattern("LPL")
                .define('P', Items.CHERRY_PLANKS)
                .define('L', ItemTags.CHERRY_LOGS)
                .define('D', Items.DIRT)
                .unlockedBy("has_cherry_logs", has(ItemTags.CHERRY_LOGS))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.CRIMSON_PLANTER_BLOCK.get())
                .pattern("LPL")
                .pattern("PDP")
                .pattern("LPL")
                .define('P', Items.CRIMSON_PLANKS)
                .define('L', ItemTags.CRIMSON_STEMS)
                .define('D', Items.DIRT)
                .unlockedBy("has_crimson_stems", has(ItemTags.CRIMSON_STEMS))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.DARK_OAK_PLANTER_BLOCK.get())
                .pattern("LPL")
                .pattern("PDP")
                .pattern("LPL")
                .define('P', Items.DARK_OAK_PLANKS)
                .define('L', ItemTags.DARK_OAK_LOGS)
                .define('D', Items.DIRT)
                .unlockedBy("has_dark_oak_logs", has(ItemTags.DARK_OAK_LOGS))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.JUNGLE_PLANTER_BLOCK.get())
                .pattern("LPL")
                .pattern("PDP")
                .pattern("LPL")
                .define('P', Items.JUNGLE_PLANKS)
                .define('L', ItemTags.JUNGLE_LOGS)
                .define('D', Items.DIRT)
                .unlockedBy("has_jungle_logs", has(ItemTags.JUNGLE_LOGS))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.MANGROVE_PLANTER_BLOCK.get())
                .pattern("LPL")
                .pattern("PDP")
                .pattern("LPL")
                .define('P', Items.MANGROVE_PLANKS)
                .define('L', ItemTags.MANGROVE_LOGS)
                .define('D', Items.DIRT)
                .unlockedBy("has_mangrove_logs", has(ItemTags.MANGROVE_LOGS))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.SPRUCE_PLANTER_BLOCK.get())
                .pattern("LPL")
                .pattern("PDP")
                .pattern("LPL")
                .define('P', Items.SPRUCE_PLANKS)
                .define('L', ItemTags.SPRUCE_LOGS)
                .define('D', Items.DIRT)
                .unlockedBy("has_spruce_logs", has(ItemTags.SPRUCE_LOGS))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.WARPED_PLANTER_BLOCK.get())
                .pattern("LPL")
                .pattern("PDP")
                .pattern("LPL")
                .define('P', Items.WARPED_PLANKS)
                .define('L', ItemTags.WARPED_STEMS)
                .define('D', Items.DIRT)
                .unlockedBy("has_warped_stems", has(ItemTags.WARPED_STEMS))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,  ModBlocks.AGRITECH_TREES_PLANTER_BLOCK.get())
                .pattern("LPL")
                .pattern("PDP")
                .pattern("LPL")
                .define('L', ItemTags.LOGS)
                .define('D', Items.DIRT)
                .define('P', ItemTags.PLANKS)
                .unlockedBy("has_planks", has(ItemTags.PLANKS))
                .save(recipeOutput, "agritechtrees:basic_planter_from_all_wood");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.AGRITECH_TREES_HOPPING_PLANTER_BLOCK.get())
                .requires(Items.HOPPER)
                .requires(ModBlocks.AGRITECH_TREES_PLANTER_BLOCK.get())
                .unlockedBy("has_oak_planter", has(ModBlocks.AGRITECH_TREES_PLANTER_BLOCK.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.ACACIA_HOPPING_PLANTER_BLOCK.get())
                .requires(Items.HOPPER)
                .requires(ModBlocks.ACACIA_PLANTER_BLOCK.get())
                .unlockedBy("has_acacia_planter", has(ModBlocks.ACACIA_PLANTER_BLOCK.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.BAMBOO_HOPPING_PLANTER_BLOCK.get())
                .requires(Items.HOPPER)
                .requires(ModBlocks.BAMBOO_PLANTER_BLOCK.get())
                .unlockedBy("has_bamboo_planter", has(ModBlocks.BAMBOO_PLANTER_BLOCK.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.BIRCH_HOPPING_PLANTER_BLOCK.get())
                .requires(Items.HOPPER)
                .requires(ModBlocks.BIRCH_PLANTER_BLOCK.get())
                .unlockedBy("has_birch_planter", has(ModBlocks.BIRCH_PLANTER_BLOCK.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.CHERRY_HOPPING_PLANTER_BLOCK.get())
                .requires(Items.HOPPER)
                .requires(ModBlocks.CHERRY_PLANTER_BLOCK.get())
                .unlockedBy("has_cherry_planter", has(ModBlocks.CHERRY_PLANTER_BLOCK.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.CRIMSON_HOPPING_PLANTER_BLOCK.get())
                .requires(Items.HOPPER)
                .requires(ModBlocks.CRIMSON_PLANTER_BLOCK.get())
                .unlockedBy("has_crimson_planter", has(ModBlocks.CRIMSON_PLANTER_BLOCK.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.DARK_OAK_HOPPING_PLANTER_BLOCK.get())
                .requires(Items.HOPPER)
                .requires(ModBlocks.DARK_OAK_PLANTER_BLOCK.get())
                .unlockedBy("has_dark_oak_planter", has(ModBlocks.DARK_OAK_PLANTER_BLOCK.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.JUNGLE_HOPPING_PLANTER_BLOCK.get())
                .requires(Items.HOPPER)
                .requires(ModBlocks.JUNGLE_PLANTER_BLOCK.get())
                .unlockedBy("has_jungle_planter", has(ModBlocks.JUNGLE_PLANTER_BLOCK.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.MANGROVE_HOPPING_PLANTER_BLOCK.get())
                .requires(Items.HOPPER)
                .requires(ModBlocks.MANGROVE_PLANTER_BLOCK.get())
                .unlockedBy("has_mangrove_planter", has(ModBlocks.MANGROVE_PLANTER_BLOCK.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.SPRUCE_HOPPING_PLANTER_BLOCK.get())
                .requires(Items.HOPPER)
                .requires(ModBlocks.SPRUCE_PLANTER_BLOCK.get())
                .unlockedBy("has_spruce_planter", has(ModBlocks.SPRUCE_PLANTER_BLOCK.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.WARPED_HOPPING_PLANTER_BLOCK.get())
                .requires(Items.HOPPER)
                .requires(ModBlocks.WARPED_PLANTER_BLOCK.get())
                .unlockedBy("has_warped_planter", has(ModBlocks.WARPED_PLANTER_BLOCK.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.HOPPING_UPGRADE.get())
                .pattern(" P ")
                .pattern("PHP")
                .pattern(" P ")
                .define('H', Items.HOPPER)
                .define('P', ItemTags.PLANKS)
                .unlockedBy("has_hopper", has(Items.HOPPER))
                .save(recipeOutput);
    }
}