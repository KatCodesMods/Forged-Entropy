package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.events.AbstractInstantEvent;
import dev.katcodes.forgedentropy.events.AbstractTimedEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class InvisiblePlayerEvent extends AbstractInstantEvent {
    @Override
    public void initPlayer(ServerPlayer player) {
        super.initPlayer(player);
        var effect = new MobEffectInstance(MobEffects.INVISIBILITY, Config.baseEventDuration,1,true,false);
        player.addEffect(effect);
    }

    @Override
    public String type() {
        return "invisibility";
    }
}
