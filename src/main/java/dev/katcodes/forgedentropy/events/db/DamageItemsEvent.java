package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.EntropyTags;
import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.events.AbstractInstantEvent;
import net.minecraft.server.level.ServerPlayer;

public class DamageItemsEvent extends AbstractInstantEvent {
    @Override
    public void initPlayer(ServerPlayer player) {
        player.getInventory().items.forEach(itemStack -> {
            if(itemStack.isDamageableItem() && !itemStack.is(EntropyTags.ItemTags.DO_NOT_DAMAGE))
                itemStack.hurtAndBreak((int)Math.ceil((itemStack.getMaxDamage()-itemStack.getDamageValue())*player.getRandom().nextFloat()),player,serverPlayer1->{});
        });
        player.getInventory().armor.forEach(itemStack -> {
            if(itemStack.isDamageableItem() && !itemStack.is(EntropyTags.ItemTags.DO_NOT_DAMAGE))
                itemStack.hurtAndBreak((int)Math.ceil((itemStack.getMaxDamage()-itemStack.getDamageValue())*player.getRandom().nextFloat()),player,serverPlayer1->{});
        });
        player.getInventory().offhand.forEach(itemStack -> {
            if(itemStack.isDamageableItem() && !itemStack.is(EntropyTags.ItemTags.DO_NOT_DAMAGE))
                itemStack.hurtAndBreak((int)Math.ceil((itemStack.getMaxDamage()-itemStack.getDamageValue())*player.getRandom().nextFloat()),player,serverPlayer1->{});
        });
        super.initPlayer(player);
    }
}
