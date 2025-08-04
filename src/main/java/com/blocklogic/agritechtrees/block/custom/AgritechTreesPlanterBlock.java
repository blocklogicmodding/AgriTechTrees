package com.blocklogic.agritechtrees.block.custom;

import com.blocklogic.agritechtrees.block.entity.AgritechTreesPlanterBlockEntity;
import com.blocklogic.agritechtrees.block.entity.ModBlockEntities;
import com.blocklogic.agritechtrees.config.AgritechTreesConfig;
import com.blocklogic.agritechtrees.item.ModItems;
import com.blocklogic.agritechtrees.screen.custom.AgritechTreesPlanterMenu;
import com.blocklogic.agritechtrees.util.PlanterUpgradeHandler;
import com.blocklogic.agritechtrees.util.RegistryHelper;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class AgritechTreesPlanterBlock extends BaseEntityBlock {
    public static final VoxelShape SHAPE = Block.box(1.0, 0.0, 1.1, 15.0, 9.0, 15.0);
    public static final MapCodec<AgritechTreesPlanterBlock> CODEC = simpleCodec(AgritechTreesPlanterBlock::new);

    public AgritechTreesPlanterBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new AgritechTreesPlanterBlockEntity(blockPos, blockState);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if(state.getBlock() != newState.getBlock()) {
            if(level.getBlockEntity(pos) instanceof AgritechTreesPlanterBlockEntity agritechPlanterBlock) {
                agritechPlanterBlock.drops();
                level.updateNeighbourForOutputSignal(pos, this);
            }
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (stack.getItem() == ModItems.HOPPING_UPGRADE.get()) {
            if (!level.isClientSide()) {
                boolean upgraded = PlanterUpgradeHandler.performUpgrade(level, pos, state, player, hand);
                if (upgraded) {
                    return ItemInteractionResult.SUCCESS;
                }
            }
            return ItemInteractionResult.SUCCESS;
        }

        if (level.getBlockEntity(pos) instanceof AgritechTreesPlanterBlockEntity planterBlockEntity) {
            if (player.isCrouching()) {
                if (!level.isClientSide()) {
                    MenuProvider menuProvider = new SimpleMenuProvider(
                            (containerId, playerInventory, playerEntity) ->
                                    new AgritechTreesPlanterMenu(containerId, playerInventory, planterBlockEntity),
                            Component.translatable("container.agritechtrees.planter")
                    );

                    player.openMenu(menuProvider, pos);
                }
                return ItemInteractionResult.SUCCESS;
            }

            ItemStack heldItem = player.getItemInHand(hand);
            String heldItemId = RegistryHelper.getItemId(heldItem);

            if (AgritechTreesConfig.isValidSapling(heldItemId) && !AgritechTreesConfig.isValidSoil(heldItemId)) {
                if (level.isClientSide()) {
                    return ItemInteractionResult.SUCCESS;
                }

                if (planterBlockEntity.inventory.getStackInSlot(0).isEmpty()) {
                    ItemStack soilStack = planterBlockEntity.inventory.getStackInSlot(1);
                    if (!soilStack.isEmpty()) {
                        String soilId = RegistryHelper.getItemId(soilStack);
                        if (!AgritechTreesConfig.isSoilValidForSapling(soilId, heldItemId)) {
                            player.displayClientMessage(Component.translatable("message.agritechtrees.invalid_seed_soil_combination"), true);
                            return ItemInteractionResult.SUCCESS;
                        }
                    }
                    ItemStack seedStack = heldItem.copyWithCount(1);
                    planterBlockEntity.inventory.setStackInSlot(0, seedStack);
                    heldItem.shrink(1);
                    level.playSound(null, pos, SoundEvents.CROP_PLANTED, SoundSource.BLOCKS, 1.0F, 1.0F);

                    level.sendBlockUpdated(pos, state, state, Block.UPDATE_CLIENTS);
                    planterBlockEntity.setChanged();
                    return ItemInteractionResult.SUCCESS;
                }
            } else if (AgritechTreesConfig.isValidSoil(heldItemId)) {
                if (level.isClientSide()) {
                    return ItemInteractionResult.SUCCESS;
                }

                if (planterBlockEntity.inventory.getStackInSlot(1).isEmpty()) {
                    ItemStack seedStack = planterBlockEntity.inventory.getStackInSlot(0);
                    if (!seedStack.isEmpty()) {
                        String seedId = RegistryHelper.getItemId(seedStack);
                        if (!AgritechTreesConfig.isSoilValidForSapling(heldItemId, seedId)) {
                            player.displayClientMessage(Component.translatable("message.agritechtrees.invalid_seed_soil_combination"), true);
                            return ItemInteractionResult.SUCCESS;
                        }
                    }

                    ItemStack soilStack = heldItem.copyWithCount(1);
                    planterBlockEntity.inventory.setStackInSlot(1, soilStack);
                    heldItem.shrink(1);
                    level.playSound(null, pos, SoundEvents.GRAVEL_PLACE, SoundSource.BLOCKS, 1.0F, 0.8F);

                    level.sendBlockUpdated(pos, state, state, Block.UPDATE_CLIENTS);
                    planterBlockEntity.setChanged();
                    return ItemInteractionResult.SUCCESS;
                }
            } else if (heldItem.getItem() instanceof BlockItem) {
                ItemStack seedStack = planterBlockEntity.inventory.getStackInSlot(0);
                if (!seedStack.isEmpty() && planterBlockEntity.inventory.getStackInSlot(1).isEmpty()) {
                    String seedId = RegistryHelper.getItemId(seedStack);
                    if (AgritechTreesConfig.isValidSoil(heldItemId) &&
                            !AgritechTreesConfig.isSoilValidForSapling(heldItemId, seedId)) {
                        if (!level.isClientSide()) {
                            player.displayClientMessage(Component.translatable("message.agritechtrees.invalid_seed_soil_combination"), true);
                        }
                        return ItemInteractionResult.SUCCESS;
                    }
                }
            }

            if (!level.isClientSide()) {
                MenuProvider menuProvider = new SimpleMenuProvider(
                        (containerId, playerInventory, playerEntity) ->
                                new AgritechTreesPlanterMenu(containerId, playerInventory, planterBlockEntity),
                        Component.translatable("container.agritechtrees.planter")
                );

                player.openMenu(menuProvider, pos);
            }
            return ItemInteractionResult.SUCCESS;
        }

        return ItemInteractionResult.FAIL;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return type == ModBlockEntities.AGRITECH_TREES_PLANTER_BLOCK_ENTITY.get() ?
                (lvl, pos, blockState, blockEntity) -> AgritechTreesPlanterBlockEntity.tick(lvl, pos, blockState, (AgritechTreesPlanterBlockEntity)blockEntity) :
                null;
    }
}
