package com.github.sib_energy_craft.chests.load;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.sec_utils.load.DefaultModInitializer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import org.jetbrains.annotations.NotNull;

import static com.github.sib_energy_craft.sec_utils.utils.ScreenUtils.registerHandler;

/**
 * @since 0.0.6
 * @author sibmaks
 */
public final class ScreenHandlers implements DefaultModInitializer {
    public static final ScreenHandlerType<GenericContainerScreenHandler> DIAMOND_CHEST;

    static {
        DIAMOND_CHEST = registerHandler(Identifiers.of("diamond_chest"), ScreenHandlers::createGeneric9x7);
    }

    public static GenericContainerScreenHandler createGeneric9x7(int syncId,
                                                                 @NotNull PlayerInventory inventory,
                                                                 @NotNull PacketByteBuf buf) {
        int rows = 7;
        var simpleInventory = new SimpleInventory(9 * rows);
        return new GenericContainerScreenHandler(DIAMOND_CHEST, syncId, inventory, simpleInventory, rows);
    }
}
