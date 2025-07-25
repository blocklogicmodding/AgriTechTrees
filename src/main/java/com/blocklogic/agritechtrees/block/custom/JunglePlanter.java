package com.blocklogic.agritechtrees.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.BaseEntityBlock;

public class JunglePlanter extends AgritechTreesPlanterBlock{
    public static final MapCodec<JunglePlanter> CODEC = simpleCodec(JunglePlanter::new);

    public JunglePlanter(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }
}
