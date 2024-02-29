package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.EntropyTags;
import dev.katcodes.forgedentropy.events.AbstractInstantEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;

public class LevitationEvent extends AbstractInstantEvent  {

    @Override
    public void initPlayer(ServerPlayer player) {
        super.initPlayer(player);
        player.level().getEntities(player,new AABB(player.blockPosition().offset(50,50,50).getCenter(),player.blockPosition().offset(-50,-50,-50).getCenter())).forEach(entity ->
        {
            if(!(entity instanceof ServerPlayer) && entity instanceof LivingEntity livingEntity && !livingEntity.getType().is(EntropyTags.EntityTypeTags.DO_NOT_LEVITATE)) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.LEVITATION, (int) (Config.baseEventDuration * 0.5),4,true,false));
            }
        });
        if(!player.getType().is(EntropyTags.EntityTypeTags.DO_NOT_LEVITATE))
            player.addEffect(new MobEffectInstance(MobEffects.LEVITATION, (int) (Config.baseEventDuration * 0.5),4,true,false));
    }
}
