package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.events.AbstractInstantEvent;
import net.minecraft.server.level.ServerPlayer;

public class HealEvent extends AbstractInstantEvent {

    @Override
    public void initPlayer(ServerPlayer player) {
        super.initPlayer(player);
        player.heal(player.getMaxHealth());
    }

    @Override
    public String type() {
        return "health";
    }
}
