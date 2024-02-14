package dev.katcodes.forgedentropy.client;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.CurrentState;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RenderGuiOverlayEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static dev.katcodes.forgedentropy.ForgedEntropyMod.MODID;

public class ForgedEntropyClient {

    public static final Logger LOGGER = LogManager.getLogger();
    private static ForgedEntropyClient instance;

    public static ClientEventHandler clientEventHandler;
    public static ForgedEntropyClient getInstance()
    {

        if(instance==null) {
            instance = new ForgedEntropyClient();
        }
        return instance;
    }

    private PostChain shader_black_and_white;
    private PostChain shader_blur;


    @SubscribeEvent
    public static void renderHud(RenderGuiOverlayEvent.Post event) {
        if(clientEventHandler!=null)
            clientEventHandler.render(event.getGuiGraphics(),event.getPartialTick());
    }

    public void renderBlackAndWhite(float tickDelta) {
        if(CurrentState.Get().blackAndWhite){
            if(shader_black_and_white==null) {
                shader_black_and_white = ShaderManager.register(new ResourceLocation(MODID,"shaders/post/black_and_white.json"));
            }
            assert shader_black_and_white != null : "Black & White shader is null";
            ShaderManager.render(shader_black_and_white,tickDelta);
        }
    }

    public void renderShaders(float tickDelta) {
        if(CurrentState.Get().blur) {
            if(shader_blur==null)
                shader_blur=ShaderManager.register(new ResourceLocation(MODID,"shaders/post/blur.json"));
            assert shader_blur != null : "Blur shader is null";
            ShaderManager.render(shader_blur,tickDelta);
        }
    }
    public  void initialize() {
        LOGGER.info("Initializing Forged Entropy Client Mod");

    }

    public void resizeShaders(int pWidth, int pHeight) {
        if(shader_black_and_white!=null)
            shader_black_and_white.resize(pWidth,pHeight);
        if(shader_blur!=null)
            shader_blur.resize(pWidth, pHeight);
    }
}