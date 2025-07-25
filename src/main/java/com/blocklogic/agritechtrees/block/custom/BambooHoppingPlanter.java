package com.blocklogic.agritechtrees.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.BaseEntityBlock;

public class BambooHoppingPlanter extends AgritechTreesHoppingPlanterBlock{

    public static final MapCodec<BambooHoppingPlanter> CODEC = simpleCodec(BambooHoppingPlanter::new);

    public BambooHoppingPlanter(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }
}
