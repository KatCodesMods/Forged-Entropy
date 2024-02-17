package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.CurrentState;
import dev.katcodes.forgedentropy.events.AbstractTimedEvent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.horse.Horse;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

public class ForceHorseRidingEvent extends AbstractTimedEvent {
    private List<Horse> spawnedHorses = new ArrayList<>();

    @OnlyIn(Dist.CLIENT)
    @Override
    public void initClient() {
        super.initClient();
        CurrentState.Get().forceRiding = true;
    }


    @Override
    public void initPlayer(ServerPlayer player) {
        super.initPlayer(player);
        spawnedHorses.add(EntityType.HORSE.spawn(player.serverLevel(),null, horse -> {
            horse.tameWithName(player);
            horse.equipSaddle(SoundSource.NEUTRAL);
            horse.setInvulnerable(true);
            player.startRiding(horse);
        }, player.blockPosition(), MobSpawnType.SPAWN_EGG,false,false));
        CurrentState.Get().forceRiding=true;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void endClient() {
        super.endClient();
        CurrentState.Get().forceRiding=false;
    }

    @Override
    public void end() {
        super.end();
        CurrentState.Get().forceRiding=false;
        spawnedHorses.forEach(Entity::discard);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void render(GuiGraphics graphics, float tickDelta) {

    }

    @Override
    public short getDuration() {
        return (short) Config.baseEventDuration;
    }
}
