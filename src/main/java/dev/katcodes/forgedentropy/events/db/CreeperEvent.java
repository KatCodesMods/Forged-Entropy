package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.events.AbstractInstantEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;

public class CreeperEvent extends AbstractInstantEvent {

    @Override
    public void initPlayer(ServerPlayer player) {
        EntityType.CREEPER.spawn(player.serverLevel(),player.blockPosition().north(), MobSpawnType.SPAWN_EGG);
        super.initPlayer(player);
    }
}
