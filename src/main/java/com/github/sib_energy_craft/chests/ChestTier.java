package com.github.sib_energy_craft.chests;

import org.jetbrains.annotations.NotNull;

/**
 * @author sibmaks
 * @since 0.0.8
 */
public enum ChestTier {
    T0, // default minecraft single chest
    T1, // copper chest
    T2, // iron chest
    T3, // gold & silver chest
    T4; // diamond chest

    /**
     * Compare current tier and passed.
     *
     * @param other passed tier
     * @return true - current tier is greater than passed, false - otherwise
     */
    public boolean isGreater(@NotNull ChestTier other) {
        return ordinal() > other.ordinal();
    }
}
