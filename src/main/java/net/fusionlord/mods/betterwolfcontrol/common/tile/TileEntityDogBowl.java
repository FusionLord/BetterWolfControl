package net.fusionlord.mods.betterwolfcontrol.common.tile;

import net.fusionlord.mods.betterwolfcontrol.common.capability.entity.ProviderWolfState;
import net.fusionlord.mods.betterwolfcontrol.common.enums.Group;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by FusionLord on 4/15/2018.
 */
public class TileEntityDogBowl extends TileEntity {
    public static final Map<Item, Float> VALIDFOODS = new HashMap<>();
    private UUID owner;
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
        if (compound.hasKey("owner"))
            NBTUtil.getUUIDFromTag(compound.getCompoundTag("owner"));
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
        if (owner != null)
            compound.setTag("owner", NBTUtil.createUUIDTag(owner));
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
        IBlockState state = world.getBlockState(pos);
        world.notifyBlockUpdate(pos, state, state.getActualState(world, pos), 255);
        System.out.println("called!");
    }

    public int addFood(ItemStack stack) {
        if (!isValidFood(stack)) return 0;
        float foodValue = VALIDFOODS.get(stack.getItem());
        if (food + foodValue > maxFood) return 0;
        int amount = Math.round((maxFood - food) / foodValue);
        amount = (int) map(amount, 0, stack.getCount());
        food += foodValue * amount;
        stack.shrink(amount);
        sync();
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
        sync();
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public void sync() {
        if (!world.isRemote) {
            world.getEntitiesWithinAABB(EntityPlayerMP.class, new AxisAlignedBB(pos).grow(64d))
                    .forEach(e -> e.connection.sendPacket(getUpdatePacket()));
        }
    }

    public float getMaxFood() {
        return maxFood;
    }

    public float getFood() {
        return food;
    }

    public static boolean isValidFood(ItemStack stack) {
        return VALIDFOODS.containsKey(stack.getItem());
    }

    public void creativeFill() {
        this.food = maxFood;
        sync();
    }

    public boolean canPlayerInteract(EntityPlayer playerIn) {
        return owner == null || owner == playerIn.getPersistentID();
    }

    public void setFood(float food) {
        this.food = food;
    }

    public UUID getOwner() {
        return owner;
    }

    public boolean hasOwner() {
        return owner != null;
    }

    public float eat(int i) {
        float a = i > food ? food : i;
        food -= a;
        return a;
    }

    public boolean canEat(EntityWolf wolf) {
        return (this.owner == null || this.owner.equals(wolf.getOwnerId())) && (group == Group.ALL || group.DYE == wolf.getCollarColor());
    }
}
