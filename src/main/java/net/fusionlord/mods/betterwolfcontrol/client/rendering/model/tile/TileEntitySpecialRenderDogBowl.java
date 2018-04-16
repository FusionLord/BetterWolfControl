package net.fusionlord.mods.betterwolfcontrol.client.rendering.model.tile;

import net.fusionlord.mods.betterwolfcontrol.common.tile.TileEntityDogBowl;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
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
        float h = te.getFoodDisplay();
        GlStateManager.pushMatrix();
        GlStateManager.translate(4 * p, p + Math.max(h, p * 2), 4 * p);
        renderLevel();
        GlStateManager.popMatrix();
    }

    private void renderLevel() {
        TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getTextureExtry(Blocks.SOUL_SAND.getRegistryName().toString());
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder builder = tessellator.getBuffer();
        builder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        builder.pos(0, 0, 0).color(1, 1, 1, 1).tex(sprite.getMinU(), sprite.getMinV()).endVertex();
        builder.pos(4 * p, 0, 0).color(1, 1, 1, 1).tex(sprite.getMaxU(), sprite.getMinV()).endVertex();
        builder.pos(4 * p, 0, 4 * p).color(1, 1, 1, 1).tex(sprite.getMaxU(), sprite.getMaxV()).endVertex();
        builder.pos(0, 0, 4 * p).color(1, 1, 1, 1).tex(sprite.getMinU(), sprite.getMaxV()).endVertex();
        tessellator.draw();
    }
}
