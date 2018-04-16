package net.fusionlord.mods.betterwolfcontrol.common.items;

import net.fusionlord.mods.betterwolfcontrol.common.config.Reference;
import net.fusionlord.mods.betterwolfcontrol.common.init.CreativeTabs;
import net.fusionlord.mods.betterwolfcontrol.common.items.interfaces.IMouseWheelListener;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

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
        }, GROUP {
            @Override
            public void doAction(EntityPlayer player) {
                ItemStack stack = player.getHeldItemMainhand();
                ((ItemWolfWhistle)stack.getItem()).changeGroup(stack, !player.isSneaking());
            }
        } ;
        public static final WhistleCommand[] VALUES = WhistleCommand.values();

        public void doAction(EntityPlayer player) {
            WhistleGroup group = WhistleGroup.VALUES[player.getHeldItemMainhand().getTagCompound().getInteger("group")];
            List<EntityWolf> wolves = player.world.getEntitiesWithinAABB(EntityWolf.class, new AxisAlignedBB(player.getPosition()).grow(this == COME ? 50 : 10));
            wolves = wolves.stream().filter(e->group == WhistleGroup.ALL || e.getCollarColor() == group.DYE).collect(Collectors.toList());
            doAction(player, wolves);
        }

        public void doAction(EntityPlayer player, List<EntityWolf> wolves) {}
    }

    public enum WhistleGroup {
        ALL(EnumDyeColor.WHITE, TextFormatting.WHITE),
        WHITE(EnumDyeColor.WHITE, TextFormatting.WHITE),
        ORANGE(EnumDyeColor.ORANGE, TextFormatting.GOLD),
        MAGENTA(EnumDyeColor.MAGENTA, TextFormatting.LIGHT_PURPLE),
        LIGHT_BLUE(EnumDyeColor.LIGHT_BLUE, TextFormatting.BLUE),
        YELLOW(EnumDyeColor.YELLOW, TextFormatting.YELLOW),
        LIME(EnumDyeColor.LIME, TextFormatting.GREEN),
        PINK(EnumDyeColor.PINK, TextFormatting.LIGHT_PURPLE),
        GRAY(EnumDyeColor.GRAY, TextFormatting.DARK_GRAY),
        SILVER(EnumDyeColor.SILVER, TextFormatting.GRAY),
        CYAN(EnumDyeColor.CYAN, TextFormatting.DARK_AQUA),
        PURPLE(EnumDyeColor.PURPLE, TextFormatting.DARK_PURPLE),
        BLUE(EnumDyeColor.BLUE, TextFormatting.DARK_BLUE),
        BROWN(EnumDyeColor.BROWN, TextFormatting.GOLD),
        GREEN(EnumDyeColor.GREEN, TextFormatting.DARK_GREEN),
        RED(EnumDyeColor.RED, TextFormatting.DARK_RED),
        BLACK(EnumDyeColor.BLACK, TextFormatting.BLACK);

        public static final WhistleGroup[] VALUES = values();

        public EnumDyeColor DYE;
        public TextFormatting TEXT;

        WhistleGroup(EnumDyeColor dye, TextFormatting text) {
            DYE = dye;
            TEXT = text;
        }
    }

    public ItemWolfWhistle() {
        super();
        setRegistryName(Reference.getResource("whistle"));
        setUnlocalizedName(getRegistryName().toString());
        setCreativeTab(CreativeTabs.MAIN.tab);

        maxStackSize = 1;

        addPropertyOverride(Reference.getResource("command"), (stack, world, entity) -> {
            if (!stack.hasTagCompound()) createDefaultTag(stack);
            float f = (float)stack.getTagCompound().getInteger("command") / 100f;
            return f;
        });
        addPropertyOverride(Reference.getResource("group"), (stack, world, entity) -> {
            if (!stack.hasTagCompound()) createDefaultTag(stack);
            float f = (float)stack.getTagCompound().getInteger("group") / 100f;
            return f;
        });
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        if (!stack.hasTagCompound()) createDefaultTag(stack);
        WhistleCommand command = WhistleCommand.VALUES[stack.getTagCompound().getInteger("command")];
        WhistleGroup group = WhistleGroup.VALUES[stack.getTagCompound().getInteger("group")];
        return I18n.format(getUnlocalizedName() + ".display",
                I18n.format(getUnlocalizedName() + ".command." + command.name().toLowerCase()),
                (group.TEXT + I18n.format(getUnlocalizedName() + ".command.group." + group.name().toLowerCase()) + TextFormatting.WHITE));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        if (!stack.hasTagCompound()) createDefaultTag(stack);
        WhistleCommand command = WhistleCommand.VALUES[stack.getTagCompound().getInteger("command")];
        WhistleGroup group = WhistleGroup.VALUES[stack.getTagCompound().getInteger("group")];
        tooltip.add(I18n.format(getUnlocalizedName() + ".lore1", I18n.format(getUnlocalizedName() + ".command." + command.name().toLowerCase())));
        tooltip.add(I18n.format(getUnlocalizedName() + ".lore2", (group.TEXT + I18n.format(getUnlocalizedName() + ".command.group." + group.name().toLowerCase()) + TextFormatting.WHITE)));
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

    private void changeGroup(ItemStack stack, boolean up) {
        if (!stack.hasTagCompound()) createDefaultTag(stack);
        int current = stack.getTagCompound().getInteger("group");
        int next = current + (up ? 1 : -1);
        if (next < 0) next = WhistleGroup.VALUES.length - 1;
        else if (next >= WhistleGroup.VALUES.length) next = 0;
        stack.getTagCompound().setInteger("group", next);
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

    public void createDefaultTag(ItemStack stack) {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("command", 0);
        tag.setInteger("group", 0);
        stack.setTagCompound(tag);
    }


}
