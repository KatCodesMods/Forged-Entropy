package dev.katcodes.forgedentropy.mixin;

import dev.katcodes.forgedentropy.CurrentState;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.KeyboardInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardInput.class)
public class KeyboardInputMixin extends Input {

    @Inject(method = "tick",at=@At("TAIL"))
    private void applyEvents(boolean slowDown, float f, CallbackInfo ci) {
        if(CurrentState.Get().forceForward) {
            this.up=true;
            this.forwardImpulse=1;
        } else if(CurrentState.Get().invertedControls) {
            this.forwardImpulse=-this.forwardImpulse;
            this.leftImpulse=-this.leftImpulse;
        }

        if(CurrentState.Get().forceJump) {
            this.jumping=true;
        }

        if(CurrentState.Get().forceSneak) {
            this.shiftKeyDown=true;
        }
    }
}
