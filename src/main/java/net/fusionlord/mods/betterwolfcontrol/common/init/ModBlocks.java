package net.fusionlord.mods.betterwolfcontrol.common.init;

import com.google.common.collect.Lists;
import net.fusionlord.mods.betterwolfcontrol.common.blocks.BlockDogBowl;
import net.fusionlord.mods.betterwolfcontrol.common.config.Reference;
import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;

import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

import java.util.List;

/**
 * Created by FusionLord on 4/2/2018.
 */
@ObjectHolder(Reference.MODID)
@EventBusSubscriber(modid = Reference.MODID)
public class ModBlocks {
    public static List<Block> BLOCKS = Lists.newArrayList();

    public static final BlockDogBowl DOGBOWL = null;

    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> event) {
        BLOCKS.add(new BlockDogBowl());
        for (Block block : BLOCKS) {
            event.getRegistry().register(block);
        }
    }
}
