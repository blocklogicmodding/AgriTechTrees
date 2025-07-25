package com.blocklogic.agritechtrees.block.entity.renderer;

import com.blocklogic.agritechtrees.block.entity.AgritechTreesPlanterBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class AgritechTreesPlanterBlockEntityRenderer implements BlockEntityRenderer<AgritechTreesPlanterBlockEntity> {

    public AgritechTreesPlanterBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(AgritechTreesPlanterBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        if (blockEntity.inventory.getSlots() > 1 && !blockEntity.inventory.getStackInSlot(1).isEmpty()) {
            ItemStack soilStack = blockEntity.inventory.getStackInSlot(1);

            if (soilStack.getItem() instanceof BlockItem blockItem) {
                BlockState soilState = blockItem.getBlock().defaultBlockState();

                poseStack.pushPose();
                poseStack.translate(0.175, 0.4, 0.175);
                poseStack.scale(0.65f, 0.05f, 0.65f);

                BlockRenderDispatcher dispatcher = Minecraft.getInstance().getBlockRenderer();
                dispatcher.renderSingleBlock(soilState, poseStack, bufferSource, packedLight, OverlayTexture.NO_OVERLAY);

                poseStack.popPose();
            }
        }

        if (blockEntity.inventory.getSlots() > 0 && !blockEntity.inventory.getStackInSlot(0).isEmpty() &&
                blockEntity.inventory.getSlots() > 1 && !blockEntity.inventory.getStackInSlot(1).isEmpty()) {

            ItemStack saplingStack = blockEntity.inventory.getStackInSlot(0);

            if (saplingStack.getItem() instanceof BlockItem blockItem) {
                Block saplingBlock = blockItem.getBlock();
                BlockState saplingState = saplingBlock.defaultBlockState();

                poseStack.pushPose();

                poseStack.translate(0.5, 0.45, 0.5);

                float growthProgress = blockEntity.getGrowthProgress();

                float scale = 0.5f + (growthProgress * 0.3f);
                poseStack.scale(scale, scale, scale);

                poseStack.translate(-0.5, 0, -0.5);

                BlockRenderDispatcher dispatcher = Minecraft.getInstance().getBlockRenderer();
                dispatcher.renderSingleBlock(saplingState, poseStack, bufferSource,
                        packedLight, OverlayTexture.NO_OVERLAY);

                poseStack.popPose();
            }
        }
    }
}