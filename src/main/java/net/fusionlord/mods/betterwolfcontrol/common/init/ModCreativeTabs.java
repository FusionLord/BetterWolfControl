package net.fusionlord.mods.betterwolfcontrol.common.init;

import com.google.common.collect.Lists;
import net.fusionlord.mods.betterwolfcontrol.common.config.Reference;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

/**
 * Created by FusionLord on 4/2/2018.
 */
@Mod.EventBusSubscriber(modid = Reference.MODID)
public class ModCreativeTabs {
    public static final List<CreativeTabs> VALUES = Lists.newArrayList();

    public static final CreativeTabs MAIN = new CreativeTabs(Reference.getResource("creativetab.main").toString()) {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ModItems.WOLF_WHISTLE);
        }
    };
}
