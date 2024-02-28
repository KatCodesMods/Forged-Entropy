package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.events.AbstractInstantEvent;
import dev.katcodes.forgedentropy.events.AbstractTimedEvent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.Random;

public class IntenseThunderStormEvent extends AbstractTimedEvent {
    Random random;
    @Override
    public void init() {
        super.init();
        random = new Random();
        ForgedEntropyMod.eventHandler.server.overworld().setWeatherParameters(0,this.getDuration(),true,true);

    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void render(GuiGraphics graphics, float tickDelta) {

    }

    @Override
    public void tick() {
        if(getTickCount()%10 == 0) {
            ForgedEntropyMod.eventHandler.getActivePlayers().forEach(serverPlayer -> {
                LightningBolt lightningBolt= EntityType.LIGHTNING_BOLT.create(serverPlayer.level());
                lightningBolt.moveTo(Vec3.atCenterOf(serverPlayer.level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, new BlockPos(serverPlayer.getBlockX()+(random.nextInt(50)-25),serverPlayer.getBlockY()+50+(random.nextInt(10)-5),serverPlayer.getBlockZ()+(random.nextInt(60)-25)))));
                serverPlayer.level().addFreshEntity(lightningBolt);
            });
        }
    }

    @Override
    public String type() {
        return "rain";
    }

    @Override
    public short getDuration() {
        return (short) (Config.baseEventDuration * 1.25);
    }
}
