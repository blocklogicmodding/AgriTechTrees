package com.blocklogic.agritechtrees.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.BaseEntityBlock;

public class JungleHoppingPlanter extends AgritechTreesHoppingPlanterBlock{

    public static final MapCodec<JungleHoppingPlanter> CODEC = simpleCodec(JungleHoppingPlanter::new);

    public JungleHoppingPlanter(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }
}
