package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.CurrentState;
import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.events.AbstractTimedEvent;
import net.minecraft.client.gui.GuiGraphics;

public class BlurEvent extends AbstractTimedEvent {
    @Override
    public void initClient() {
        CurrentState.Get().blur= true;
    }

    @Override
    public void endClient() {
        CurrentState.Get().blur=false;
        this.hasEnded=true;
    }

    @Override
    public void render(GuiGraphics graphics, float tickDelta) {

    }

    @Override
    public String type() {
        return "shader";
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public short getDuration() {
        return (short) (Config.baseEventDuration*1.5f);
    }
}
