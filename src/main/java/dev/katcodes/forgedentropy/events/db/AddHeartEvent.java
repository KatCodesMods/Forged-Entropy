package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.events.AbstractInstantEvent;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class AddHeartEvent extends AbstractInstantEvent {
    @Override
    public void init() {
        for(var player: ForgedEntropyMod.eventHandler.getActivePlayers()) {
            player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(player.getMaxHealth()+2);
            player.setHealth(player.getHealth()+2);
        }
    }

    @Override
    public String type() {
        return "health";
    }
}
