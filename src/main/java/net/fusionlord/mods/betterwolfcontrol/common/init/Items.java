package net.fusionlord.mods.betterwolfcontrol.common.init;

import net.fusionlord.mods.betterwolfcontrol.common.config.Reference;
import net.fusionlord.mods.betterwolfcontrol.common.items.ItemWolfWhistle;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.*;

/**
 * Created by FusionLord on 4/2/2018.
 */
@Mod.EventBusSubscriber(modid = Reference.MODID)
public class Items {
    public static final Set<Item> ITEMS = new HashSet<>();

    public static final Item WOLF_WHISTLE = add(new ItemWolfWhistle());

    private static Item add(Item item) {
        ITEMS.add(item);
        return item;
    }

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event) {
        ITEMS.forEach(event.getRegistry()::register);
    }
}
