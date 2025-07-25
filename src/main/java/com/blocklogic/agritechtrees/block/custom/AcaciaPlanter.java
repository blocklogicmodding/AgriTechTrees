package com.blocklogic.agritechtrees.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.BaseEntityBlock;

public class AcaciaPlanter extends AgritechTreesPlanterBlock{
    public static final MapCodec<AcaciaPlanter> CODEC = simpleCodec(AcaciaPlanter::new);

    public AcaciaPlanter(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }
}
