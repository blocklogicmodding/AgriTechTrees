package com.blocklogic.agritechtrees.block.entity;

import com.blocklogic.agritechtrees.AgritechTrees;
import com.blocklogic.agritechtrees.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, AgritechTrees.MODID);

    public static final Supplier<BlockEntityType<AgritechTreesPlanterBlockEntity>> AGRITECH_TREES_PLANTER_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("agritech_planter_be", () -> BlockEntityType.Builder.of(
                    AgritechTreesPlanterBlockEntity::new, ModBlocks.AGRITECH_TREES_PLANTER_BLOCK.get(),
                    ModBlocks.AGRITECH_TREES_HOPPING_PLANTER_BLOCK.get()
            ).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
