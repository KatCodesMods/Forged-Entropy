package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.events.AbstractInstantEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Bee;

public class BeeEvent extends AbstractInstantEvent {
    @Override
    public void init() {
        ForgedEntropyMod.eventHandler.getActivePlayers().forEach(serverPlayer -> {
            for(int i=0;i<10;i++) {
                EntityType.BEE.spawn(serverPlayer.serverLevel(),serverPlayer.blockPosition(), MobSpawnType.SPAWN_EGG);
            }
        });
    }
}
