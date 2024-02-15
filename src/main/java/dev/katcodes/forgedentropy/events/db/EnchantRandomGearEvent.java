package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.EntropyTags;
import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.events.AbstractInstantEvent;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.ArrayList;
import java.util.Collections;

public class EnchantRandomGearEvent extends AbstractInstantEvent {
    private static ArrayList<Enchantment> _enchantments = new ArrayList<Enchantment>() {
        {
            for (var enchantment : BuiltInRegistries.ENCHANTMENT) {
                if (!enchantment.isCurse()) {
                    add(enchantment);
                }
            }
            removeAll(BuiltInRegistries.ENCHANTMENT.getTag(EntropyTags.EnchantmentTags.DO_NOT_ENCHANT_WITH).stream().toList());
        }
    };

    @Override
    public void initPlayer(ServerPlayer player) {
        var inventory=new ArrayList<ItemStack>();
        inventory.addAll(player.getInventory().offhand);
        inventory.addAll(player.getInventory().armor);
        inventory.addAll(player.getInventory().items);

        Collections.shuffle(inventory);
        Collections.shuffle(_enchantments);

        for(var itemStack: inventory) {
            for(var enchantment: _enchantments) {
                if(enchantment.canEnchant(itemStack)){
                    var hasEnchantment = false;
                    var existingEnchantments = EnchantmentHelper.getEnchantments(itemStack);
                    for(Enchantment existingEnchantment: existingEnchantments.keySet()) {
                        if(existingEnchantment == enchantment || !existingEnchantment.isCompatibleWith(enchantment)) {
                            hasEnchantment = true;
                            break;
                        }
                    }
                    if(!hasEnchantment) {
                        itemStack.enchant(enchantment,enchantment.getMaxLevel());
                        return;
                    }

                }
            }
        }
        super.initPlayer(player);
    }
}
