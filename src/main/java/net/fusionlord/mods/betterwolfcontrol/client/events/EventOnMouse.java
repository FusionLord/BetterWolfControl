package net.fusionlord.mods.betterwolfcontrol.client.events;

import net.fusionlord.mods.betterwolfcontrol.BetterWolfControl;
import net.fusionlord.mods.betterwolfcontrol.client.network.messages.PacketMouseWheel;
import net.fusionlord.mods.betterwolfcontrol.common.items.interfaces.IMouseWheelListener;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by FusionLord on 4/2/2018.
 */
public class EventOnMouse {
    @SubscribeEvent
    public void onMouse(net.minecraftforge.client.event.MouseEvent event) {
        if (event.isCanceled()) return;
        if (event.getDwheel() != 0) {
            EntityPlayer player = Minecraft.getMinecraft().player;
            if (player.isSneaking() && player.getHeldItemMainhand().getItem() instanceof IMouseWheelListener) {
                BetterWolfControl.INSTANCE.networkHandler.sendToServer(new PacketMouseWheel(event.getDwheel()));
                event.setCanceled(true);
            }
        }
    }
}
