package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.events.AbstractInstantEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;

public class EndermiteEvent extends AbstractInstantEvent {
    @Override
    public void initPlayer(ServerPlayer player) {
        for(int i=0;i<4;i++)
            EntityType.ENDERMITE.spawn(player.serverLevel(),player.blockPosition(), MobSpawnType.SPAWN_EGG);
        super.initPlayer(player);
    }
}
