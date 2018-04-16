package net.fusionlord.mods.betterwolfcontrol.common.network.packets;

import net.fusionlord.mods.betterwolfcontrol.client.network.messages.PacketMouseWheel;
import net.fusionlord.mods.betterwolfcontrol.common.items.interfaces.IMouseWheelListener;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by FusionLord on 4/2/2018.
 */
public class HandlerMouseWheel implements IMessageHandler<PacketMouseWheel, IMessage> {
    @Override
    public IMessage onMessage(PacketMouseWheel message, MessageContext ctx) {
        EntityPlayer player = ctx.getServerHandler().player;
        if (player.isSneaking() && player.getHeldItemMainhand().getItem() instanceof IMouseWheelListener)
            ((IMouseWheelListener)player.getHeldItemMainhand().getItem()).onWheel(player.getHeldItemMainhand(), message.amount, message.modifier);
        return null;
    }
}
