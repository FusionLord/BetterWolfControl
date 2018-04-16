package net.fusionlord.mods.betterwolfcontrol.common.init;

import net.fusionlord.mods.betterwolfcontrol.common.config.Reference;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

/**
 * Created by FusionLord on 4/2/2018.
 */
public enum CreativeTabs {
    MAIN(Reference.MODID);

    public static void register() {}

    public net.minecraft.creativetab.CreativeTabs tab;

    CreativeTabs(String tabName) {
        tab = new net.minecraft.creativetab.CreativeTabs(I18n.format("creativetab." + tabName, "")) {
            @Override
            public ItemStack getTabIconItem() {
                return new ItemStack(ModItems.WOLF_WHISTLE);
            }
        };
    }
}
