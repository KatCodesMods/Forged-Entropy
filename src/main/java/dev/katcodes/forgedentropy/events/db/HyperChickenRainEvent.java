package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.events.AbstractTimedEvent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;

import java.util.Random;

public class HyperChickenRainEvent extends AbstractTimedEvent {
    Random random;

    @Override
    public void init() {
        random=new Random();
        super.init();
    }

    @Override
    public void end() {
        this.hasEnded=true;
    }

    @Override
    public void render(GuiGraphics graphics, float tickDelta) {

    }

    @Override
    public void tick() {
        if(getTickCount() %10 ==0) {
            for(int i=0;i<10;i++) {
                ForgedEntropyMod.eventHandler.getActivePlayers().forEach(serverPlayer -> {
                    EntityType.CHICKEN.spawn(serverPlayer.serverLevel(),serverPlayer.blockPosition().offset((random.nextInt(50)-25),50+(random.nextInt(10)-5),(random.nextInt(50)-25)), MobSpawnType.COMMAND);

                });
            }
        }
        super.tick();
    }

    @Override
    public String type() {
        return "rain";
    }

    @Override
    public short getDuration() {
        return (short) (Config.baseEventDuration);
    }
}
