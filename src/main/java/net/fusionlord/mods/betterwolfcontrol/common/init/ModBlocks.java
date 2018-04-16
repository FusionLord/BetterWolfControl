package net.fusionlord.mods.betterwolfcontrol.common.init;

import com.google.common.collect.Lists;
import net.fusionlord.mods.betterwolfcontrol.common.blocks.BlockDogBowl;
import net.fusionlord.mods.betterwolfcontrol.common.config.Reference;
import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

/**
 * Created by FusionLord on 4/2/2018.
 */
@Mod.EventBusSubscriber(modid = Reference.MODID)
public class ModBlocks {
    public static final List<Block> BLOCKS = Lists.newArrayList();

    public static final BlockDogBowl DOG_BOWL = add(new BlockDogBowl());

    private static <T extends Block> T add(T t) {
        BLOCKS.add(t);
        return t;
    }

    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> event) {
        BLOCKS.forEach(event.getRegistry()::register);
    }
}
