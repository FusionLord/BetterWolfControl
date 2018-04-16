package net.fusionlord.mods.betterwolfcontrol.common.enums;

import net.fusionlord.mods.betterwolfcontrol.common.config.Reference;
import net.fusionlord.mods.betterwolfcontrol.common.items.ItemWhistle;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Created by FusionLord on 4/15/2018.
 */
public enum Command {
    SIT {
        @Override
        public void doAction(EntityPlayer player, List<EntityWolf> wolves) {
            wolves.forEach(e -> e.aiSit.setSitting(true));
        }
    }, COME {
        @Override
        public void doAction(EntityPlayer player, List<EntityWolf> wolves) {
            wolves.forEach(e -> e.aiSit.setSitting(false));
        }
    }, SPEAK {
        @Override
        public void doAction(EntityPlayer player, List<EntityWolf> wolves) {
            wolves.forEach(EntityLiving::playLivingSound);
        }
    }, STEAL {
        @Override
        public void doAction(EntityPlayer player) {
            List<EntityWolf> wolves = player.world.getEntitiesWithinAABB(EntityWolf.class, new AxisAlignedBB(player.getPosition()).grow((this == COME ? 50 : 10) * (player.isSneaking() ? 5 : 1)));
            wolves.forEach(e -> e.setOwnerId(player.getPersistentID()));
        }
    };
    public static final Command[] VALUES = Command.values();

    public void doAction(EntityPlayer player) {
        Group group = ItemWhistle.getGroup(player.getHeldItemMainhand());
        List<EntityWolf> wolves = player.world.getEntitiesWithinAABB(EntityWolf.class, new AxisAlignedBB(player.getPosition()).grow((this == COME ? 50 : 10) * (player.isSneaking() ? 5 : 1)));
        wolves = wolves.stream().filter(e -> (group == Group.ALL || e.getCollarColor() == group.DYE) && e.isOwner(player)).collect(Collectors.toList());
        doAction(player, wolves);
    }

    public void doAction(EntityPlayer player, List<EntityWolf> wolves) {
    }

    public String getName() {
        return name().toLowerCase(Locale.ENGLISH);
    }

    public String getDisplayString() {
        return I18n.format(Reference.getResource("command.").toString().concat(getName()));
    }
}