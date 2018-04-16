package net.fusionlord.mods.betterwolfcontrol.common.tile;

import net.fusionlord.mods.betterwolfcontrol.common.enums.Group;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by FusionLord on 4/15/2018.
 */
public class TileEntityDogBowl extends TileEntity {
    public static final Map<ItemFood, Float> VALIDFOODS = new HashMap<>();
    private float food;
    private float maxFood = 255;
    private Group group;

    public TileEntityDogBowl() {
        super();
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        group = Group.VALUES[compound.getInteger("group")];
        food = compound.getFloat("food");
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("group", group.ordinal());
        compound.setFloat("food", food);
        return super.writeToNBT(compound);
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return super.getUpdateTag();
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
        super.handleUpdateTag(tag);
    }

    public ItemStack addFood(ItemStack stack) {
        if (!VALIDFOODS.keySet().contains(stack.getItem())) return stack;
        float foodValue = VALIDFOODS.get(stack.getItem());
        if (food + foodValue > maxFood) return stack;
        int amount = Math.round((maxFood - food) / foodValue);
        food += foodValue * amount;
        stack.shrink(amount);
        return stack;
    }

    public float getFoodDisplay() {
        return food / maxFood;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
