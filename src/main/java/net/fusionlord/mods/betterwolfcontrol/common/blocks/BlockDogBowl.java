package net.fusionlord.mods.betterwolfcontrol.common.blocks;

import net.fusionlord.mods.betterwolfcontrol.common.config.Reference;
import net.fusionlord.mods.betterwolfcontrol.common.enums.Group;
import net.fusionlord.mods.betterwolfcontrol.common.tile.TileEntityDogBowl;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Created by FusionLord on 4/15/2018.
 */
public class BlockDogBowl extends Block {
    public static final PropertyEnum<Group> COLOR = PropertyEnum.create("group", Group.class);

    public BlockDogBowl() {
        super(Material.IRON, MapColor.IRON);
        this.setRegistryName(Reference.getResource("dogbowl"));
        this.setUnlocalizedName(this.getRegistryName().toString());
        this.setDefaultState(blockState.getBaseState().withProperty(COLOR, Group.ALL));
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
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(COLOR, Group.VALUES[meta]);
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        super.getSubBlocks(itemIn, items);
        for(Group color : Group.VALUES) {
            items.add(new ItemStack(this, 1, color.ordinal()));
        }
    }
}
