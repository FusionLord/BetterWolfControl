package net.fusionlord.mods.betterwolfcontrol.common.network;

import net.fusionlord.mods.betterwolfcontrol.common.config.Reference;
import net.fusionlord.mods.betterwolfcontrol.client.network.messages.PacketMouseWheel;
import net.fusionlord.mods.betterwolfcontrol.common.network.packets.HandlerMouseWheel;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by FusionLord on 4/2/2018.
 */
public class NetworkHandler extends SimpleNetworkWrapper {

    public NetworkHandler() {
        super(Reference.MODID);
        registerMessages();
    }

    private void registerMessages() {
        registerMessage(HandlerMouseWheel.class, PacketMouseWheel.class, 0, Side.SERVER);
    }
}
