package net.fusionlord.mods.betterwolfcontrol.client.events;

import net.fusionlord.mods.betterwolfcontrol.common.config.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.inventory.Slot;
import net.minecraftforge.client.event.GuiContainerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.awt.*;

@Mod.EventBusSubscriber(modid = Reference.MODID, value = Side.CLIENT)
public class GuiScreenEvents {
    private static boolean init;
    private static int[] h;
    @SubscribeEvent
    public static void drawScreenEvent(final GuiContainerEvent.DrawForeground event) {
        Minecraft mc = event.getGuiContainer().mc;
        FontRenderer fr = mc.fontRenderer;
        if (mc.gameSettings.showDebugInfo) {
            if (h == null || h.length != event.getGuiContainer().inventorySlots.inventorySlots.size()) {
                h = new int[event.getGuiContainer().inventorySlots.inventorySlots.size()];
                init = false;
            }
            for (Slot s : event.getGuiContainer().inventorySlots.inventorySlots) {
                int slot = event.getGuiContainer().inventorySlots.inventorySlots.indexOf(s);
                int x = s.xPos;
                int y = s.yPos + fr.FONT_HEIGHT / 2;
                if (!init) {
                    h[slot] = slot;
                }
                fr.drawString(String.valueOf(slot), x, y, rainbowColor(slot), true);
            }
            init = true;
        }
    }

    private static int rainbowColor(int s) {
        return Color.HSBtoRGB((h[s]++ % 255f) / 255f, 1f, 1f);
    }
}
