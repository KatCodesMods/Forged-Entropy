package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.EntropyTags;
import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.events.AbstractInstantEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class ExplodeNearbyEntitiesEvent extends AbstractInstantEvent {
    @Override
    public void initPlayer(ServerPlayer player) {
        super.initPlayer(player);
        player.getCommandSenderWorld().getEntities(player,new AABB(player.blockPosition().offset(70,70,70).getCenter(), player.blockPosition().offset(-70,-70,-70).getCenter())).forEach(entity -> {
            entity.getCommandSenderWorld().explode(entity,entity.getX(),entity.getY(),entity.getZ(),2.1f, Level.ExplosionInteraction.MOB);
            if(!entity.getType().is(EntropyTags.EntityTypeTags.DO_NOT_EXPLODE))
                entity.kill();
        });
    }
}
