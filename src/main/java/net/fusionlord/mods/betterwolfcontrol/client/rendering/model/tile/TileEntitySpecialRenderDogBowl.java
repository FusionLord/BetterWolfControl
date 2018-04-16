package net.fusionlord.mods.betterwolfcontrol.client.rendering.model.tile;

import net.fusionlord.mods.betterwolfcontrol.common.tile.TileEntityDogBowl;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import org.lwjgl.opengl.GL11;

/**
 * Created by FusionLord on 4/15/2018.
 */
public class TileEntitySpecialRenderDogBowl extends TileEntitySpecialRenderer<TileEntityDogBowl> {
    public static final float p = 0.0625f;
    @Override
    public void render(TileEntityDogBowl te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        super.render(te, x, y, z, partialTicks, destroyStage, alpha);
        float h = te.getFoodDisplay() * (p*2);
        if (h == 0) return;
        TextureAtlasSprite sprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getModelForState(Blocks.SOUL_SAND.getDefaultState()).getParticleTexture();

        bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + .5f - 2f * p, y + p + h, z + .5f - 2f * p);
        GlStateManager.disableLighting();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(0f, 0f, 4f * p).tex(sprite.getMinU(), sprite.getMaxV()).endVertex();
        bufferbuilder.pos(4f * p, 0f, 4f * p).tex(sprite.getMaxU(), sprite.getMaxV()).endVertex();
        bufferbuilder.pos(4f * p, 0f, 0f).tex(sprite.getMaxU(), sprite.getMinV()).endVertex();
        bufferbuilder.pos(0f, 0f, 0f).tex(sprite.getMinU(), sprite.getMinV()).endVertex();
        tessellator.draw();
        GlStateManager.popMatrix();
    }

    private float map(float h, int i, float v) {
        if (h < i) return i;
        if (h > v) return v;
        return h;
    }

    private void renderLevel() {
    }

    public static void drawScaledCustomSizeModalRect(float x, float y, float u, float v, float uMax, float vMax, float width, float height) {
    }
}
