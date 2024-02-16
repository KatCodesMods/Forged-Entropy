package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.events.AbstractInstantEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

public class ExtremeExplosionEvent extends AbstractInstantEvent {

    @Override
    public void initPlayer(ServerPlayer player) {
        player.level().explode(player,player.getX(),player.getY(),player.getZ(),8f, Level.ExplosionInteraction.TNT);
        super.initPlayer(player);
    }
}
