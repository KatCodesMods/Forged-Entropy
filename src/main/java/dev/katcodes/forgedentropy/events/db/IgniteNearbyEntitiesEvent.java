package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.EntropyTags;
import dev.katcodes.forgedentropy.events.AbstractInstantEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.AABB;

public class IgniteNearbyEntitiesEvent extends AbstractInstantEvent {

    @Override
    public void initPlayer(ServerPlayer player) {
        super.initPlayer(player);
        player.getCommandSenderWorld().getEntities(player,new AABB(player.blockPosition().offset(50,50,50).getCenter(),player.blockPosition().offset(-50,-50,-50).getCenter())).forEach(entity -> {
            if(!entity.getType().is(EntropyTags.EntityTypeTags.DO_NOT_IGNITE))
                entity.setSecondsOnFire(30);
        });
    }
}
