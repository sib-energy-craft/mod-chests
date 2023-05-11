package com.github.sib_energy_craft.chests.load;

import com.github.sib_energy_craft.chests.screen.Container9x7Screen;
import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.sec_utils.load.DefaultModInitializer;
import com.github.sib_energy_craft.sec_utils.utils.ScreenUtils;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import org.jetbrains.annotations.NotNull;

/**
 * @since 0.0.1
 * @author sibmaks
 */
public final class Screens implements DefaultModInitializer {
    public static final ScreenHandlerType<GenericContainerScreenHandler> GENERIC_9X7;

    static {
        GENERIC_9X7 = ScreenUtils.register(Identifiers.of("generic_9x7"), Screens::createGeneric9x7,
                Container9x7Screen::new);
    }

    public static GenericContainerScreenHandler createGeneric9x7(int syncId,
                                                                 @NotNull PlayerInventory inventory,
                                                                 @NotNull PacketByteBuf buf) {
        int rows = 7;
        var simpleInventory = new SimpleInventory(9 * rows);
        return new GenericContainerScreenHandler(GENERIC_9X7, syncId, inventory, simpleInventory, rows);
    }
}
