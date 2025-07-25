package com.blocklogic.agritechtrees.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.BaseEntityBlock;

public class BirchHoppingPlanter extends AgritechTreesHoppingPlanterBlock{

    public static final MapCodec<BirchHoppingPlanter> CODEC = simpleCodec(BirchHoppingPlanter::new);

    public BirchHoppingPlanter(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }
}
