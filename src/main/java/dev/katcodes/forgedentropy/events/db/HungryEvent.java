package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.events.AbstractInstantEvent;
import net.minecraft.server.level.ServerPlayer;

public class HungryEvent extends AbstractInstantEvent {
    @Override
    public void initPlayer(ServerPlayer player) {
        super.initPlayer(player);
        player.getFoodData().setFoodLevel(0);
    }
}
