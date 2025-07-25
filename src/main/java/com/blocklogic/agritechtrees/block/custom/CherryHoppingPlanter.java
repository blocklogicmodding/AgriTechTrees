package com.blocklogic.agritechtrees.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.BaseEntityBlock;

public class CherryHoppingPlanter extends AgritechTreesHoppingPlanterBlock{

    public static final MapCodec<CherryHoppingPlanter> CODEC = simpleCodec(CherryHoppingPlanter::new);

    public CherryHoppingPlanter(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }
}
