package com.blocklogic.agritechtrees.compat.jei;

import com.blocklogic.agritechtrees.block.ModBlocks;
import com.blocklogic.agritechtrees.config.AgritechTreesConfig;
import com.blocklogic.agritechtrees.util.RegistryHelper;
import com.blocklogic.agritechtrees.AgritechTrees;
import com.mojang.logging.LogUtils;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@JeiPlugin
public class AgritechTreesJeiPlugin implements IModPlugin {
    private static final ResourceLocation PLUGIN_ID =
            ResourceLocation.fromNamespaceAndPath(AgritechTrees.MODID, "jei_plugin");

    @Override
    public ResourceLocation getPluginUid() {
        return PLUGIN_ID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(
                new PlanterRecipeCategory(registration.getJeiHelpers().getGuiHelper())
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        List<PlanterRecipe> planterRecipes = generatePlanterRecipes();
        registration.addRecipes(PlanterRecipeCategory.PLANTER_RECIPE_RECIPE_TYPE, planterRecipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(
                new ItemStack(ModBlocks.AGRITECH_TREES_PLANTER_BLOCK.get()),
                PlanterRecipeCategory.PLANTER_RECIPE_RECIPE_TYPE
        );
        registration.addRecipeCatalyst(
                new ItemStack(ModBlocks.AGRITECH_TREES_HOPPING_PLANTER_BLOCK.get()),
                PlanterRecipeCategory.PLANTER_RECIPE_RECIPE_TYPE
        );
    }

    private List<PlanterRecipe> generatePlanterRecipes() {
        List<PlanterRecipe> recipes = new ArrayList<>();

        Map<String, List<String>> saplingToSoilMap = AgritechTreesConfig.getAllSaplingToSoilMappings();

        for (Map.Entry<String, List<String>> entry : saplingToSoilMap.entrySet()) {
            String saplingId = entry.getKey();

            for (String soilId : entry.getValue()) {
                try {
                    Block soilBlock = RegistryHelper.getBlock(soilId);
                    if (soilBlock == null) {
                        LogUtils.getLogger().error("Invalid soil block in config: {} for sapling {}", soilId, saplingId);
                        continue;
                    }

                    PlanterRecipe recipe = PlanterRecipe.create(saplingId, soilId);
                    if (recipe != null && !recipe.getOutputs().isEmpty()) {
                        recipes.add(recipe);
                    }
                } catch (Exception e) {
                    LogUtils.getLogger().error("Error creating recipe for sapling {} and soil {}: {}",
                            saplingId, soilId, e.getMessage(), e);
                }
            }
        }

        LogUtils.getLogger().info("Generated {} tree planter recipes for JEI", recipes.size());
        return recipes;
    }
}