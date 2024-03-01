package dev.katcodes.forgedentropy.mixin;

import dev.katcodes.forgedentropy.CurrentState;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {

    @Inject(method="isAutoJumpEnabled", at=@At("RETURN"), cancellable = true)
    private void changeAutoJump(CallbackInfoReturnable<Boolean> cir) {
        if(CurrentState.Get().noJump) {
            cir.setReturnValue(false);
        }
    }
}
