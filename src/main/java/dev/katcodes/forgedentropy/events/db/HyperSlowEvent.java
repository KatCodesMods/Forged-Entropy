package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.events.AbstractTimedEvent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class HyperSlowEvent extends AbstractTimedEvent {
    AttributeModifier modifier;
    @Override
    public void init() {
        modifier = new AttributeModifier("hyperSpeed",-0.8d,AttributeModifier.Operation.MULTIPLY_TOTAL);
        super.init();
    }

    @Override
    public void initPlayer(ServerPlayer player) {
        super.initPlayer(player);
        player.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(modifier);
    }

    @Override
    public void endPlayer(ServerPlayer player) {
        player.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(modifier.getId());
        super.endPlayer(player);

    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void render(GuiGraphics graphics, float tickDelta) {

    }
    @Override
    public void tick() {
        if(getTickCount() % 20 ==0 ) {
            ForgedEntropyMod.eventHandler.getActivePlayers().forEach(serverPlayer ->  {
                if(serverPlayer.getAttribute(Attributes.MOVEMENT_SPEED).getModifier(modifier.getId()) == null)
                    serverPlayer.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(modifier);
            });
        }
        super.tick();
    }

    @Override
    public String type() {
        return "speed";
    }

    @Override
    public short getDuration() {
        return (short) (Config.baseEventDuration * 1.25);
    }
}
