package dev.katcodes.forgedentropy.mixin;

import dev.katcodes.forgedentropy.CurrentState;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SoundEngine.class)
public class SoundEngineMixin {

    @Inject(method="calculatePitch", at=@At("RETURN"), cancellable = true)
    private void forcePitch(SoundInstance soundInstance, CallbackInfoReturnable<Float> cir) {
        if(CurrentState.Get().forcePitch)
            cir.setReturnValue(CurrentState.currentState.forcedPitch);
    }
}
