package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.events.AbstractInstantEvent;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.monster.Blaze;

public class BlazeEvent extends AbstractInstantEvent {
    @Override
    public void initPlayer(ServerPlayer player) {
        Blaze blaze=EntityType.BLAZE.spawn(player.serverLevel(),player.blockPosition(), MobSpawnType.SPAWN_EGG);
        assert blaze != null;
        blaze.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,9999,2));
        blaze.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST,120,1));
        super.initPlayer(player);
    }
}
