package net.fusionlord.mods.betterwolfcontrol.client.rendering.model.items;

import net.fusionlord.mods.betterwolfcontrol.common.items.ItemWhistle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;

import java.awt.*;

/**
 * Created by FusionLord on 4/14/2018.
 */
public class ItemColorWhistle implements IItemColor {
    private float speed = 64f; //Number of colors to go through.
    private float interval = 255f / speed;
    @Override
    public int colorMultiplier(ItemStack stack, int tintIndex) {
        if (stack.getItem() instanceof ItemWhistle && tintIndex == 0)
            if (ItemWhistle.getGroup(stack) == ItemWhistle.Group.ALL)
                return Color.HSBtoRGB((float) Minecraft.getMinecraft().player.ticksExisted % interval / interval * speed, .5f, .5f);
            else
                return ItemWhistle.getGroup(stack).DYE.getColorValue();
        return 0xFFFFFF;
    }
}