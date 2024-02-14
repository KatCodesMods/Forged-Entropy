package dev.katcodes.forgedentropy.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.io.IOException;

import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;

public class ShaderManager {

    public static PostChain register(ResourceLocation id) {
        Minecraft client = Minecraft.getInstance();
        try {
            PostChain shader;
            shader=new PostChain(client.textureManager,client.getResourceManager(),client.getMainRenderTarget(),id);
            shader.resize(client.getWindow().getWidth(),client.getWindow().getHeight());
            return shader;
        } catch (IOException e)
        {
            ForgedEntropyClient.LOGGER.warn("Could not read shader: {}",id);
        }
        return null;
    }

    public static void render(PostChain shader,float tickDelta) {
        RenderSystem.disableBlend();
        RenderSystem.disableDepthTest();
        RenderSystem.resetTextureMatrix();
        shader.process(tickDelta);
        Minecraft.getInstance().getMainRenderTarget().bindWrite(true);
        RenderSystem.disableBlend();
        RenderSystem.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        RenderSystem.enableDepthTest();
    }
}
