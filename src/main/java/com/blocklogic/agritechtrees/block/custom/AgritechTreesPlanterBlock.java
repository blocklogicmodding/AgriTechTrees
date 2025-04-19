package com.blocklogic.agritechtrees.block.custom;

import com.blocklogic.agritechtrees.block.entity.AgritechTreesPlanterBlockEntity;
import com.blocklogic.agritechtrees.block.entity.ModBlockEntities;
import com.blocklogic.agritechtrees.screen.custom.AgritechTreesPlanterMenu;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
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
    public static final VoxelShape SHAPE = Block.box(0, 0.0, 0, 15.9, 15.9, 15.9);
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
        if (level.getBlockEntity(pos) instanceof AgritechTreesPlanterBlockEntity agritechHoppingPlanterBlockEntity) {
            if (!level.isClientSide()) {
                MenuProvider menuProvider = new SimpleMenuProvider(
                        (containerId, playerInventory, playerEntity) -> new AgritechTreesPlanterMenu(containerId, playerInventory, agritechHoppingPlanterBlockEntity),
                        Component.translatable("container.agritechtrees.planter")
                );

                player.openMenu(menuProvider, pos);
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide());
        }

        return ItemInteractionResult.SUCCESS;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return type == ModBlockEntities.AGRITECH_TREES_PLANTER_BLOCK_ENTITY.get() ?
                (lvl, pos, blockState, blockEntity) -> AgritechTreesPlanterBlockEntity.tick(lvl, pos, blockState, (AgritechTreesPlanterBlockEntity)blockEntity) :
                null;
    }
}
