package net.fusionlord.mods.betterwolfcontrol.common.blocks;

import net.fusionlord.mods.betterwolfcontrol.common.config.Reference;
import net.fusionlord.mods.betterwolfcontrol.common.enums.Group;
import net.fusionlord.mods.betterwolfcontrol.common.tile.TileEntityDogBowl;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Created by FusionLord on 4/15/2018.
 */
public class BlockDogBowl extends Block {
    public static final PropertyEnum<Group> GROUP = PropertyEnum.create("group", Group.class);

    public BlockDogBowl() {
        super(Material.IRON, MapColor.IRON);
        this.setRegistryName(Reference.getResource("dogbowl"));
        this.setUnlocalizedName(this.getRegistryName().toString());
        this.setDefaultState(blockState.getBaseState().withProperty(GROUP, Group.ALL));
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
        ((TileEntityDogBowl)worldIn.getTileEntity(pos)).setGroup(Group.VALUES[stack.getMetadata()]);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return state.withProperty(GROUP, ((TileEntityDogBowl)worldIn.getTileEntity(pos)).getGroup());
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        super.getSubBlocks(itemIn, items);
        for(Group color : Group.VALUES) {
            items.add(new ItemStack(this, 1, color.ordinal()));
        }
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState();
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }
}
