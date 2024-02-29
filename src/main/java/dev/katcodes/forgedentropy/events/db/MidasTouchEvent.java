package dev.katcodes.forgedentropy.events.db;

import com.mojang.datafixers.util.Pair;
import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.EntropyTags;
import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.events.AbstractTimedEvent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MidasTouchEvent extends AbstractTimedEvent {


    @Override
    public void tick() {
        for(var player: ForgedEntropyMod.eventHandler.getActivePlayers()) {
            var minX = (int) (player.getX() - (player.getX() < 0 ? 1.5 : .5));
            var minY = (int) player.getY() - 1;
            var minZ = (int) (player.getZ() - (player.getZ() < 0 ? 1.5 : .5));
            var maxX = minX+1;
            var maxY = minY+3;
            var maxZ = minZ+1;
            var level = player.level();

            for(int ix = minX; ix <= maxX; ix++) {
                for(int iy = minY; iy <= maxY; iy++) {
                    for(int iz = minZ; iz <= maxZ; iz++) {
                        var blockPos = new BlockPos(ix,iy,iz);
                        if(level.getBlockState(blockPos).is(EntropyTags.BlockTags.IGNORED_BY_MIDAS_TOUCH))
                            continue;
                        var odds = player.getRandom().nextInt(100);
                        if(odds < 96)
                            level.setBlockAndUpdate(blockPos,
                                    level.dimension() == Level.NETHER ? Blocks.NETHER_GOLD_ORE.defaultBlockState() : Blocks.GOLD_ORE.defaultBlockState());
                        else if (odds < 98)
                            level.setBlockAndUpdate(blockPos,Blocks.RAW_GOLD_BLOCK.defaultBlockState());
                        else
                            level.setBlockAndUpdate(blockPos,Blocks.GOLD_BLOCK.defaultBlockState());
                    }
                }
            }
            var box = new AABB(minX,minY,minZ,maxX,maxY,maxZ);
            var mobs = level.getEntities(player,box,x -> x instanceof LivingEntity && x.isAlive() && !x.getType().is(EntropyTags.EntityTypeTags.IGNORED_BY_MIDAS_TOUCH));
            for(var mob: mobs) {
                ItemStack itemStack;
                switch(player.getRandom().nextInt(16)) {
                    case 0:
                        itemStack = new ItemStack(Items.GOLD_INGOT);
                        break;
                    case 1:
                        itemStack = new ItemStack(Items.GOLD_BLOCK);
                        break;
                    case 2:
                        itemStack = new ItemStack(Items.RAW_GOLD_BLOCK);
                        break;
                    case 3:
                        itemStack = new ItemStack(Items.GOLD_ORE);
                        break;
                    case 4:
                        itemStack = new ItemStack(Items.NETHER_GOLD_ORE);
                        break;
                    default:
                        itemStack = new ItemStack(Items.GOLD_NUGGET, player.getRandom().nextInt(6) + 2);
                        break;
                }

                var entityItem = new ItemEntity(level,mob.getX(),mob.getY(),mob.getZ(),itemStack);
                level.addFreshEntity(entityItem);
                mob.kill();
            }

            var inventoryItems = getPairs(player);

            for(var pair: inventoryItems) {
                var itemStack = pair.getFirst().get(pair.getSecond());
                if(itemStack.isEmpty() || itemStack.is(EntropyTags.ItemTags.IGNORED_BY_MIDAS_TOUCH) || itemStack.is(EntropyTags.ItemTags.MIDAS_TOUCH_GOLDEN_ITEMS))
                    continue;
                var item = itemStack.getItem();

                ItemStack newItemStack=itemStack;
                if(item.isEdible()) {
                    var odds = player.getRandom().nextInt(100);
                    if(item == Items.MELON_SLICE && odds < 50)
                        newItemStack = new ItemStack(Items.GLISTERING_MELON_SLICE, itemStack.getCount());
                    else if(odds < 75)
                        newItemStack = new ItemStack(Items.GOLDEN_CARROT, itemStack.getCount());
                    else if(odds < 95)
                        newItemStack = new ItemStack(Items.GOLDEN_APPLE, itemStack.getCount());
                    else
                        newItemStack = new ItemStack(Items.ENCHANTED_GOLDEN_APPLE, itemStack.getCount());
                } else if(item instanceof BlockItem) {
                    newItemStack = switch (player.getRandom().nextInt(6)) {
                        case 0 -> new ItemStack(Items.GOLD_BLOCK, Math.min(itemStack.getCount(), 3));
                        case 1 -> new ItemStack(Items.RAW_GOLD_BLOCK, Math.min(itemStack.getCount(), 3));
                        case 2 -> new ItemStack(Items.NETHER_GOLD_ORE, Math.min(itemStack.getCount(), 32));
                        default -> new ItemStack(Items.GOLD_ORE, Math.min(itemStack.getCount(), 10));
                    };
                } else if(item instanceof ArmorItem) {
                    if(((ArmorItem)item).getEquipmentSlot() == EquipmentSlot.HEAD)
                        newItemStack = new ItemStack(Items.GOLDEN_HELMET, itemStack.getCount());
                    else if(((ArmorItem)item).getEquipmentSlot() == EquipmentSlot.CHEST)
                        newItemStack = new ItemStack(Items.GOLDEN_CHESTPLATE, itemStack.getCount());
                    else if(((ArmorItem)item).getEquipmentSlot() == EquipmentSlot.LEGS)
                        newItemStack = new ItemStack(Items.GOLDEN_LEGGINGS, itemStack.getCount());
                    else if(((ArmorItem)item).getEquipmentSlot() == EquipmentSlot.FEET)
                        newItemStack = new ItemStack(Items.GOLDEN_BOOTS, itemStack.getCount());
                } else if (item instanceof PickaxeItem) {
                    newItemStack = new ItemStack(Items.GOLDEN_PICKAXE, itemStack.getCount());
                } else if (item instanceof AxeItem) {
                    newItemStack = new ItemStack(Items.GOLDEN_AXE, itemStack.getCount());
                } else if (item instanceof ShovelItem) {
                    newItemStack = new ItemStack(Items.GOLDEN_SHOVEL, itemStack.getCount());
                } else if (item instanceof HoeItem) {
                    newItemStack = new ItemStack(Items.GOLDEN_HOE, itemStack.getCount());
                } else if (item instanceof SwordItem) {
                    newItemStack = new ItemStack(Items.GOLDEN_SWORD, itemStack.getCount());
                } else if (item instanceof HorseArmorItem) {
                    newItemStack = new ItemStack(Items.GOLDEN_HORSE_ARMOR, itemStack.getCount());
                } else {
                    newItemStack = switch (player.getRandom().nextInt(3)) {
                        case 0 -> new ItemStack(Items.GOLD_INGOT, itemStack.getCount());
                        case 1 -> new ItemStack(Items.GOLD_NUGGET, itemStack.getCount());
                        default -> new ItemStack(Items.RAW_GOLD, itemStack.getCount());
                    };
                }

                pair.getFirst().set(pair.getSecond(),newItemStack);
            }
        }
        super.tick();
    }

    @NotNull
    private static ArrayList<Pair<NonNullList<ItemStack>, Integer>> getPairs(ServerPlayer player) {
        var playerInventory = player.getInventory();
        var inventoryItems = new ArrayList<Pair<NonNullList<ItemStack>, Integer>>() {
            {
                add(new Pair<NonNullList<ItemStack>, Integer>(playerInventory.offhand,0));
                add(new Pair<NonNullList<ItemStack>, Integer>(playerInventory.armor,0));
                add(new Pair<NonNullList<ItemStack>, Integer>(playerInventory.armor,1));
                add(new Pair<NonNullList<ItemStack>, Integer>(playerInventory.armor,2));
                add(new Pair<NonNullList<ItemStack>, Integer>(playerInventory.armor,3));
                add(new Pair<NonNullList<ItemStack>, Integer>(playerInventory.items, playerInventory.selected));
            }
        };
        return inventoryItems;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void render(GuiGraphics graphics, float tickDelta) {

    }

    @Override
    public short getDuration() {
        return (short) (Config.baseEventDuration * 0.2f);
    }
}
