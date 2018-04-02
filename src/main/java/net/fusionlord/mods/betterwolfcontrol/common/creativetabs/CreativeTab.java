package net.fusionlord.mods.betterwolfcontrol.common.creativetabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

/**
 * Created by FusionLord on 4/2/2018.
 */
public class CreativeTab extends CreativeTabs {
    private ItemStack displayStack;

    public CreativeTab(String label) {
        super(label);
    }

    @Override
    public ItemStack getTabIconItem() {
        return displayStack;
    }

    public void setDisplayStack(ItemStack displayStack) {
        this.displayStack = displayStack;
    }
}
