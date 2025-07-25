
package com.blocklogic.agritechtrees.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.BaseEntityBlock;

public class CrimsonPlanter extends AgritechTreesPlanterBlock{
    public static final MapCodec<CrimsonPlanter> CODEC = simpleCodec(CrimsonPlanter::new);

    public CrimsonPlanter(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }
}
