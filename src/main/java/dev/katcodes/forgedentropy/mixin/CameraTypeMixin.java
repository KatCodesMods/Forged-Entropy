package dev.katcodes.forgedentropy.mixin;

import dev.katcodes.forgedentropy.CurrentState;
import net.minecraft.client.Camera;
import net.minecraft.client.CameraType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CameraType.class)
public class CameraTypeMixin {

    @Inject(method = "isFirstPerson", at=@At("RETURN"), cancellable = true)
    private void isFirstPerson(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(cir.getReturnValue() && !CurrentState.Get().thirdPersonView);
    }

    @Inject(method = "isMirrored",at=@At("RETURN"), cancellable = true)
    private void isFrontView(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(cir.getReturnValue() && !CurrentState.Get().thirdPersonView || CurrentState.Get().frontView);
    }
}
