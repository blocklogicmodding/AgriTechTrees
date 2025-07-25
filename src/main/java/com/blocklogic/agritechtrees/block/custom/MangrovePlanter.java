package com.blocklogic.agritechtrees.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.BaseEntityBlock;

public class MangrovePlanter extends AgritechTreesPlanterBlock{
    public static final MapCodec<MangrovePlanter> CODEC = simpleCodec(MangrovePlanter::new);

    public MangrovePlanter(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }
}
