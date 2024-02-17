package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.events.AbstractInstantEvent;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.Tags;

import java.util.Random;

public class GiveRandomOreEvent extends AbstractInstantEvent {

    @Override
    public void initPlayer(ServerPlayer player) {
        super.initPlayer(player);

        Random random=new Random();
        var items = BuiltInRegistries.ITEM.getTag(Tags.Items.ORES);
        items.ifPresent(item -> {
            int index=RandomSource.create().nextInt(0,item.size()-1);
            ItemStack itemStack=new ItemStack(item.getRandomElement(player.getRandom()).orElseGet(Items.COAL::builtInRegistryHolder),random.nextInt(0,15));
            if(!player.getInventory().add(itemStack))
                player.spawnAtLocation(itemStack);
        });
    }
}
