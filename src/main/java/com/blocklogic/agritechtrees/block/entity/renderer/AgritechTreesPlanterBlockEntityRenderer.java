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
        // Render soil similarly to crop renderer
        if (blockEntity.inventory.getSlots() > 1 && !blockEntity.inventory.getStackInSlot(1).isEmpty()) {
            ItemStack soilStack = blockEntity.inventory.getStackInSlot(1);

            if (soilStack.getItem() instanceof BlockItem blockItem) {
                BlockState soilState = blockItem.getBlock().defaultBlockState();

                poseStack.pushPose();
                poseStack.translate(0.135, 0.4, 0.135);
                poseStack.scale(0.77f, 0.4f, 0.77f);

                BlockRenderDispatcher dispatcher = Minecraft.getInstance().getBlockRenderer();
                dispatcher.renderSingleBlock(soilState, poseStack, bufferSource, packedLight, OverlayTexture.NO_OVERLAY);

                poseStack.popPose();
            }
        }

        // Render sapling with simple scaling based on growth progress
        if (blockEntity.inventory.getSlots() > 0 && !blockEntity.inventory.getStackInSlot(0).isEmpty() &&
                blockEntity.inventory.getSlots() > 1 && !blockEntity.inventory.getStackInSlot(1).isEmpty()) {

            ItemStack saplingStack = blockEntity.inventory.getStackInSlot(0);

            if (saplingStack.getItem() instanceof BlockItem blockItem) {
                Block saplingBlock = blockItem.getBlock();
                BlockState saplingState = saplingBlock.defaultBlockState();

                poseStack.pushPose();

                // Center the sapling in the planter
                poseStack.translate(0.5, 0.8, 0.5);

                // Get growth progress (0.0 to 1.0)
                float growthProgress = blockEntity.getGrowthProgress();

                // Scale sapling based on growth progress
                float scale = 0.5f + (growthProgress * 0.3f);
                poseStack.scale(scale, scale, scale);

                // Shift back to ensure centered positioning
                poseStack.translate(-0.5, 0, -0.5);

                BlockRenderDispatcher dispatcher = Minecraft.getInstance().getBlockRenderer();
                dispatcher.renderSingleBlock(saplingState, poseStack, bufferSource,
                        packedLight, OverlayTexture.NO_OVERLAY);

                poseStack.popPose();
            }
        }
    }
}