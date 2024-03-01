package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.events.AbstractInstantEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Phantom;

public class PhantomEvent extends AbstractInstantEvent {


    @Override
    public void initPlayer(ServerPlayer player) {
        super.initPlayer(player);
        for(int i=0;i<3;i++) {
            Phantom phantom = EntityType.PHANTOM.spawn(player.serverLevel(),player.blockPosition().above(5), MobSpawnType.SPAWN_EGG);
            if(phantom!=null) {
                phantom.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE,460));
                phantom.setTarget(player);
            }
        }
    }
}
