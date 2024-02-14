package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.CurrentState;
import dev.katcodes.forgedentropy.events.AbstractTimedEvent;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class HideEventsEvent extends AbstractTimedEvent {

    @OnlyIn(Dist.CLIENT)
    @Override
    public void initClient() {
        CurrentState.Get().doNotShowEvents=true;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void endClient() {
        CurrentState.Get().doNotShowEvents=false;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void render(GuiGraphics graphics, float tickDelta) {

    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public short getDuration() {
        return (short) (Config.baseEventDuration*3);
    }
}
