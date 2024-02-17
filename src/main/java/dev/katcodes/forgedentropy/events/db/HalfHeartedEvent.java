package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.events.AbstractTimedEvent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.HashMap;
import java.util.Map;

public class HalfHeartedEvent extends AbstractTimedEvent {
    private Map<ServerPlayer,Float> previousHealth = new HashMap<>();
    private Map<ServerPlayer, Double> previousMax = new HashMap<>();

    @Override
    public void initPlayer(ServerPlayer player) {
        super.initPlayer(player);
        previousHealth.put(player,player.getHealth());
        previousMax.put(player,player.getAttribute(Attributes.MAX_HEALTH).getBaseValue());
        player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(1);

        if(player.getHealth()>1)
            player.setHealth(1);
    }

    @Override
    public void end() {
        super.end();
        ForgedEntropyMod.eventHandler.getActivePlayers().forEach(serverPlayer -> {
            serverPlayer.getAttribute(Attributes.MAX_HEALTH).setBaseValue(previousMax.get(serverPlayer));
            serverPlayer.setHealth(previousHealth.get(serverPlayer));
        });
    }

    @Override
    public void tick() {
        if(this.getTickCount()%20 == 0 ) {
            ForgedEntropyMod.eventHandler.getActivePlayers().forEach(serverPlayer -> {

                if(!previousMax.containsKey(serverPlayer))
                    previousMax.put(serverPlayer,serverPlayer.getAttribute(Attributes.MAX_HEALTH).getBaseValue());
                if(!previousHealth.containsKey(serverPlayer))
                    previousHealth.put(serverPlayer,serverPlayer.getHealth());


                serverPlayer.getAttribute(Attributes.MAX_HEALTH).setBaseValue(1);


                if(serverPlayer.getHealth()>1)
                    serverPlayer.setHealth(1);
            });
        }
        super.tick();
    }

    @Override
    public String type() {
        return "health";
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void render(GuiGraphics graphics, float tickDelta) {

    }

    @Override
    public short getDuration() {
        return (short)(Config.baseEventDuration*1.25);
    }
}
