package net.fusionlord.mods.betterwolfcontrol.common.proxy;

import net.fusionlord.mods.betterwolfcontrol.BetterWolfControl;
import net.fusionlord.mods.betterwolfcontrol.common.capability.entity.*;
import net.fusionlord.mods.betterwolfcontrol.common.init.ModCreativeTabs;
import net.fusionlord.mods.betterwolfcontrol.common.network.NetworkHandler;

import net.fusionlord.mods.betterwolfcontrol.common.tile.TileEntityDogBowl;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by FusionLord on 4/2/2018.
 */
public class CommonProxy implements IProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        BetterWolfControl.INSTANCE.networkHandler = new NetworkHandler();
    }

    @Override
    public void init(FMLInitializationEvent event) {
        CapabilityManager.INSTANCE.register(IWolfState.class, new StorageWolfState(), WolfState.class);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        ForgeRegistries.ITEMS.getValuesCollection().stream().filter(i -> i instanceof ItemFood).map(ItemFood.class::cast).collect(Collectors.toList()).forEach(i -> TileEntityDogBowl.VALIDFOODS.put(i, (float)i.getHealAmount(ItemStack.EMPTY)));
    }

    @Override
    public World getWorld(int dim) {
        return FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(dim);
    }

    @Override
    public Side getSide() {
        return Side.SERVER;
    }
}
