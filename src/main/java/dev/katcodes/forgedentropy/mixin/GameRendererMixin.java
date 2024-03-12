package dev.katcodes.forgedentropy.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.katcodes.forgedentropy.CurrentState;
import dev.katcodes.forgedentropy.client.ForgedEntropyClient;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.FogType;
import net.neoforged.neoforge.client.event.ViewportEvent;
import org.joml.Math;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Shadow
    @Final
    Minecraft minecraft;

    @Shadow private float oldFov;
    @Shadow private  float fov;


    @Inject(method = "render",at=@At(value="TAIL"))
    public void renderBlackWhiteShader(float tickDelta, long pNanoTime, boolean pRenderLevel, CallbackInfo ci)
    {
        if(ForgedEntropyClient.getInstance()!=null) {
            ForgedEntropyClient.getInstance().renderBlackAndWhite(tickDelta);
        }
    }


    @Inject(method = "render",at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;doEntityOutline()V",shift=At.Shift.AFTER))
    public void renderShaders(float tickDelta, long pNanoTime, boolean pRenderLevel, CallbackInfo ci) {
        if(ForgedEntropyClient.getInstance()!=null)
            ForgedEntropyClient.getInstance().renderShaders(tickDelta);
    }
    @Inject(method="resize", at=@At(value = "TAIL"))
    public void resize(int pWidth, int pHeight,CallbackInfo ci) {
        if(ForgedEntropyClient.getInstance()!=null) {
         ForgedEntropyClient.getInstance().resizeShaders(pWidth,pHeight);
        }
    }

    @Inject(method = "getFov", at=@At("RETURN"), cancellable = true)
    public void changeFov(Camera pActiveRenderInfo, float pPartialTicks, boolean pUseFOVSetting, CallbackInfoReturnable<Double> ci) {
        if(CurrentState.Get().forcedFov) {
            if (CurrentState.Get().ignoreVariableFov) {
                ci.setReturnValue((double) CurrentState.Get().fov * Math.lerp(minecraft.options.fovEffectScale().get(), CurrentState.Get().fov, 1.0D));
            } else {
                ci.setReturnValue(updateFov(pActiveRenderInfo, pPartialTicks, pUseFOVSetting, CurrentState.Get().fov));
            }
        }
    }

    @Inject(method="renderLevel", at=@At(value = "INVOKE", target="Lnet/minecraft/client/Camera;setup(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/world/entity/Entity;ZZF)V",shift=At.Shift.AFTER))
    private void rollCamera(float pPartialTicks, long pFinishTimeNano, PoseStack pPoseStack, CallbackInfo ci) {
        if(CurrentState.Get().cameraRoll!=0f) {
            pPoseStack.mulPose(Axis.ZP.rotationDegrees(CurrentState.Get().cameraRoll));
        }
    }

    private Double updateFov(Camera camera, float tickDelta, boolean changingFov, int fovValue) {
        double fov = 70.0D;

        if(changingFov) {
            fov = fovValue;
            fov*=Math.lerp(tickDelta,this.oldFov,this.fov);
        }

        if (camera.getEntity() instanceof LivingEntity && ((LivingEntity)camera.getEntity()).isDeadOrDying()) {
            float f = java.lang.Math.min((float)((LivingEntity)camera.getEntity()).deathTime + tickDelta, 20.0F);
            fov /= (double)((1.0F - 500.0F / (f + 500.0F)) * 2.0F + 1.0F);
        }

        FogType fogtype = camera.getFluidInCamera();
        if (fogtype == FogType.LAVA || fogtype == FogType.WATER) {
            fov *= Mth.lerp((Double)this.minecraft.options.fovEffectScale().get(), 1.0, 0.8571428656578064);
        }

        return fov;

    }
}
