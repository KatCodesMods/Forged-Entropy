package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.events.AbstractTimedEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class ForceJump2Event extends ForceJumpEvent {
    @Override
    public void initPlayer(ServerPlayer player) {
        super.initPlayer(player);
        player.addEffect(new MobEffectInstance(MobEffects.JUMP,getDuration(),3,true,false,false));
    }
}
