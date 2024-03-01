package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.events.AbstractInstantEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.block.Blocks;

import java.util.Random;

public class RaidEvent extends AbstractInstantEvent {

    @Override
    public void initPlayer(ServerPlayer player) {
        super.initPlayer(player);
        var villager = new Villager(EntityType.VILLAGER,player.level());
        villager.setPos(player.position());
        player.level().setBlockAndUpdate(player.blockPosition().offset(0,-1,0), Blocks.LECTERN.defaultBlockState());
        player.level().addFreshEntity(villager);
        player.addEffect(new MobEffectInstance(MobEffects.BAD_OMEN,1000,1+(new Random().nextInt(5))));
    }
}
