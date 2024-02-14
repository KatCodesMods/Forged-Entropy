package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.events.AbstractInstantEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Bee;

public class AngryBeeEvent extends AbstractInstantEvent {
    @Override
    public void init() {
        ForgedEntropyMod.eventHandler.getActivePlayers().forEach(serverPlayer -> {
            for(int i=0;i<3;i++) {
                Bee bee=EntityType.BEE.spawn(serverPlayer.serverLevel(),serverPlayer.blockPosition().east(2), MobSpawnType.SPAWN_EGG);
                if(bee!=null) {
                    bee.setPersistentAngerTarget(serverPlayer.getUUID());
                    bee.startPersistentAngerTimer();
                }
            }
        });
    }
}
