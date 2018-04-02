package net.fusionlord.mods.betterwolfcontrol.common.init;

import net.fusionlord.mods.betterwolfcontrol.BetterWolfControl;
import net.fusionlord.mods.betterwolfcontrol.common.config.Reference;
import net.fusionlord.mods.betterwolfcontrol.common.creativetabs.CreativeTab;
import net.fusionlord.mods.betterwolfcontrol.common.items.ItemWolfWhistle;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.registries.IForgeRegistry;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by FusionLord on 4/2/2018.
 */
public enum Items {
    WOLF_WHISTLE("whistle", new ItemWolfWhistle(), CreativeTabs.MAIN.tab);
    public static final Items[] VALUES = values();

    public Item item;

    Items(String unlocalizedname, Item item, net.minecraft.creativetab.CreativeTabs creativeTab) {
        this.item = item;
        this.item.setRegistryName(Reference.getResource(unlocalizedname));
        this.item.setUnlocalizedName(this.item.getRegistryName().toString());
        this.item.setCreativeTab(creativeTab);
        if (creativeTab.getTabIconItem() == null) {
            ((CreativeTab)creativeTab).setDisplayStack(new ItemStack(item));
        }
    }

    @Mod.EventBusSubscriber(modid = Reference.MODID)
    public static class RegistrationHandler {
        public static final Set<Item> ITEMS = new HashSet<>();

        @SubscribeEvent
        public static void registerItems(final RegistryEvent.Register<Item> event) {
            final Set<Item> items = Arrays.stream(Items.values()).map(e->e.item).collect(Collectors.toSet());
            final IForgeRegistry<Item> registry = event.getRegistry();
            for (final Item item : items) {
                registry.register(item);
                ITEMS.add(item);
            }
        }
    }
}
