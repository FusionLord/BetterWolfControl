package net.fusionlord.mods.betterwolfcontrol.client.proxy;

import net.fusionlord.mods.betterwolfcontrol.client.events.MouseEvent;
import net.fusionlord.mods.betterwolfcontrol.common.proxy.CommonProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;


/**
 * Created by FusionLord on 4/2/2018.
 */
public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        MinecraftForge.EVENT_BUS.register(new MouseEvent());
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

    @Override
    public World getWorld(int dim) {
        return Minecraft.getMinecraft().world;
    }

    @Override
    public Side getSide() {
        return Side.CLIENT;
    }
}
