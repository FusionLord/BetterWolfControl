package net.fusionlord.mods.betterwolfcontrol.common.init;

import net.fusionlord.mods.betterwolfcontrol.common.config.Reference;
import net.fusionlord.mods.betterwolfcontrol.common.creativetabs.CreativeTab;
import net.minecraft.client.resources.I18n;

/**
 * Created by FusionLord on 4/2/2018.
 */
public enum CreativeTabs {
    MAIN(Reference.MODID);

    public static void register() {}

    net.minecraft.creativetab.CreativeTabs tab;

    CreativeTabs(String tabName) {
        tab = new CreativeTab(I18n.format("creativetab." + tabName, ""));
    }
}
