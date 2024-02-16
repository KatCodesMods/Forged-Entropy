package dev.katcodes.forgedentropy.mixin;

import dev.katcodes.forgedentropy.CurrentState;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {

    @Inject(method = "isEntityUpsideDown",at=@At("HEAD"),cancellable = true)
    private static void flipUpsideDown(LivingEntity entity, CallbackInfoReturnable<Boolean> cir)
    {
        if(CurrentState.Get().flipEntities)
            cir.setReturnValue(true);
    }
}
