package com.github.sib_energy_craft.chests.client.load;

import com.github.sib_energy_craft.chests.load.ScreenHandlers;
import com.github.sib_energy_craft.chests.screen.Container9x7Screen;
import com.github.sib_energy_craft.sec_utils.load.DefaultClientModInitializer;

import static com.github.sib_energy_craft.sec_utils.utils.ScreenUtils.registerScreen;

/**
 * @since 0.0.1
 * @author sibmaks
 */
public final class Screens implements DefaultClientModInitializer {

    static {
        registerScreen(ScreenHandlers.DIAMOND_CHEST, Container9x7Screen::new);
    }

}
