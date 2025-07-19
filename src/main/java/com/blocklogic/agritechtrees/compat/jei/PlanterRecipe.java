package com.blocklogic.agritechtrees.compat.jei;

import com.blocklogic.agritechtrees.config.AgritechTreesConfig;
import com.blocklogic.agritechtrees.util.RegistryHelper;
import com.mojang.logging.LogUtils;
import mezz.jei.api.recipe.category.extensions.IRecipeCategoryExtension;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;

public class PlanterRecipe implements IRecipeCategoryExtension {
    private final Ingredient saplingIngredient;
    private final Ingredient soilIngredient;
    private final List<ItemStack> outputs;

    public PlanterRecipe(Ingredient saplingIngredient, Ingredient soilIngredient, List<ItemStack> outputs) {
        this.saplingIngredient = saplingIngredient;
        this.soilIngredient = soilIngredient;
        this.outputs = outputs;
    }

    public Ingredient getSaplingIngredient() {
        return saplingIngredient;
    }

    public Ingredient getSeedIngredient() {
        return saplingIngredient;
    }

    public Ingredient getSoilIngredient() {
        return soilIngredient;
    }

    public List<ItemStack> getOutputs() {
        return outputs;
    }

    public static PlanterRecipe create(String saplingId, String soilId) {
        Item saplingItem = RegistryHelper.getItem(saplingId);
        if (saplingItem == null) {
            LogUtils.getLogger().error("Failed to create tree planter recipe: Sapling item not found for ID: {}", saplingId);
            throw new IllegalArgumentException("Sapling item not found for ID: " + saplingId);
        }

        Block soilBlock = RegistryHelper.getBlock(soilId);
        if (soilBlock == null) {
            LogUtils.getLogger().error("Failed to create tree planter recipe: Soil block not found for ID: {}", soilId);
            throw new IllegalArgumentException("Soil block not found for ID: " + soilId);
        }

        Ingredient saplingIngredient = Ingredient.of(saplingItem);
        Ingredient soilIngredient = Ingredient.of(soilBlock.asItem());

        List<ItemStack> outputs = new ArrayList<>();
        List<AgritechTreesConfig.DropInfo> drops = AgritechTreesConfig.getTreeDrops(saplingId);

        for (AgritechTreesConfig.DropInfo dropInfo : drops) {
            if (dropInfo.chance > 0) {
                Item dropItem = RegistryHelper.getItem(dropInfo.item);
                if (dropItem != null) {
                    ItemStack outputStack = new ItemStack(
                            dropItem,
                            (dropInfo.minCount + dropInfo.maxCount) / 2
                    );
                    outputs.add(outputStack);
                } else {
                    LogUtils.getLogger().error("Drop item not found for ID: {} in recipe for sapling {}", dropInfo.item, saplingId);
                    throw new IllegalArgumentException("Drop item not found for ID: " + dropInfo.item + " in recipe for sapling " + saplingId);
                }
            }
        }

        return new PlanterRecipe(saplingIngredient, soilIngredient, outputs);
    }
}