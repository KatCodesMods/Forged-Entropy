package dev.katcodes.forgedentropy.mixin;

import dev.katcodes.forgedentropy.CurrentState;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "hurt", at=@At("RETURN"))
    private void hurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        var _this = (LivingEntity)(Object)this;
        var attacker  = source.getEntity();
        if(attacker instanceof Player) {
            if(CurrentState.Get().isOnePunchActivated > 0) {
                var pos = _this.position();
                _this.level().addParticle(ParticleTypes.EXPLOSION,pos.x,pos.y,pos.z,0,0,0);
                _this.kill();
            }
            if(CurrentState.Get().shouldLaunchEntity > 0) {
                var direction = attacker.getLookAngle().normalize().scale(4).add(0,1.75,0);
                _this.addDeltaMovement(direction);
            }
        }
    }
}
