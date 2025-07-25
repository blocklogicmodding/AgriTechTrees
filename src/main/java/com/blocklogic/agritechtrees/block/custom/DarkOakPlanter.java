
package com.blocklogic.agritechtrees.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.BaseEntityBlock;

public class DarkOakPlanter extends AgritechTreesPlanterBlock{
    public static final MapCodec<DarkOakPlanter> CODEC = simpleCodec(DarkOakPlanter::new);

    public DarkOakPlanter(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }
}
