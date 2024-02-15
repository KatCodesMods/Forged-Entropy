package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.events.AbstractInstantEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.enchantment.Enchantments;

public class ArmorCurseEvent extends AbstractInstantEvent {
    @Override
    public void initPlayer(ServerPlayer player) {
        player.getInventory().armor.forEach(item -> {
            item.enchant(Enchantments.BINDING_CURSE,1);
        });
        super.initPlayer(player);
    }
}
