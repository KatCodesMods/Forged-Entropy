package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.events.AbstractInstantEvent;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import oshi.util.tuples.Triplet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.stream.IntStream;

public class DowngradeRandomGearEvent extends AbstractInstantEvent {
    private static HashMap<Item,Item> _downgrades = new HashMap<>(){
        {
            // HELMET
            put(Items.GOLDEN_HELMET, Items.LEATHER_HELMET);
            put(Items.CHAINMAIL_HELMET, Items.GOLDEN_HELMET);
            put(Items.IRON_HELMET, Items.CHAINMAIL_HELMET);
            put(Items.TURTLE_HELMET, Items.IRON_HELMET);
            put(Items.DIAMOND_HELMET, Items.TURTLE_HELMET);
            put(Items.NETHERITE_HELMET, Items.DIAMOND_HELMET);

            // CHESTPLATE
            put(Items.GOLDEN_CHESTPLATE, Items.LEATHER_CHESTPLATE);
            put(Items.CHAINMAIL_CHESTPLATE, Items.GOLDEN_CHESTPLATE);
            put(Items.IRON_CHESTPLATE, Items.CHAINMAIL_CHESTPLATE);
            put(Items.DIAMOND_CHESTPLATE, Items.IRON_CHESTPLATE);
            put(Items.NETHERITE_CHESTPLATE, Items.DIAMOND_CHESTPLATE);

            // LEGGINGS
            put(Items.GOLDEN_LEGGINGS, Items.LEATHER_LEGGINGS);
            put(Items.CHAINMAIL_LEGGINGS, Items.GOLDEN_LEGGINGS);
            put(Items.IRON_LEGGINGS, Items.CHAINMAIL_LEGGINGS);
            put(Items.DIAMOND_LEGGINGS, Items.IRON_LEGGINGS);
            put(Items.NETHERITE_LEGGINGS, Items.DIAMOND_LEGGINGS);

            // BOOTS
            put(Items.GOLDEN_BOOTS, Items.LEATHER_BOOTS);
            put(Items.CHAINMAIL_BOOTS, Items.GOLDEN_BOOTS);
            put(Items.IRON_BOOTS, Items.CHAINMAIL_BOOTS);
            put(Items.DIAMOND_BOOTS, Items.IRON_BOOTS);
            put(Items.NETHERITE_BOOTS, Items.DIAMOND_BOOTS);

            // SWORD
            put(Items.GOLDEN_SWORD, Items.WOODEN_SWORD);
            put(Items.STONE_SWORD, Items.GOLDEN_SWORD);
            put(Items.IRON_SWORD, Items.STONE_SWORD);
            put(Items.DIAMOND_SWORD, Items.IRON_SWORD);
            put(Items.NETHERITE_SWORD, Items.DIAMOND_SWORD);

            // SHOVEL
            put(Items.GOLDEN_SHOVEL, Items.WOODEN_SHOVEL);
            put(Items.STONE_SHOVEL, Items.GOLDEN_SHOVEL);
            put(Items.IRON_SHOVEL, Items.STONE_SHOVEL);
            put(Items.DIAMOND_SHOVEL, Items.IRON_SHOVEL);
            put(Items.NETHERITE_SHOVEL, Items.DIAMOND_SHOVEL);

            // PICKAXE
            put(Items.GOLDEN_PICKAXE, Items.WOODEN_PICKAXE);
            put(Items.STONE_PICKAXE, Items.GOLDEN_PICKAXE);
            put(Items.IRON_PICKAXE, Items.STONE_PICKAXE);
            put(Items.DIAMOND_PICKAXE, Items.IRON_PICKAXE);
            put(Items.NETHERITE_PICKAXE, Items.DIAMOND_PICKAXE);

            // AXE
            put(Items.GOLDEN_AXE, Items.WOODEN_AXE);
            put(Items.STONE_AXE, Items.GOLDEN_AXE);
            put(Items.IRON_AXE, Items.STONE_AXE);
            put(Items.DIAMOND_AXE, Items.IRON_AXE);
            put(Items.NETHERITE_AXE, Items.DIAMOND_AXE);

            // HOE
            put(Items.GOLDEN_HOE, Items.WOODEN_HOE);
            put(Items.STONE_HOE, Items.GOLDEN_HOE);
            put(Items.IRON_HOE, Items.STONE_HOE);
            put(Items.DIAMOND_HOE, Items.IRON_HOE);
            put(Items.NETHERITE_HOE, Items.DIAMOND_HOE);
        }
    };

    @Override
    public void initPlayer(ServerPlayer player) {
        var inventory = player.getInventory();
        var items = new ArrayList<Triplet<NonNullList<ItemStack>, Integer, ItemStack>>();
        items.addAll(IntStream.range(0,inventory.items.size())
                .mapToObj(i -> new Triplet<NonNullList<ItemStack>,Integer,ItemStack>(inventory.items,i,inventory.items.get(i))).toList()
        );
        items.addAll(IntStream.range(0,inventory.armor.size())
                .mapToObj(i -> new Triplet<NonNullList<ItemStack>,Integer,ItemStack>(inventory.armor,i,inventory.armor.get(i))).toList()
        );
        items.addAll(IntStream.range(0,inventory.offhand.size())
                .mapToObj(i -> new Triplet<NonNullList<ItemStack>,Integer,ItemStack>(inventory.offhand,i,inventory.offhand.get(i))).toList()
        );

        Collections.shuffle(items);

        for(var element: items) {
            var itemStack = element.getC();
            var item = itemStack.getItem();
            if(_downgrades.containsKey(item)) {
                downgrade(item,itemStack, element.getA(),element.getB(), player.getRandom());
                break;
            }
        }
        super.initPlayer(player);
    }

    private void downgrade(Item item, ItemStack itemStack, NonNullList<ItemStack> inventory, Integer index, RandomSource random) {
        var newItem=_downgrades.get(item);
        var newItemStack = new ItemStack(newItem);

        var enchantments = EnchantmentHelper.getEnchantments(itemStack);
        for(var enchantment: enchantments.entrySet()) {
            newItemStack.enchant(enchantment.getKey(),enchantment.getValue());
        }

        var newDamage = (float)itemStack.getDamageValue() / (float) itemStack.getMaxDamage() * (float) newItemStack.getMaxDamage();
        newItemStack.setDamageValue((int) newDamage);
        //todo: copy NBT maybe?
        inventory.set(index,newItemStack);
        if(_downgrades.containsKey(newItem) && random.nextDouble() < 0.25D) {
            downgrade(newItem,newItemStack,inventory,index,random);
        }
    }
}
