package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.events.AbstractInstantEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class FatigueEvent extends AbstractInstantEvent {

    @Override
    public void initPlayer(ServerPlayer player) {
        player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN,(int)(Config.baseEventDuration*1.25),1));
        super.initPlayer(player);
    }
}
