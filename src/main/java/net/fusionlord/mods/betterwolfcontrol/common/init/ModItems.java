package net.fusionlord.mods.betterwolfcontrol.common.init;

import com.google.common.collect.Lists;
import net.fusionlord.mods.betterwolfcontrol.common.blocks.intefaces.ICustomItemBlock;
import net.fusionlord.mods.betterwolfcontrol.common.items.ItemBlockDogBowl;
import net.fusionlord.mods.betterwolfcontrol.common.items.ItemWhistle;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

import java.util.*;

import static net.fusionlord.mods.betterwolfcontrol.common.config.Reference.MODID;

/**
 * Created by FusionLord on 4/2/2018.
 */
@ObjectHolder(MODID)
@EventBusSubscriber(modid = MODID)
public class ModItems {
    public static List<Item> ITEMS = Lists.newArrayList();

    public static final ItemWhistle WHISTLE = null;
    public static final ItemBlockDogBowl DOGBOWL = null;

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event) {
        ITEMS.add(new ItemWhistle());
        ITEMS.add(new ItemBlockDogBowl(ModBlocks.DOGBOWL));
        ITEMS.forEach(event.getRegistry()::register);
    }
}
