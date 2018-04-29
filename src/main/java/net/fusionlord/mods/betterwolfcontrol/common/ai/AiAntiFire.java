package net.fusionlord.mods.betterwolfcontrol.common.ai;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

/**
 * Created by FusionLord on 4/17/2018.
 */
public class AiAntiFire extends EntityAIBase {
    private EntityWolf wolf;
    private World world;
    BlockPos safe;

    public AiAntiFire(EntityWolf wolf) {
        this.wolf = wolf;
        this.world = wolf.world;
    }

    @Override
    public boolean shouldExecute() {
        return this.wolf.isBurning() || (this.wolf.isWet() && !this.wolf.isInWater());
    }

    @Override
    public boolean shouldContinueExecuting() {
        return shouldExecute();
    }

    @Override
    public void updateTask() {
        if (this.safe == null)
            this.wolf.getNavigator().setPath(this.wolf.getNavigator().getPathToPos(findSafeBlock()), 1.4d);
        if (this.safe.distanceSq(this.wolf.getPosition()) <= 1f) {
            this.wolf.setFire(1);
            this.wolf.isWet = true;
            resetTask();
        }
    }

    private BlockPos findSafeBlock() {
        Vec3d v = RandomPositionGenerator.findRandomTarget(this.wolf, 10, 7);
        if (v == null)
            this.safe = this.wolf.getPosition().offset(EnumFacing.NORTH, 1);
        else
            this.safe = new BlockPos(v);
        return this.safe;
    }

    @Override
    public void resetTask() {
        this.safe = null;
    }
}
