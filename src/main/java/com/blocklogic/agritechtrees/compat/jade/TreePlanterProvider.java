package com.blocklogic.agritechtrees.compat.jade;

import com.blocklogic.agritechtrees.AgritechTrees;
import com.blocklogic.agritechtrees.block.entity.AgritechTreesPlanterBlockEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum TreePlanterProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    INSTANCE;

    private static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(AgritechTrees.MODID, "tree_planter_info");

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        CompoundTag data = accessor.getServerData();

        if (!data.contains("hasTree") || !data.getBoolean("hasTree")) {
            return;
        }

        String saplingName = data.getString("saplingName");
        float progressPercent = data.getFloat("progressPercent");
        String soilName = data.getString("soilName");
        float growthModifier = data.getFloat("growthModifier");
        boolean readyToHarvest = data.getBoolean("readyToHarvest");

        if (readyToHarvest) {
            tooltip.add(Component.translatable("jade.agritechtrees.tree_ready", saplingName));
        } else {
            tooltip.add(Component.translatable("jade.agritechtrees.tree_progress",
                    saplingName, Math.round(progressPercent)).withStyle(ChatFormatting.DARK_GREEN));
        }

        tooltip.add(Component.translatable("jade.agritechtrees.soil_info",
                soilName, String.format("%.2fx", growthModifier)));
    }

    @Override
    public void appendServerData(CompoundTag data, BlockAccessor accessor) {
        if (!(accessor.getBlockEntity() instanceof AgritechTreesPlanterBlockEntity planter)) {
            return;
        }

        ItemStack saplingStack = planter.inventory.getStackInSlot(0);
        ItemStack soilStack = planter.inventory.getStackInSlot(1);

        if (!saplingStack.isEmpty() && !soilStack.isEmpty()) {
            data.putBoolean("hasTree", true);
            data.putString("saplingName", saplingStack.getDisplayName().getString());
            data.putFloat("progressPercent", planter.getGrowthProgress() * 100);
            data.putString("soilName", soilStack.getDisplayName().getString());
            data.putFloat("growthModifier", planter.getGrowthModifier(soilStack));
            data.putBoolean("readyToHarvest", planter.getGrowthProgress() >= 1.0f);
        } else {
            data.putBoolean("hasTree", false);
        }
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }
}