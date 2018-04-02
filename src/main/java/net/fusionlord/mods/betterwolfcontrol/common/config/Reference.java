package net.fusionlord.mods.betterwolfcontrol.common.config;

import net.minecraft.util.ResourceLocation;

/**
 * Created by FusionLord on 4/2/2018.
 */
public class Reference {
    public static final String MODID = "betterwolfcontrol";
    public static final String MODNAME = "Better Wolf Control";
    public static final String VERSION = "{$VERSION}";
    public static final String PACKAGE = "net.fusionlord.mods." + MODID;
    public static final String COMMONPROXY = PACKAGE + ".common.proxy.CommonProxy";
    public static final String CLIENTPROXY = PACKAGE + ".client.proxy.ClientProxy";

    public static ResourceLocation getResource(String resource) {
        return new ResourceLocation(MODID, resource);
    }
}
