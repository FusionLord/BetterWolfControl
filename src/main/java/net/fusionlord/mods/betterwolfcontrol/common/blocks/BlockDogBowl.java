package net.fusionlord.mods.betterwolfcontrol.common.blocks;

import com.google.common.collect.Lists;
import net.fusionlord.mods.betterwolfcontrol.common.blocks.intefaces.ICustomItemBlock;
import net.fusionlord.mods.betterwolfcontrol.common.config.Reference;
import net.fusionlord.mods.betterwolfcontrol.common.enums.Group;
import net.fusionlord.mods.betterwolfcontrol.common.items.ItemBlockDogBowl;
import net.fusionlord.mods.betterwolfcontrol.common.tile.TileEntityDogBowl;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

/**
 * Created by FusionLord on 4/15/2018.
 */
public class BlockDogBowl extends Block {
    private static final PropertyEnum<Group> GROUP = PropertyEnum.create("group", Group.class);
    private static final AxisAlignedBB box1 = new AxisAlignedBB(0.1875f, 0f, 0.1875f, 0.8125f, 0.0625f, 0.8125f);
    private static final AxisAlignedBB box2 = new AxisAlignedBB(0.25f, 0.0625f, 0.25f, 0.75f, 0.125f, 0.75f);
    private static final AxisAlignedBB box3 = new AxisAlignedBB(0.3125f, 0.125f, 0.3125f, 0.6875f, 0.1875f, 0.6875f);
    private static final AxisAlignedBB bb = new AxisAlignedBB(0.1875f, 0f, 0.1875f, 0.8125f, 0.1875f, 0.8125f);

    public BlockDogBowl() {
        super(Material.IRON, MapColor.IRON);
        this.setRegistryName(Reference.getResource("dogbowl"));
        this.setUnlocalizedName(getRegistryName().toString());
        this.setDefaultState(blockState.getBaseState().withProperty(GROUP, Group.ALL));
        this.setHardness(0.25f);
        TileEntity.register(getRegistryName().toString().concat("_tileentity"), TileEntityDogBowl.class);
    }

    @Override
    public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player) {
        return true;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, GROUP);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityDogBowl();
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        TileEntity te = worldIn.getTileEntity(pos);
        if (!(te instanceof TileEntityDogBowl)) return;
        TileEntityDogBowl bowl = (TileEntityDogBowl) te;
        bowl.setGroup(Group.VALUES[stack.getMetadata()]);
        if (placer instanceof EntityPlayer)
            bowl.setOwner(placer.getPersistentID());
        if (stack.getTagCompound() != null) {
            bowl.setFood(stack.getTagCompound().getFloat("food"));
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntity te = worldIn.getTileEntity(pos);
        if (!(te instanceof TileEntityDogBowl))
            return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
        TileEntityDogBowl bowl = (TileEntityDogBowl)te;
        if (!bowl.canPlayerInteract(playerIn)) return true;
        ItemStack stack = playerIn.getHeldItem(hand);
        if (stack.getItem() == Items.DYE && state.getValue(GROUP) != Group.ALL) {
            bowl.setGroup(Group.getFromDye(EnumDyeColor.byDyeDamage(stack.getMetadata())));
            stack.shrink(1);
            return true;
        }
        if (TileEntityDogBowl.isValidFood(stack)) {
            if (playerIn.capabilities.isCreativeMode) {
                bowl.creativeFill();
                return true;
            }
            stack.shrink(bowl.addFood(stack));
            sendStatus(playerIn, bowl);
            return true;
        }
        sendStatus(playerIn, bowl);
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }

    private void sendStatus(EntityPlayer player, TileEntityDogBowl bowl) {
        if (player.world.isRemote) return;
        String message = String.format(I18n.translateToLocal(Reference.getResource("foodlevel").toString()), String.valueOf(bowl.getFoodDisplay() * 100).concat("%"), String.valueOf(bowl.getFood()), String.valueOf(bowl.getMaxFood()));
        player.sendStatusMessage(new TextComponentTranslation(message), true);
        TextComponentTranslation trans = new TextComponentTranslation("betterwolfcontrol:command.come.afsdf");
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        TileEntityDogBowl bowl = (TileEntityDogBowl)worldIn.getTileEntity(pos);
        if (bowl == null) {
            return getDefaultState();
        }
        return state.withProperty(GROUP, bowl.getGroup());
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState();
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    @Override
    public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        return false;
    }

    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(this, 1, getActualState(state,world,pos).getValue(GROUP).ordinal());
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        return willHarvest || super.removedByPlayer(state, world, pos, player, willHarvest);
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        ItemStack stack = new ItemStack(this, 1, getActualState(state,world,pos).getValue(GROUP).ordinal());

        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileEntityDogBowl) {
            TileEntityDogBowl bowl = (TileEntityDogBowl)te;
            if (bowl.getFood() > 0) {
                NBTTagCompound tag = new NBTTagCompound();
                tag.setFloat("food", bowl.getFood());
                stack.setTagCompound(tag);
            }
        }

        List<ItemStack> drops = Lists.newArrayList();
        drops.add(stack);
        return drops;
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        super.harvestBlock(worldIn, player, pos, state, te, stack);
        worldIn.setBlockToAir(pos);
    }



    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return null;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, box1);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, box2);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, box3);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return bb;
    }
}
