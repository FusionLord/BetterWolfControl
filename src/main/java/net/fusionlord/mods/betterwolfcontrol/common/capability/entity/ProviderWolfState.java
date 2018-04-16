package net.fusionlord.mods.betterwolfcontrol.common.capability.entity;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by FusionLord on 4/12/2018.
 */
public class ProviderWolfState implements ICapabilitySerializable<NBTBase> {
    @CapabilityInject(IWolfState.class)
    public static final Capability<IWolfState> WOLF_STATE_CAP = null;

    private IWolfState INSTANCE = WOLF_STATE_CAP.getDefaultInstance();

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == WOLF_STATE_CAP;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == WOLF_STATE_CAP ? WOLF_STATE_CAP.cast(INSTANCE) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return WOLF_STATE_CAP.getStorage().writeNBT(WOLF_STATE_CAP, INSTANCE, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        WOLF_STATE_CAP.getStorage().readNBT(WOLF_STATE_CAP, INSTANCE, null, nbt);
    }

}
