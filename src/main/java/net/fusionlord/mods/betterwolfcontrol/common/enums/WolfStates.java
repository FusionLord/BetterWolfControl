package net.fusionlord.mods.betterwolfcontrol.common.enums;

import net.fusionlord.mods.betterwolfcontrol.common.config.Reference;

/**
 * Created by FusionLord on 4/6/2018.
 */
public enum WolfStates {
    AGGRESSIVE,
    PASSIVE,
    DEFENSIVE;

    public static final WolfStates[] VALUES = values();

    public String getUnlocalizedString() {
        return Reference.getResource("wolfstate." + name().toLowerCase()).toString();
    }

    public static WolfStates next(WolfStates wolfStates) {
        int id = wolfStates.ordinal();
        return VALUES[++id > VALUES.length-1 ? 0 : id];
    }

    public static WolfStates prev(WolfStates wolfStates) {
        int id = wolfStates.ordinal();
        return VALUES[--id < 0 ? VALUES.length-1 : id];
    }
}
