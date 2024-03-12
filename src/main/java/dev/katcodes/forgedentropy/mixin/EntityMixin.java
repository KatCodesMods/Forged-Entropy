package dev.katcodes.forgedentropy.mixin;

import dev.katcodes.forgedentropy.CurrentState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;

import static dev.katcodes.forgedentropy.common.MixinUtils.getRandomItem;

@Mixin(Entity.class)
public abstract class EntityMixin {


    @Shadow
    private Entity vehicle;

    @Shadow
    private Level level;

    @Shadow
    public abstract double getX();

    @Shadow
    public abstract double getY();

    @Shadow
    public abstract double getZ();

    @Shadow
    public abstract Collection<ItemEntity> captureDrops();

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

    @Inject(method="spawnAtLocation(Lnet/minecraft/world/item/ItemStack;F)Lnet/minecraft/world/entity/item/ItemEntity;",at=@At("HEAD"),cancellable = true)
    private void randomDrops(ItemStack stack, float yOffset, CallbackInfoReturnable<ItemEntity> cir) {
        if(CurrentState.Get().noDrops) {
            cir.setReturnValue(null);
            cir.cancel();
        }
        if(CurrentState.Get().luckyDrops || CurrentState.Get().randomDrops){
            if (stack.isEmpty()) {
                cir.setReturnValue(null);
                cir.cancel();
            } else if (this.level.isClientSide) {
                cir.setReturnValue(null);
                cir.cancel();
            } else {
                ItemEntity itementity = new ItemEntity(this.level, this.getX(), this.getY() + (double)yOffset, this.getZ(), forged_Entropy$computeItemStack(stack));
                itementity.setDefaultPickUpDelay();
                if (captureDrops() != null) captureDrops().add(itementity);
                else
                    this.level.addFreshEntity(itementity);
                cir.setReturnValue(itementity);
                cir.cancel();
            }
            cir.cancel();
        }
    }

    @Unique
    private ItemStack forged_Entropy$computeItemStack(ItemStack stack) {
        if(CurrentState.Get().luckyDrops) {
            stack.setCount(stack.getCount() * 5);
            return stack;
        } else if(CurrentState.Get().randomDrops) {
            return new ItemStack(getRandomItem(level),stack.getCount());
        }
        return stack;
    }
}
