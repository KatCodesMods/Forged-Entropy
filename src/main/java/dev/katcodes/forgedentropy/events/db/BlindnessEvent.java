package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.events.AbstractInstantEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class BlindnessEvent extends AbstractInstantEvent {

    @Override
    public void init() {
        ForgedEntropyMod.eventHandler.getActivePlayers().forEach(serverPlayer -> serverPlayer.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, Config.baseEventDuration)));
    }
}
