
package com.blocklogic.agritechtrees.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.BaseEntityBlock;

public class CrimsonHoppingPlanter extends AgritechTreesHoppingPlanterBlock{

    public static final MapCodec<CrimsonHoppingPlanter> CODEC = simpleCodec(CrimsonHoppingPlanter::new);

    public CrimsonHoppingPlanter(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }
}
