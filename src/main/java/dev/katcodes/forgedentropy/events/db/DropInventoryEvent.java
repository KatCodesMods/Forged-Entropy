package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.events.AbstractInstantEvent;
import net.minecraft.server.level.ServerPlayer;

public class DropInventoryEvent extends AbstractInstantEvent {
    @Override
    public void initPlayer(ServerPlayer player) {
        player.getInventory().dropAll();
        super.initPlayer(player);
    }
}
