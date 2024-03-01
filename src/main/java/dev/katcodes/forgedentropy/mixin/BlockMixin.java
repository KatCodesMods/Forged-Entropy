package dev.katcodes.forgedentropy.mixin;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.CurrentState;
import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.client.ForgedEntropyClient;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

@Mixin(Block.class)
public abstract class BlockMixin {

    @Inject(method = "playerDestroy", at = @At("HEAD"))
    private void explodeOnBreak(Level pLevel, Player pPlayer, BlockPos pPos, BlockState pState, @Nullable BlockEntity pBlockEntity, ItemStack pTool, CallbackInfo ci) {
        if(CurrentState.Get().explodingPickaxe) {
            pPlayer.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,20,4,false,false));
            pLevel.explode(null,pPos.getX(),pPos.getY(),pPos.getZ(), ThreadLocalRandom.current().nextInt(1,3), Level.ExplosionInteraction.TNT );
        }
    }


    @Invoker("popResource")
    public static void vanillaPopResource(Level pLevel, Supplier<ItemEntity> pItemEntitySupplier, ItemStack pStack) {
        throw new AssertionError();
    }

    @Inject(method = "popResource(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/item/ItemStack;)V", at = @At("HEAD"), cancellable = true)
    private static void randomDrops(Level pLevel, BlockPos pPos, ItemStack pStack, CallbackInfo ci) {
        if(CurrentState.Get().noDrops)
            ci.cancel();
        if(CurrentState.Get().luckyDrops) {
            ForgedEntropyMod.LOGGER.info("Lucky drops event is active");
            if (!pLevel.isClientSide && !pStack.isEmpty() && pLevel.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && !pLevel.restoringBlockSnapshots) {
                ForgedEntropyMod.LOGGER.info("Spawning lucky drops");
                double d0 = (double)EntityType.ITEM.getHeight() / 2.0;
                double d1 = (double)pPos.getX() + 0.5 + Mth.nextDouble(pLevel.random, -0.25, 0.25);
                double d2 = (double)pPos.getY() + 0.5 + Mth.nextDouble(pLevel.random, -0.25, 0.25) - d0;
                double d3 = (double)pPos.getZ() + 0.5 + Mth.nextDouble(pLevel.random, -0.25, 0.25);
               ItemStack stack =  computeItemStack(pStack, pLevel);

                ForgedEntropyMod.LOGGER.info("Spawning lucky drops: {}", stack.getCount())   ;
                vanillaPopResource(pLevel, () -> new ItemEntity(pLevel, d1, d2, d3, stack), stack);
            }
            ci.cancel();
        }
    }

    @Unique
    private static ItemStack computeItemStack(ItemStack itemStack, Level level) {
        if (CurrentState.Get().luckyDrops) {
            itemStack.setCount(itemStack.getCount() * 5);
            return itemStack;
        }
        return null;
    }
}
