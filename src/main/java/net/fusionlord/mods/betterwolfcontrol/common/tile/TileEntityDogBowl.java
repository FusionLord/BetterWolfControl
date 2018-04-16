package net.fusionlord.mods.betterwolfcontrol.common.tile;

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
    private float maxFood;

    public TileEntityDogBowl() {
        super();
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
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
}
