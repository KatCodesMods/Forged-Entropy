package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.events.AbstractTimedEvent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class LowGravityEvent extends AbstractTimedEvent {

    @Override
    public void initPlayer(ServerPlayer player) {
        super.initPlayer(player);
        player.addEffect(new MobEffectInstance(MobEffects.JUMP, (int) (Config.baseEventDuration ),2,true,false,false));
        player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, (int) (Config.baseEventDuration ),2,true,false,false));
    }


    @OnlyIn(Dist.CLIENT)
    @Override
    public void render(GuiGraphics graphics, float tickDelta) {

    }

    @Override
    public void tick() {
        if(getTickCount() % 30 == 0) {
            ForgedEntropyMod.eventHandler.getActivePlayers().forEach(player -> {
                player.addEffect(new MobEffectInstance(MobEffects.JUMP, (int) (Config.baseEventDuration ),2,true,false,false));
                player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, (int) (Config.baseEventDuration ),2,true,false,false));
            });
        }
        super.tick();
    }

    @Override
    public short getDuration() {
        return (short) (Config.baseEventDuration);
    }
}
