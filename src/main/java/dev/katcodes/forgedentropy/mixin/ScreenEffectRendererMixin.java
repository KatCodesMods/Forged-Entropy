package dev.katcodes.forgedentropy.mixin;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.CurrentState;
import net.minecraft.client.renderer.ScreenEffectRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ScreenEffectRenderer.class)
public class ScreenEffectRendererMixin {

    @Inject(method = "renderFire",at=@At("HEAD"),cancellable = true)
    private static void preventFire(CallbackInfo ci){
        if(CurrentState.Get().fire && Config.accessibilityMode)
            ci.cancel();
    }
}
