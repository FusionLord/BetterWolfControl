package net.fusionlord.mods.betterwolfcontrol.common.init;

import com.google.common.collect.Lists;
import net.fusionlord.mods.betterwolfcontrol.common.blocks.intefaces.ICustomItemBlock;
import net.fusionlord.mods.betterwolfcontrol.common.config.Reference;
import net.fusionlord.mods.betterwolfcontrol.common.items.ItemWhistle;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by FusionLord on 4/2/2018.
 */
@Mod.EventBusSubscriber(modid = Reference.MODID)
public class ModItems {
    public static final List<Item> ITEMS = Lists.newArrayList();

    public static final ItemWhistle WOLF_WHISTLE = add(new ItemWhistle());

    private static<T extends Item> T add(T t) {
        ITEMS.add(t);
        return t;
    }

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event) {
        ITEMS.forEach(event.getRegistry()::register);
        ModBlocks.BLOCKS.stream().filter(b -> b instanceof ICustomItemBlock).map(ICustomItemBlock.class::cast).collect(Collectors.toList()).forEach(i -> event.getRegistry().register(i.getItemBlock()));
    }
}
