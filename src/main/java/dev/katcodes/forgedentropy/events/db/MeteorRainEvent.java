package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.events.AbstractTimedEvent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class MeteorRainEvent extends AbstractTimedEvent {
    RandomSource random;

    @Override
    public void init() {
        super.init();
        random = RandomSource.create();
    }


    @OnlyIn(Dist.CLIENT)
    @Override
    public void render(GuiGraphics graphics, float tickDelta) {

    }

    @Override
    public void tick() {
        if(getTickCount() % 20 == 0) {
            for(int i=0; i<7 ; i ++ ) {
                ForgedEntropyMod.eventHandler.getActivePlayers().forEach(serverPlayer -> {
                    LargeFireball meteor = new LargeFireball(serverPlayer.level(),serverPlayer,0,-1 * (random.nextInt(4) + 1),0,2);
                    meteor.moveTo(serverPlayer.getX() + random.nextInt(100) - 50, serverPlayer.getY()+50+ random.nextInt(10)-5, serverPlayer.getZ() + random.nextInt(100) - 50);
                    serverPlayer.level().addFreshEntity(meteor);
                });
            }
        }
        if(getTickCount() == getTickCount()/2) {
            ForgedEntropyMod.eventHandler.getActivePlayers().forEach(serverPlayer -> {
                LargeFireball meteor = new LargeFireball(serverPlayer.level(),serverPlayer,0,-1 * (random.nextInt(4) + 1),0,4);
                meteor.moveTo(serverPlayer.getX() + random.nextInt(100) - 50, serverPlayer.getY()+50+ random.nextInt(10)-5, serverPlayer.getZ() + random.nextInt(100) - 50);
                serverPlayer.level().addFreshEntity(meteor);
            });
        }
        super.tick();
    }

    @Override
    public String type() {
        return "rain";
    }

    @Override
    public short getDuration() {
        return (short) Config.baseEventDuration;
    }
}
