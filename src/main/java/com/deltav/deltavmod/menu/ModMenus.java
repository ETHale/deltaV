package com.deltav.deltavmod.menu;

import com.deltav.deltavmod.DeltaV;
import com.deltav.deltavmod.block.entity.BasicBatteryBlockEntity;
import com.deltav.deltavmod.screen.custom.CrusherMenu;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenus {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, DeltaV.MODID);

    public static final DeferredHolder<MenuType<?>,MenuType<BasicBatteryMenu>> BASIC_BATTERY_MENU = MENUS.register(
        "basic_battery", 
        () -> IMenuTypeExtension.create(
            (windowId, inv, buffer) -> {
                return new BasicBatteryMenu(windowId, inv, buffer);
            }
        )
    );

    public static final DeferredHolder<MenuType<?>, MenuType<CrusherMenu>> CRUSHER_MENU = MENUS.register(
        "crusher",
        () -> IMenuTypeExtension.create(CrusherMenu::new)
    );

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
