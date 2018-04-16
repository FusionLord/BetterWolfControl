package net.fusionlord.mods.betterwolfcontrol.client.rendering.entity;

import net.fusionlord.mods.betterwolfcontrol.common.capability.entity.ProviderWolfState;
import net.fusionlord.mods.betterwolfcontrol.common.enums.WolfStates;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Created by FusionLord on 4/6/2018.
 */
public class BetterWolfLayerRenderer implements LayerRenderer<EntityWolf> {

    @Override
    public void doRenderLayer(EntityWolf entityWolf, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        Minecraft minecraft = Minecraft.getMinecraft();
        EntityPlayer player = minecraft.player;
        if (!(minecraft.objectMouseOver != null && minecraft.objectMouseOver.entityHit == entityWolf) || minecraft.objectMouseOver == null || !entityWolf.isTamed() || !entityWolf.isOwner(player)) return;
        RenderManager renderManager = minecraft.getRenderManager();

        WolfStates wolfState = entityWolf.getCapability(ProviderWolfState.WOLF_STATE_CAP, null).get();

        double maxDistance = 64;
        double d0 = entityWolf.getDistanceSq(renderManager.renderViewEntity);
        if (d0 <= maxDistance * maxDistance) {
            float f0 = entityWolf.prevRenderYawOffset + (entityWolf.renderYawOffset - entityWolf.prevRenderYawOffset) * partialTicks;
            float f = -renderManager.playerViewY + f0;
            float f1 = renderManager.playerViewX - 180;
            boolean flag1 = renderManager.options.thirdPersonView == 2;
            EntityRenderer.drawNameplate(minecraft.fontRenderer, I18n.format(wolfState.getUnlocalizedString()), 0f, 0f, 0f, -3, f, f1, flag1, false);
        }
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}
