package net.fusionlord.mods.betterwolfcontrol.client.init;

import net.fusionlord.mods.betterwolfcontrol.common.config.Reference;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by FusionLord on 4/2/2018.
 */
@Mod.EventBusSubscriber(modid = Reference.MODID)
public class Items {
    @SubscribeEvent
    public static void registerItemRenders(final ModelRegistryEvent event) {
        for (final Item item : net.fusionlord.mods.betterwolfcontrol.common.init.Items.ITEMS) {
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName().toString()));
        }
    }
}
