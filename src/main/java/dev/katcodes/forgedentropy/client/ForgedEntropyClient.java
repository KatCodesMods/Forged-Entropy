package dev.katcodes.forgedentropy.client;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.CurrentState;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RenderGuiOverlayEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static dev.katcodes.forgedentropy.ForgedEntropyMod.MODID;

public class ForgedEntropyClient {

    public static final Logger LOGGER = LogManager.getLogger();

    public static final ResourceLocation herobrineAmbianceID = new ResourceLocation(MODID,"ambient.herobrine");
    public static SoundEvent herobrineAmbiance = SoundEvent.createVariableRangeEvent(herobrineAmbianceID);
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

    private PostChain shader_monitor;


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

        else if(CurrentState.Get().monitor) {
            if(shader_monitor==null)
                shader_monitor=ShaderManager.register(new ResourceLocation(MODID,"shaders/post/crt.json"));
            assert shader_monitor!=null : "CRT shader is null";
            ShaderManager.render(shader_monitor,tickDelta);
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
        if(shader_monitor!=null)
            shader_monitor.resize(pWidth, pHeight);
    }
}
