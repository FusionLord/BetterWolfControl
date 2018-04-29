package net.fusionlord.mods.betterwolfcontrol.common.items;

import net.fusionlord.mods.betterwolfcontrol.common.enums.Group;
import net.minecraft.block.Block;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

/**
 * Created by FusionLord on 4/15/2018.
 */
public class ItemBlockDogBowl extends ItemBlock {
    public ItemBlockDogBowl(Block block) {
        super(block);
        setRegistryName(block.getRegistryName());
        setUnlocalizedName(getRegistryName().toString());
        setHasSubtypes(true);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return String.format(I18n.translateToLocal(getUnlocalizedName()), I18n.translateToLocal(Group.getFromMetaData(stack.getMetadata()).getUnlocalizedString()));
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (tab != getCreativeTab()) return;
        for(Group color : Group.VALUES) {
            items.add(new ItemStack(this, 1, color.ordinal()));
        }
    }
}
