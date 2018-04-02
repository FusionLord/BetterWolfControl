package net.fusionlord.mods.betterwolfcontrol.common.items;

import net.fusionlord.mods.betterwolfcontrol.common.items.interfaces.IMouseWheelListener;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by FusionLord on 4/2/2018.
 */
public class ItemWolfWhistle extends Item implements IMouseWheelListener
{
    public enum WhistleCommand {
        SIT {
            @Override
            public void doAction(EntityPlayer player, List<EntityWolf> wolves) {
                for (EntityWolf wolf : wolves)
                    if (wolf.getOwner() == player)
                        wolf.aiSit.setSitting(true);
            }
        }, COME {
            @Override
            public void doAction(EntityPlayer player, List<EntityWolf> wolves) {
                for (EntityWolf wolf : wolves)
                    if (wolf.getOwner() == player)
                        wolf.aiSit.setSitting(false);
            }
        }, SPEAK {
            @Override
            public void doAction(EntityPlayer player, List<EntityWolf> wolves) {
                for (EntityWolf wolf : wolves)
                    if (wolf.getOwner() == player)
                        wolf.playLivingSound();
            }
        }, ;
        public static final WhistleCommand[] VALUES = WhistleCommand.values();

        public void doAction(EntityPlayer player) {
            List<EntityWolf> wolves = player.world.getEntitiesWithinAABB(EntityWolf.class, new AxisAlignedBB(player.getPosition()).grow(this == COME ? 50 : 10));
            doAction(player, wolves);
        }

        abstract public void doAction(EntityPlayer player, List<EntityWolf> wolves);
    }

    public ItemWolfWhistle() {
        super();
        maxStackSize = 1;

        addPropertyOverride(new ResourceLocation("command"), (stack, world, entity) -> {
            if (!stack.hasTagCompound()) createDefaultTag(stack);
            return stack.getTagCompound().getInteger("command");
        });
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        if (!stack.hasTagCompound()) createDefaultTag(stack);
        WhistleCommand command = WhistleCommand.VALUES[stack.getTagCompound().getInteger("command")];
        return I18n.format(getUnlocalizedName() + ".display",
                I18n.format(getUnlocalizedName() + ".command." + command.name().toLowerCase(), ""));
    }

    @Override
    public void onWheel(ItemStack stack, int amount) {
        changeCommand(stack, amount > 0);
    }

    @SuppressWarnings("ConstantConditions")
    private void changeCommand(ItemStack stack, boolean up) {
        if (!stack.hasTagCompound()) createDefaultTag(stack);
        int current = stack.getTagCompound().getInteger("command");
        int next = current + (up ? 1 : -1);
        if (next < 0) next = WhistleCommand.VALUES.length - 1;
        else if (next >= WhistleCommand.VALUES.length) next = 0;
        stack.getTagCompound().setInteger("command", next);
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
        if (!stack.hasTagCompound()) createDefaultTag(stack);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (world.isRemote) return new ActionResult(EnumActionResult.SUCCESS, stack);
        if (!stack.hasTagCompound()) createDefaultTag(stack);
        int current = stack.getTagCompound().getInteger("command");
        WhistleCommand.VALUES[current].doAction(player);
        return new ActionResult(EnumActionResult.SUCCESS, stack);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (world.isRemote) return EnumActionResult.SUCCESS;
        ItemStack stack = player.getHeldItem(hand);
        if (!stack.hasTagCompound()) createDefaultTag(stack);
        int current = stack.getTagCompound().getInteger("command");
        WhistleCommand.VALUES[current].doAction(player);
        return EnumActionResult.SUCCESS;
    }

    private void createDefaultTag(ItemStack stack) {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("command", 0);
        stack.setTagCompound(tag);
    }
}
