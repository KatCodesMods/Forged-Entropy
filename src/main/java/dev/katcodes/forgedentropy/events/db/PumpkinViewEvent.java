package dev.katcodes.forgedentropy.events.db;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.events.AbstractTimedEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class PumpkinViewEvent extends AbstractTimedEvent {
    private static final ResourceLocation PUMKIN_TEXTURE = new ResourceLocation("textures/misc/pumpkinblur.png");
    Minecraft client;


    @Override
    public void initClient() {
        super.initClient();
        client = Minecraft.getInstance();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void render(GuiGraphics graphics, float tickDelta) {
        renderVignetteOverlay();
    }

    @OnlyIn(Dist.CLIENT)
    private void renderVignetteOverlay() {
        int scaledHeight = client.getWindow().getGuiScaledHeight();
        int scaledWidth = client.getWindow().getGuiScaledWidth();
        RenderSystem.enableBlend();
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, PUMKIN_TEXTURE);
        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuilder();
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferBuilder.vertex(0.0, scaledHeight, -90.0).uv(0.0f, 1.0f).endVertex();
        bufferBuilder.vertex(scaledWidth, scaledHeight, -90.0).uv(1.0f, 1.0f).endVertex();
        bufferBuilder.vertex(scaledWidth, 0.0, -90.0).uv(1.0f, 0.0f).endVertex();
        bufferBuilder.vertex(0.0, 0.0, -90.0).uv(0.0f, 0.0f).endVertex();
        tessellator.end();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
    }


    @Override
    public short getDuration() {
        return (short)(Config.baseEventDuration*1.25);
    }
}
