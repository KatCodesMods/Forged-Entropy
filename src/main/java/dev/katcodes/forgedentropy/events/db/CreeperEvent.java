package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.events.AbstractInstantEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;

public class CreeperEvent extends AbstractInstantEvent {

    @Override
    public void init() {
        super.init();
        ForgedEntropyMod.eventHandler.getActivePlayers().forEach(serverPlayer -> EntityType.CREEPER.spawn(serverPlayer.serverLevel(),serverPlayer.blockPosition().north(), MobSpawnType.SPAWN_EGG));
    }
}
