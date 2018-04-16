package net.fusionlord.mods.betterwolfcontrol.client.rendering.model.items;

import com.google.common.collect.ImmutableMap;
import net.fusionlord.mods.betterwolfcontrol.common.config.Reference;
import net.fusionlord.mods.betterwolfcontrol.common.items.ItemWhistle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverride;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ItemLayerModel;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

public class ItemOverridesWhistle extends ItemOverrideList {
    private Map<ItemWhistle.Command, IBakedModel> modelCache = new HashMap<>();
    public static final Function<ResourceLocation, TextureAtlasSprite> textureGetter = location -> Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());

    public ItemOverridesWhistle(List<ItemOverride> overridesIn) {
        super(overridesIn);
    }

    @Override
    public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity) {
        ItemWhistle.Command command = ItemWhistle.getCommand(stack);

        IBakedModel cachedModel = modelCache.get(command);
        if (cachedModel != null) {
            return cachedModel;
        }

        ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
        builder.put("layer0", Reference.MODID + ":items/whistle/whistle");
        builder.put("layer1", Reference.MODID + ":items/whistle/command/" + command.name().toLowerCase(Locale.ENGLISH));
        ItemLayerModel newModel = ItemLayerModel.INSTANCE.retexture(builder.build());
        cachedModel = newModel.bake(newModel.getDefaultState(), DefaultVertexFormats.ITEM, textureGetter);
        modelCache.put(command, cachedModel);
        return cachedModel;
    }
}