package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.events.AbstractInstantEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class NightVisionEvent extends AbstractInstantEvent  {

    @Override
    public void initPlayer(ServerPlayer player) {
        super.initPlayer(player);
        player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION,(int)(Config.baseEventDuration*1.5)));
    }
}
