package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.events.AbstractInstantEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.Horse;

public class HorseEvent extends AbstractInstantEvent {

    @Override
    public void initPlayer(ServerPlayer player) {
        super.initPlayer(player);
        Horse horse = EntityType.HORSE.spawn(player.serverLevel(),player.blockPosition(), MobSpawnType.SPAWN_EGG);
        if(horse==null)
            return;
        horse.equipSaddle(SoundSource.NEUTRAL);
        horse.getAttribute(Attributes.MAX_HEALTH).setBaseValue(2);
        horse.setHealth(2);
    }
}
