package com.blocklogic.agritechtrees.item;

import com.blocklogic.agritechtrees.AgritechTrees;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(AgritechTrees.MODID);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
