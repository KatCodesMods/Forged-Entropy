package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.events.AbstractInstantEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Bee;

public class AngryBeeEvent extends AbstractInstantEvent {
    @Override
    public void initPlayer(ServerPlayer player) {
        super.initPlayer(player);
        for(int i=0;i<3;i++) {
            Bee bee=EntityType.BEE.spawn(player.serverLevel(),player.blockPosition().east(2), MobSpawnType.SPAWN_EGG);
            if(bee!=null) {
                bee.setPersistentAngerTarget(player.getUUID());
                bee.startPersistentAngerTimer();
            }
        }
    }
}
