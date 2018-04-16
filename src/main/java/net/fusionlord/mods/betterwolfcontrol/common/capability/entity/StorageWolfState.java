package net.fusionlord.mods.betterwolfcontrol.common.capability.entity;

import net.fusionlord.mods.betterwolfcontrol.common.enums.WolfStates;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;
import java.util.concurrent.Callable;

/**
 * Created by FusionLord on 4/6/2018.
 */
public class StorageWolfState implements Capability.IStorage<IWolfState> {

    @Nullable
    @Override
    public NBTBase writeNBT(Capability<IWolfState> capability, IWolfState instance, EnumFacing side) {
        return new NBTTagInt(instance.get().ordinal());
    }

    @Override
    public void readNBT(Capability<IWolfState> capability, IWolfState instance, EnumFacing side, NBTBase nbt) {
        instance.set(WolfStates.VALUES[((NBTTagInt)nbt).getInt()]);
    }
}

interface IWolfState {
    WolfStates get();
    WolfStates set(WolfStates newState);
}

class WolfState implements IWolfState {
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