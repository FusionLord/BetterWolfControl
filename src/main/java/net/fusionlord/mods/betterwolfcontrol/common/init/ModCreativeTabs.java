package net.fusionlord.mods.betterwolfcontrol.common.init;

import net.fusionlord.mods.betterwolfcontrol.common.config.Reference;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

/**
 * Created by FusionLord on 4/2/2018.
 */
public enum ModCreativeTabs {
    MAIN(Reference.MODID);

    public static void register() {}

    public CreativeTabs tab;

    ModCreativeTabs(String tabName) {
        tab = new CreativeTabs(I18n.format("creativetab." + tabName, "")) {
            @Override
            public ItemStack getTabIconItem() {
                return new ItemStack(ModItems.WOLF_WHISTLE);
            }
        };
    }
}
