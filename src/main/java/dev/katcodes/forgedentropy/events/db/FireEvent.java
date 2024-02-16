package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.CurrentState;
import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.events.AbstractTimedEvent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class FireEvent extends AbstractTimedEvent {

    @OnlyIn(Dist.CLIENT)
    @Override
    public void initClient() {
        super.initClient();
        CurrentState.Get().fire=true;
    }


    @OnlyIn(Dist.CLIENT)
    @Override
    public void endClient() {
        super.endClient();
        CurrentState.Get().fire=false;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void render(GuiGraphics graphics, float tickDelta) {

    }

    @Override
    public void tick() {
        if(tickCount % 5==0) {
            ForgedEntropyMod.eventHandler.getActivePlayers().forEach(serverPlayer -> {
                ServerLevel level = serverPlayer.serverLevel();
                BlockPos pos =serverPlayer.blockPosition();

                if(level.getBlockState(pos).canBeReplaced())
                    level.setBlockAndUpdate(pos, Blocks.FIRE.defaultBlockState());
                pos = pos.above();
                if(level.getBlockState(pos).canBeReplaced())
                    level.setBlockAndUpdate(pos, Blocks.FIRE.defaultBlockState());
                pos = serverPlayer.blockPosition().below();
                if(level.getBlockState(pos).canBeReplaced())
                    level.setBlockAndUpdate(pos, Blocks.FIRE.defaultBlockState());
                serverPlayer.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE,20,1));
            });
        }
        super.tick();
    }

    @Override
    public short getDuration() {
        return (short) Config.baseEventDuration;
    }
}
