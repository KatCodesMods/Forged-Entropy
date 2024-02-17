package dev.katcodes.forgedentropy.mixin;

import dev.katcodes.forgedentropy.CurrentState;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {


    @Shadow
    private Entity vehicle;

    @Inject(method = "startRiding(Lnet/minecraft/world/entity/Entity;Z)Z",at=@At("HEAD"),cancellable = true)
    private void preventMounting(Entity entityToMount, boolean force, CallbackInfoReturnable<Boolean> cir) {
        if(CurrentState.Get().forceRiding && vehicle!=null)
            cir.setReturnValue(false);
    }

    @Inject(method = "removeVehicle", at=@At("HEAD"),cancellable = true)
    private void preventDismount(CallbackInfo ci) {
        if(CurrentState.Get().forceRiding)
            ci.cancel();
    }
}
