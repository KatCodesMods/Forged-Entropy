package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.CurrentState;
import dev.katcodes.forgedentropy.events.AbstractTimedEvent;
import net.minecraft.client.gui.GuiGraphics;

public class BlackAndWhiteEvent extends AbstractTimedEvent {
    @Override
    public void initClient() {
        CurrentState.Get().blackAndWhite=true;
    }

    @Override
    public void endClient() {
        CurrentState.Get().blackAndWhite=false;
    }

    @Override
    public void render(GuiGraphics graphics, float tickDelta) {

    }

    @Override
    public String type() {
        return "shader";
    }

    @Override
    public short getDuration() {
        return (short)(Config.baseEventDuration*1.5f);
    }
}
