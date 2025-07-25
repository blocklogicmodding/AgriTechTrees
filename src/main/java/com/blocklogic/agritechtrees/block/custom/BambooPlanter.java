package com.blocklogic.agritechtrees.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.BaseEntityBlock;

public class BambooPlanter extends AgritechTreesPlanterBlock{
    public static final MapCodec<BambooPlanter> CODEC = simpleCodec(BambooPlanter::new);

    public BambooPlanter(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }
}
