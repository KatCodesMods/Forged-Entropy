package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.events.AbstractTimedEvent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Random;

public class RandomCreeperEvent extends AbstractTimedEvent  {
    @OnlyIn(Dist.CLIENT)
    @Override
    public void render(GuiGraphics graphics, float tickDelta) {

    }

    @Override
    public void tick() {
        if(getTickCount()%70==0) {
            ForgedEntropyMod.eventHandler.getActivePlayers().forEach(player -> {
                if(new Random().nextInt(10)>=6) {
                    EntityType.CREEPER.spawn(player.serverLevel(), player.blockPosition().north(), MobSpawnType.SPAWN_EGG);
                    //todo: play sound
                    //player.connection.send(new ClientboundSoundPacket(BuiltInRegistries.SOUND_EVENT.getHolder(SoundEvents.CREEPER_PRIMED), SoundSource.HOSTILE, player.getX(), player.getY(), player.getZ(), 1.0f, 0.5f, RandomSource.create().nextLong()));
                }
            });
        }
        super.tick();

    }

    @Override
    public short getDuration() {
        return (short) (Config.baseEventDuration*0.75f);
    }
}
