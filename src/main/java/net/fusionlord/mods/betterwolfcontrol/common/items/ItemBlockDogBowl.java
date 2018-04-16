package net.fusionlord.mods.betterwolfcontrol.common.items;

import net.fusionlord.mods.betterwolfcontrol.common.config.Reference;
import net.fusionlord.mods.betterwolfcontrol.common.enums.Group;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
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
        setHasSubtypes(true);
    }

    @Override
    public String getUnlocalizedName() {
        return "item.".concat(Reference.getResource("dogbowl").toString());
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return I18n.format(getUnlocalizedName(), Group.getFromMetaData(stack.getMetadata()).getDisplayString());
    }



    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (tab != getCreativeTab()) return;
        for(Group color : Group.VALUES) {
            items.add(new ItemStack(this, 1, color.ordinal()));
        }
    }
}
