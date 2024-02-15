package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.EntropyTags;
import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.events.AbstractInstantEvent;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CurseRandomGearEvent extends AbstractInstantEvent {
    private static List<Enchantment> _curses;

    @Override
    public void init() {
        if(_curses==null)
        {
            _curses=new ArrayList<>();
            _curses=BuiltInRegistries.ENCHANTMENT.stream().filter(Enchantment::isCurse).collect(Collectors.toList());
        }
        super.init();
    }

    @Override
    public void initPlayer(ServerPlayer player) {
        var inventory=new ArrayList<ItemStack>();
        inventory.addAll(player.getInventory().offhand);
        inventory.addAll(player.getInventory().armor);
        inventory.addAll(player.getInventory().items);

        Collections.shuffle(inventory);
        Collections.shuffle(_curses);

        for(var itemStack: inventory) {
            if(itemStack.is(EntropyTags.ItemTags.DO_NOT_CURSE))
                continue;
            for(var curse: _curses) {
                if(curse.canEnchant(itemStack)){
                    var hasCurse = false;
                    var existingEnchantments = EnchantmentHelper.getEnchantments(itemStack);
                    hasCurse = existingEnchantments.keySet().stream().anyMatch(Enchantment::isCurse);

                    if(!hasCurse) {
                        itemStack.enchant(curse,curse.getMaxLevel());
                        return;
                    }

                }
            }
        }
        super.initPlayer(player);
    }
}
