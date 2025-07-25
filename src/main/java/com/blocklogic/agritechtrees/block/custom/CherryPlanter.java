package com.blocklogic.agritechtrees.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.BaseEntityBlock;

public class CherryPlanter extends AgritechTreesPlanterBlock{
    public static final MapCodec<CherryPlanter> CODEC = simpleCodec(CherryPlanter::new);

    public CherryPlanter(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }
}
