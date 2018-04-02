package net.fusionlord.mods.betterwolfcontrol.client.network.messages;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

/**
 * Created by FusionLord on 4/2/2018.
 */
public class PacketMouseWheel implements IMessage {

    public int amount;

    public PacketMouseWheel() {}

    public PacketMouseWheel(int amount) {
        this.amount = amount;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        amount = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(amount);
    }
}
