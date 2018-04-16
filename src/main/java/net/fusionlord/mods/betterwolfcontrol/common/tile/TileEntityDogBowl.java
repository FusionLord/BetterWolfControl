package net.fusionlord.mods.betterwolfcontrol.common.tile;

import net.fusionlord.mods.betterwolfcontrol.common.enums.Group;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by FusionLord on 4/15/2018.
 */
public class TileEntityDogBowl extends TileEntity {
    public static final Map<ItemFood, Float> VALIDFOODS = new HashMap<>();
    private float food = 0f;
    private float maxFood = 255f;
    private Group group;

    public TileEntityDogBowl() {
        super();
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        readData(compound);
    }

    private void readData(NBTTagCompound compound) {
        int i = compound.getInteger("group");
        if (i < 0 || i >= Group.VALUES.length) i = 0;
        group = Group.VALUES[i];
        food = compound.getFloat("food");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        return writeData(compound);
    }

    private NBTTagCompound writeData() {
        return writeData(new NBTTagCompound());
    }

    private NBTTagCompound writeData(NBTTagCompound compound) {
        compound.setInteger("group", group.ordinal());
        compound.setFloat("food", food);
        return compound;
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound tag = new NBTTagCompound();
        writeData(tag);
        return new SPacketUpdateTileEntity(getPos(), 0, writeData());
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(writeData());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        readData(pkt.getNbtCompound());
    }

    public int addFood(ItemStack stack) {
        if (!VALIDFOODS.keySet().contains(stack.getItem())) return 0;
        float foodValue = VALIDFOODS.get(stack.getItem());
        if (food + foodValue > maxFood) return 0;
        int amount = Math.round((maxFood - food) / foodValue);
        amount = (int) map(amount, 0, stack.getCount());
        food += foodValue * amount;
        stack.shrink(amount);
        return amount;
    }

    private float map(float x, float y, float z) {
        if (x < y) return y;
        if (x > z) return z;
        return x;
    }

    public float getFoodDisplay() {
        return food / maxFood;
    }

    public Group getGroup() {
        if (group == null) group = Group.ALL;
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public float getMaxFood() {
        return maxFood;
    }

    public float getFood() {
        return food;
    }
}
