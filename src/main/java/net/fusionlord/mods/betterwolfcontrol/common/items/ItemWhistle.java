package net.fusionlord.mods.betterwolfcontrol.common.items;

import net.fusionlord.mods.betterwolfcontrol.common.config.Reference;
import net.fusionlord.mods.betterwolfcontrol.common.enums.Command;
import net.fusionlord.mods.betterwolfcontrol.common.enums.Group;
import net.fusionlord.mods.betterwolfcontrol.common.items.interfaces.IMouseWheelListener;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by FusionLord on 4/2/2018.
 */
public class ItemWhistle extends Item implements IMouseWheelListener {
    public ItemWhistle() {
        super();
        setRegistryName(Reference.getResource("whistle"));
        setUnlocalizedName(getRegistryName().toString());

        bFull3D = true;

        maxStackSize = 1;

        addPropertyOverride(Reference.getResource("command"), (stack, world, entity) -> (float) verifyCompound(stack).getTagCompound().getInteger("command") / 100f);
        addPropertyOverride(Reference.getResource("group"), (stack, world, entity) -> (float) verifyCompound(stack).getTagCompound().getInteger("group") / 100f);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return getGroup(stack).TEXT + I18n.translateToLocal(getUnlocalizedName() + ".display");
    }

    private static ItemStack verifyCompound(ItemStack stack) {
        if (!stack.hasTagCompound()){
            createDefaultTag(stack);
            return stack;
        }
        int command = stack.getTagCompound().getInteger("command");
        int group = stack.getTagCompound().getInteger("group");
        if (command < 0 || command >= Command.VALUES.length || group < 0 || group >= Group.VALUES.length)
            createDefaultTag(stack);
        return stack;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        verifyCompound(stack);
        Command command = getCommand(stack);
        Group group = getGroup(stack);
        tooltip.add(String.format(I18n.translateToLocal(getUnlocalizedName() + ".lore1"), I18n.translateToLocal(command.getUnlocalizedString())));
        tooltip.add(String.format(I18n.translateToLocal(getUnlocalizedName() + ".lore2"), I18n.translateToLocal(group.getUnlocalizedString())));
    }

    @Override
    public void onWheel(ItemStack stack, int amount, boolean modifier) {
        changeWhistle(stack, amount > 0, modifier);
    }

    @SuppressWarnings("ConstantConditions")
    private void changeWhistle(ItemStack stack, boolean up, boolean modifier) {
        verifyCompound(stack);
        int current;
        int next;
        if (!modifier) {
            current = stack.getTagCompound().getInteger("command");
             next = current + (up ? 1 : -1);
            if (next < 0) next = Command.VALUES.length - 1;
            else if (next >= Command.VALUES.length) next = 0;
            stack.getTagCompound().setInteger("command", next);
        } else {
            current = stack.getTagCompound().getInteger("group");
            next = current + (up ? 1 : -1);
            if (next < 0) next = Group.VALUES.length - 1;
            else if (next >= Group.VALUES.length) next = 0;
            stack.getTagCompound().setInteger("group", next);
        }
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) { verifyCompound(stack); }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (world.isRemote) return new ActionResult(EnumActionResult.SUCCESS, stack);
        verifyCompound(stack);
        int command = stack.getTagCompound().getInteger("command");
        Command.VALUES[command].doAction(player);
        return new ActionResult(EnumActionResult.SUCCESS, stack);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (world.isRemote) return EnumActionResult.SUCCESS;
        ItemStack stack = player.getHeldItem(hand);
        verifyCompound(stack);
        int command = stack.getTagCompound().getInteger("command");
        Command.VALUES[command].doAction(player);
        return EnumActionResult.SUCCESS;
    }

    public static void createDefaultTag(ItemStack stack) {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("command", 0);
        tag.setInteger("group", 0);
        stack.setTagCompound(tag);
    }

    public static Group getGroup(ItemStack stack) {
        return Group.VALUES[verifyCompound(stack).getTagCompound().getInteger("group")];
    }
    public static Command getCommand(ItemStack stack) {
        return Command.VALUES[verifyCompound(stack).getTagCompound().getInteger("command")];
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return !(oldStack.getItem() == newStack.getItem() && (getCommand(oldStack) == getCommand(newStack) || getGroup(oldStack) == getGroup(newStack)));
    }
}
