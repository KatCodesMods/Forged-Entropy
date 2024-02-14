package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.events.AbstractTimedEvent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.level.GameType;

public class AdventureEvent extends AbstractTimedEvent {

    @Override
    public void init() {
        ForgedEntropyMod.eventHandler.getActivePlayers().forEach(serverPlayer -> serverPlayer.setGameMode(GameType.ADVENTURE));
    }
    public void end() {
        ForgedEntropyMod.eventHandler.getActivePlayers().forEach(serverPlayer -> serverPlayer.setGameMode(GameType.SURVIVAL));
        this.hasEnded=true;
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void render(GuiGraphics graphics, float tickDelta) {

    }

    @Override
    public short getDuration() {
        return (short) (Config.baseEventDuration*1.f);
    }
}
