package com.blocklogic.agritechtrees.screen;

import com.blocklogic.agritechtrees.AgritechTrees;
import com.blocklogic.agritechtrees.screen.custom.AgritechTreesPlanterMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, AgritechTrees.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<AgritechTreesPlanterMenu>> AGRITECH_TREES_PLANTER_MENU = registerMenuType("agritech_trees_planter_menu", AgritechTreesPlanterMenu::new);

    private static <T extends AbstractContainerMenu>DeferredHolder<MenuType<?>, MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory) {
        return MENUS.register(name, () -> IMenuTypeExtension.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
