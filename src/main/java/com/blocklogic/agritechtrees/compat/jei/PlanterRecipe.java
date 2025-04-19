package com.blocklogic.agritechtrees.compat.jei;

import com.blocklogic.agritechtrees.config.AgritechTreesConfig;
import com.blocklogic.agritechtrees.util.RegistryHelper;
import mezz.jei.api.recipe.category.extensions.IRecipeCategoryExtension;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

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

    /**
     * Factory method to create a recipe from sapling and soil IDs
     */
    public static PlanterRecipe create(String saplingId, String soilId) {
        Ingredient saplingIngredient = Ingredient.of(RegistryHelper.getItem(saplingId));
        Ingredient soilIngredient = Ingredient.of(RegistryHelper.getBlock(soilId).asItem());

        List<ItemStack> outputs = new ArrayList<>();
        List<AgritechTreesConfig.DropInfo> drops = AgritechTreesConfig.getTreeDrops(saplingId);

        for (AgritechTreesConfig.DropInfo dropInfo : drops) {
            if (dropInfo.chance > 0) {
                ItemStack outputStack = new ItemStack(
                        RegistryHelper.getItem(dropInfo.item),
                        (dropInfo.minCount + dropInfo.maxCount) / 2
                );
                outputs.add(outputStack);
            }
        }

        return new PlanterRecipe(saplingIngredient, soilIngredient, outputs);
    }
}