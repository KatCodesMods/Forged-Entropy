package dev.katcodes.forgedentropy.mixin;

import dev.katcodes.forgedentropy.CurrentState;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public class MouseHandlerMixin {

    @Shadow
    private double accumulatedDX;

    @Shadow
    private double accumulatedDY;


    @ModifyArg(method = "turnPlayer", at= @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;turn(DD)V"),index = 0)
    public double invertX(double x) {
        if(CurrentState.currentState.invertedControls)
            return -x;
        return x;
    }

    @ModifyArg(method = "turnPlayer", at= @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;turn(DD)V"),index = 1)
    public double invertY(double y) {
        if(CurrentState.currentState.invertedControls)
            return -y;
        return y;
    }

    @Inject(method= "turnPlayer", at = @At("HEAD"))
    private void driftMouse(CallbackInfo ci) {
        if(CurrentState.Get().mouseDrifting) {
            this.accumulatedDX += CurrentState.Get().mouseDriftingSignX * 1.5;
            this.accumulatedDY += CurrentState.Get().mouseDriftingSignY * 0.1;
        }
    }
}
