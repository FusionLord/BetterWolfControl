package net.fusionlord.mods.betterwolfcontrol.common.events;

import net.fusionlord.mods.betterwolfcontrol.common.capability.entity.IWolfState;
import net.fusionlord.mods.betterwolfcontrol.common.capability.entity.ProviderWolfState;
import net.fusionlord.mods.betterwolfcontrol.common.config.Reference;
import net.fusionlord.mods.betterwolfcontrol.common.enums.WolfStates;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by FusionLord on 4/6/2018.
 */
@Mod.EventBusSubscriber(modid = Reference.MODID)
public class WolfEvents {
    private static final ResourceLocation WOLF_STATE_CAP = Reference.getResource("wolf_state");

    @SubscribeEvent
    public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
        if (!(event.getObject() instanceof EntityWolf)) return;
        event.addCapability(WOLF_STATE_CAP, new ProviderWolfState());
    }

    @SubscribeEvent
    public static void attackEntity(AttackEntityEvent event) {
        if (!(event.getTarget() instanceof EntityWolf)) return;
        EntityWolf wolf = (EntityWolf)event.getTarget();
        if (!wolf.isOwner(event.getEntityLiving())) return;
        IWolfState iState = wolf.getCapability(ProviderWolfState.WOLF_STATE_CAP, null);
        WolfStates state;
        if (event.getEntityPlayer().isSneaking())
            state = WolfStates.prev(iState.get());
        else
            state = WolfStates.next(iState.get());
        iState.set(state);
        event.setCanceled(true);
    }
}
