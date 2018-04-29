package net.fusionlord.mods.betterwolfcontrol.client.init;

import com.google.common.collect.ImmutableList;
import net.fusionlord.mods.betterwolfcontrol.client.rendering.model.item.ItemOverridesWhistle;
import net.fusionlord.mods.betterwolfcontrol.common.config.Reference;
import net.fusionlord.mods.betterwolfcontrol.common.enums.Command;
import net.fusionlord.mods.betterwolfcontrol.common.init.ModItems;
import net.fusionlord.mods.betterwolfcontrol.common.items.ItemWhistle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ItemLayerModel;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collections;
import java.util.function.Function;

/**
 * Created by FusionLord on 4/2/2018.
 */

@Mod.EventBusSubscriber(modid = Reference.MODID, value = {Side.CLIENT})
public class Items {
    @SubscribeEvent
    public static void registerItemRenders(final ModelRegistryEvent event) {
        for (final Item item : ModItems.ITEMS) {
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName().toString(), "inventory"));
        }
    }

    @SubscribeEvent
    public static void bakeModels(final ModelBakeEvent event) {
        ItemLayerModel model = new ItemLayerModel(ImmutableList.of(Reference.getResource( "whistle")), new ItemOverridesWhistle());
        Function<ResourceLocation, TextureAtlasSprite> textureGetter = location -> Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());
        IBakedModel bakedModel = model.bake(model.getDefaultState(), DefaultVertexFormats.ITEM, textureGetter);
        event.getModelRegistry().putObject(new ModelResourceLocation(Reference.getResource("whistle"), "inventory"), bakedModel);
    }

    @SubscribeEvent
    public static void stitchTextures(final TextureStitchEvent.Pre event) {
        event.getMap().registerSprite(Reference.getResource("items/whistle/whistle"));
        for (Command command : Command.VALUES)
            event.getMap().registerSprite(Reference.getResource("items/whistle/command/" + command.name().toLowerCase()));
    }
}
