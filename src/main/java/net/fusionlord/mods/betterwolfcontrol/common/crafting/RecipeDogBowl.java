package net.fusionlord.mods.betterwolfcontrol.common.crafting;

import net.fusionlord.mods.betterwolfcontrol.common.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

/**
 * Created by FusionLord on 4/15/2018.
 */
public class RecipeDogBowl extends net.minecraftforge.registries.IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        return inv.getStackInRowAndColumn(0, 1).getItem() == Item.getItemFromBlock(Blocks.IRON_BLOCK) &&
                (inv.getStackInRowAndColumn(1, 1).getItem() == Item.getItemFromBlock(Blocks.STAINED_HARDENED_CLAY)
                || inv.getStackInRowAndColumn(1, 1).getItem() == Item.getItemFromBlock(Blocks.HARDENED_CLAY));
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        ItemStack stack = inv.getStackInRowAndColumn(1, 1);
        Block block = ((ItemBlock)stack.getItem()).getBlock();
        return new ItemStack(ModBlocks.DOG_BOWL, 5, block == Blocks.HARDENED_CLAY ? 0 : 1 + stack.getMetadata());
    }

    @Override
    public boolean canFit(int width, int height) {
        return width >= 2 && height >= 2;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }
}
