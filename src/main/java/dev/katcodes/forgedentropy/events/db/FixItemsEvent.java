package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.EntropyTags;
import dev.katcodes.forgedentropy.events.AbstractInstantEvent;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class FixItemsEvent extends AbstractInstantEvent {

    public static void fixItem(ItemStack itemStack, ServerPlayer player) {
        if(itemStack.is(EntropyTags.ItemTags.UNFIXABLE))
            return;
        CriteriaTriggers.ITEM_DURABILITY_CHANGED.trigger(player,itemStack,itemStack.getMaxDamage());
        itemStack.setDamageValue(0);
    }

    @Override
    public void initPlayer(ServerPlayer player) {
        player.getInventory().items.forEach(itemStack -> {
            if(itemStack.isDamageableItem())
                fixItem(itemStack,player);
        });
        player.getInventory().armor.forEach(itemStack -> {
            if(itemStack.isDamageableItem())
                fixItem(itemStack,player);
        });
        player.getInventory().offhand.forEach(itemStack -> {
            if(itemStack.isDamageableItem())
                fixItem(itemStack,player);
        });
        super.initPlayer(player);
    }
}
