package dev.katcodes.forgedentropy.mixin;

import dev.katcodes.forgedentropy.CurrentState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(net.minecraft.client.renderer.entity.layers.SheepFurLayer.class)
public class SheepFurLayerMixin {

    @Redirect(method="render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/animal/Sheep;FFFFFF)V",at= @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/Sheep;hasCustomName()Z"))
    private boolean makeAllSheepRainbow1(net.minecraft.world.entity.animal.Sheep sheep) {
        return CurrentState.Get().rainbowSheepEverywhere || sheep.hasCustomName();
    }
    @ModifyArg(method="render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/animal/Sheep;FFFFFF)V",at= @At(value = "INVOKE", target = "Ljava/lang/String;equals(Ljava/lang/Object;)Z"),index = 0)
    private Object makeAllSheepRainbow2(Object sheepName) {
        if(CurrentState.Get().rainbowSheepEverywhere)
            return "jeb_";
        return sheepName;
    }


}
