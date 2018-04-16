package net.fusionlord.mods.betterwolfcontrol.common.enums;

import net.fusionlord.mods.betterwolfcontrol.common.items.ItemWhistle;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by FusionLord on 4/15/2018.
 */
public enum Command {
    SIT {
        @Override
        public void doAction(EntityPlayer player, List<EntityWolf> wolves) {
            for (EntityWolf wolf : wolves)
                if (wolf.getOwner() == player)
                    wolf.aiSit.setSitting(true);
        }
    }, COME {
        @Override
        public void doAction(EntityPlayer player, List<EntityWolf> wolves) {
            for (EntityWolf wolf : wolves)
                if (wolf.getOwner() == player)
                    wolf.aiSit.setSitting(false);
        }
    }, SPEAK {
        @Override
        public void doAction(EntityPlayer player, List<EntityWolf> wolves) {
            for (EntityWolf wolf : wolves)
                if (wolf.getOwner() == player)
                    wolf.playLivingSound();
        }
    };
    public static final Command[] VALUES = Command.values();

    public void doAction(EntityPlayer player) {
        Group group = ItemWhistle.getGroup(player.getHeldItemMainhand());
        List<EntityWolf> wolves = player.world.getEntitiesWithinAABB(EntityWolf.class, new AxisAlignedBB(player.getPosition()).grow(this == COME ? 50 : 10));
        wolves = wolves.stream().filter(e -> group == Group.ALL || e.getCollarColor() == group.DYE).collect(Collectors.toList());
        doAction(player, wolves);
    }

    public void doAction(EntityPlayer player, List<EntityWolf> wolves) {
    }
}