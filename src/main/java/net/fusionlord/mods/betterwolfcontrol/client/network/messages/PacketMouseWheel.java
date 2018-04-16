package net.fusionlord.mods.betterwolfcontrol.client.network.messages;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import org.lwjgl.input.Keyboard;

/**
 * Created by FusionLord on 4/2/2018.
 */
public class PacketMouseWheel implements IMessage {

    public int amount;
    public boolean modifier;

    public PacketMouseWheel() {}

    public PacketMouseWheel(int amount) {
        this.amount = amount;
        this.modifier = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        amount = buf.readInt();
        modifier = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(amount);
        buf.writeBoolean(modifier);
    }
}
