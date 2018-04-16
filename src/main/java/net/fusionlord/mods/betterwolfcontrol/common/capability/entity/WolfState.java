package net.fusionlord.mods.betterwolfcontrol.common.capability.entity;

import net.fusionlord.mods.betterwolfcontrol.common.enums.WolfStates;

/**
 * Created by FusionLord on 4/12/2018.
 */
public class WolfState implements IWolfState {
    private WolfStates state = WolfStates.AGGRESSIVE;

    @Override
    public WolfStates get() {
        return state;
    }

    @Override
    public WolfStates set(WolfStates newState) {
        WolfStates oldState = state;
        state = newState;
        return oldState;
    }
}
