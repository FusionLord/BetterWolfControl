package net.fusionlord.mods.betterwolfcontrol.common.proxy;

import net.fusionlord.mods.betterwolfcontrol.BetterWolfControl;
import net.fusionlord.mods.betterwolfcontrol.common.init.Blocks;
import net.fusionlord.mods.betterwolfcontrol.common.init.CreativeTabs;
import net.fusionlord.mods.betterwolfcontrol.common.init.Items;
import net.fusionlord.mods.betterwolfcontrol.common.network.NetworkHandler;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

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
        CreativeTabs.register();
        Blocks.register();
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

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
