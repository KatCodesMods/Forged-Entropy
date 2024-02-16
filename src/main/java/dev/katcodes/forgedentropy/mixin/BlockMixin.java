package dev.katcodes.forgedentropy.mixin;

import dev.katcodes.forgedentropy.CurrentState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Mixin(Block.class)
public class BlockMixin {

    @Inject(method = "playerDestroy", at = @At("HEAD"))
    private void explodeOnBreak(Level pLevel, Player pPlayer, BlockPos pPos, BlockState pState, @Nullable BlockEntity pBlockEntity, ItemStack pTool, CallbackInfo ci) {
        if(CurrentState.Get().explodingPickaxe) {
            pPlayer.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,20,4,false,false));
            pLevel.explode(null,pPos.getX(),pPos.getY(),pPos.getZ(), ThreadLocalRandom.current().nextInt(1,3), Level.ExplosionInteraction.TNT );
        }
    }
}
