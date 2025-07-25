package com.blocklogic.agritechtrees.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.BaseEntityBlock;

public class WarpedHoppingPlanter extends AgritechTreesHoppingPlanterBlock{

    public static final MapCodec<WarpedHoppingPlanter> CODEC = simpleCodec(WarpedHoppingPlanter::new);

    public WarpedHoppingPlanter(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }
}
