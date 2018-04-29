package net.fusionlord.mods.betterwolfcontrol;

import net.fusionlord.mods.betterwolfcontrol.common.config.Reference;
import net.fusionlord.mods.betterwolfcontrol.common.network.NetworkHandler;
import net.fusionlord.mods.betterwolfcontrol.common.proxy.IProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MODID, name = Reference.MODNAME, version = Reference.VERSION)
public class BetterWolfControl {

    public static BetterWolfControl INSTANCE;
    @SidedProxy(modId = Reference.MODID, clientSide = Reference.CLIENTPROXY, serverSide = Reference.SERVERPROXY)
    public static IProxy proxy;
    public NetworkHandler networkHandler;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) { INSTANCE = this; proxy.preInit(event);}
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {proxy.init(event);}
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {proxy.postInit(event);}
}
