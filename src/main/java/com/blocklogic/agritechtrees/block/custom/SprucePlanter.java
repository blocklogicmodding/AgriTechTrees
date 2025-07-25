package com.blocklogic.agritechtrees.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.BaseEntityBlock;

public class SprucePlanter extends AgritechTreesPlanterBlock{
    public static final MapCodec<SprucePlanter> CODEC = simpleCodec(SprucePlanter::new);

    public SprucePlanter(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }
}
