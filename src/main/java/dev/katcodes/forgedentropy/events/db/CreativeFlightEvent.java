package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.events.AbstractTimedEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class CreativeFlightEvent extends AbstractTimedEvent {


    @Override
    public void initClient() {
        super.initClient();
        Player player = Minecraft.getInstance().player;
        if(player!=null) {
            if(!(player.isCreative() || player.isSpectator())) {
                player.getAbilities().mayfly = true;
            }
        }
    }


    @Override
    public void endClient() {
        super.endClient();
        Player player = Minecraft.getInstance().player;
        if(player!=null) {
            if(!(player.isCreative() || player.isSpectator())) {
                player.getAbilities().mayfly = false;
            }
        }
    }


    @Override
    public void init() {
        super.init();
        ForgedEntropyMod.eventHandler.getActivePlayers().forEach(serverPlayer -> {
            if(!(serverPlayer.isCreative() || serverPlayer.isSpectator()))
                serverPlayer.getAbilities().mayfly = true;
        });
    }

    @Override
    public void end() {
        super.end();
        ForgedEntropyMod.eventHandler.getActivePlayers().forEach(serverPlayer -> {
            if(!(serverPlayer.isCreative() || serverPlayer.isSpectator()))
                serverPlayer.getAbilities().mayfly = false;
        });
    }

    @Override
    public void render(GuiGraphics graphics, float tickDelta) {

    }

    @Override
    public short getDuration() {
        return (short) (Config.baseEventDuration * 0.75f);
    }
}
