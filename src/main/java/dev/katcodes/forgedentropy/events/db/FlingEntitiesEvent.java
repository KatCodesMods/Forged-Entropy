package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.EntropyTags;
import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.events.AbstractInstantEvent;
import dev.katcodes.forgedentropy.server.EventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FlingEntitiesEvent extends AbstractInstantEvent {
    private void fling(LivingEntity entity) {
        RandomSource random=entity.getRandom();
        entity.setDeltaMovement(random.nextInt(-10,9)+random.nextDouble(),random.nextInt(3)+random.nextDouble(),random.nextInt(-10,9)+random.nextDouble());

    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void initClient() {
        super.initClient();
        fling(Minecraft.getInstance().player);
    }

    @Override
    public void init() {
        EventHandler eventHandler= ForgedEntropyMod.eventHandler;
        List<ServerLevel> worlds=new ArrayList<>();
        eventHandler.getActivePlayers().forEach(serverPlayer -> {
            serverPlayer.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING,140));
            ServerLevel world = serverPlayer.serverLevel();
            if(!worlds.contains(world))
                worlds.add(world);
        });
        worlds.forEach(serverLevel -> {
            serverLevel.getAllEntities().forEach(entity -> {
                if(entity instanceof LivingEntity livingEntity && !livingEntity.getType().is(EntropyTags.EntityTypeTags.DO_NOT_FLING)) {
                    fling(livingEntity);
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING,140));
                }
            });
        });
    }
}
