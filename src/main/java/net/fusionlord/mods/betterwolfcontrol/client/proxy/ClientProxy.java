package net.fusionlord.mods.betterwolfcontrol.client.proxy;

import net.fusionlord.mods.betterwolfcontrol.client.events.EventOnMouse;
import net.fusionlord.mods.betterwolfcontrol.client.rendering.model.entity.BetterWolfLayerRenderer;
import net.fusionlord.mods.betterwolfcontrol.client.rendering.model.item.ItemColorWhistle;
import net.fusionlord.mods.betterwolfcontrol.client.rendering.model.tile.TileEntitySpecialRenderDogBowl;
import net.fusionlord.mods.betterwolfcontrol.common.init.ModItems;
import net.fusionlord.mods.betterwolfcontrol.common.proxy.CommonProxy;
import net.fusionlord.mods.betterwolfcontrol.common.tile.TileEntityDogBowl;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderWolf;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by FusionLord on 4/2/2018.
 */
@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber
public class ClientProxy extends CommonProxy {
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new ItemColorWhistle(), ModItems.WOLF_WHISTLE);
        MinecraftForge.EVENT_BUS.register(new EventOnMouse());
        Render<EntityWolf> wolfRender = Minecraft.getMinecraft().getRenderManager().getEntityClassRenderObject(EntityWolf.class);
        if (wolfRender instanceof RenderWolf)
            ((RenderWolf) wolfRender).addLayer(new BetterWolfLayerRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDogBowl.class, new TileEntitySpecialRenderDogBowl());
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
