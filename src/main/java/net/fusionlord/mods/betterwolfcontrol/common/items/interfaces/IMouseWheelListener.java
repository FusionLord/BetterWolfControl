package net.fusionlord.mods.betterwolfcontrol.common.items.interfaces;

import net.minecraft.item.ItemStack;

/**
 * Created by FusionLord on 4/2/2018.
 */
public interface IMouseWheelListener {
    void onWheel(ItemStack stack, int amount, boolean modifier);
}
