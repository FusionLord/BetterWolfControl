package net.fusionlord.mods.betterwolfcontrol.common.capability.entity;

import net.fusionlord.mods.betterwolfcontrol.common.enums.WolfStates;

/**
 * Created by FusionLord on 4/12/2018.
 */
public interface IWolfState {
    WolfStates get();
    WolfStates set(WolfStates newState);
}
