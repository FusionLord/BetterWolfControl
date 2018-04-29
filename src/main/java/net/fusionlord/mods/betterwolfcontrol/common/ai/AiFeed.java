package net.fusionlord.mods.betterwolfcontrol.common.ai;

import net.fusionlord.mods.betterwolfcontrol.common.tile.TileEntityDogBowl;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Map;

/**
 * Created by FusionLord on 4/17/2018.
 */
public class AiFeed extends EntityAIBase {
    private EntityWolf wolf;
    private World world;
    private TileEntityDogBowl bowl;
    private static final int chunkRadius = 3;
    public static final double DISTANCE = chunkRadius * 2 * 16;

    public AiFeed(EntityWolf wolf) {
        this.wolf = wolf;
        this.world = wolf.world;
    }

    @Override
    public boolean shouldContinueExecuting() {
        return this.wolf.getHealth() < this.wolf.getMaxHealth() && !this.wolf.isBurning();
    }

    @Override
    public boolean shouldExecute() {
        return !this.wolf.isSitting() && this.wolf.getHealth() < this.wolf.getMaxHealth() && !this.wolf.isBurning();
    }

    private boolean getBowl(BlockPos pos) {
        Map<BlockPos, TileEntity> bowls = this.world.getChunkFromBlockCoords(pos).getTileEntityMap();
        for (Map.Entry<BlockPos, TileEntity> entry : bowls.entrySet()) {
            BlockPos p = entry.getKey();
            TileEntity t = entry.getValue();
            if (t instanceof TileEntityDogBowl && pos.distanceSq(p) <= DISTANCE) {
                TileEntityDogBowl b = (TileEntityDogBowl) t;
                if (b.getFood() > 0f && b.canEat(this.wolf)) {
                    this.bowl = b;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void updateTask() {
        if (bowl == null)
            for(int x = -chunkRadius; x < chunkRadius * 2; x++)
                for(int z = -chunkRadius; z < chunkRadius * 2; z++)
                    if (getBowl(this.wolf.getPosition().offset(EnumFacing.NORTH, x * 16).offset(EnumFacing.EAST, z * 16))) break;
        if (bowl == null) return;
        BlockPos destination = this.bowl.getPos();
        this.wolf.getNavigator().setPath(this.wolf.getNavigator().getPathToPos(destination), 1);
        if (this.wolf.getDistanceSq(destination) < 2f) {
            this.wolf.getLookHelper().setLookPosition(destination.getX(), destination.getY(), destination.getZ(), 0.25f, 0.25f);
            this.wolf.heal(this.bowl.eat((int) (this.wolf.getMaxHealth() - this.wolf.getHealth())));
            if (this.wolf.getHealth() >= this.wolf.getMaxHealth() || this.bowl.getFood() <= 0) {
                resetTask();
            }
        }
    }

    @Override
    public void resetTask() {
        this.wolf.getNavigator().clearPath();
        this.bowl = null;
    }
}
