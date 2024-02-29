package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.CurrentState;
import dev.katcodes.forgedentropy.events.AbstractTimedEvent;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class LowPitchEvent extends AbstractTimedEvent {

    @OnlyIn(Dist.CLIENT)
    @Override
    public void initClient() {
        super.initClient();
        CurrentState.Get().forcePitch=true;
        CurrentState.Get().forcedPitch=0.375f;
    }

    @Override
    public void endClient() {
        super.endClient();
        CurrentState.Get().forcePitch=false;
        CurrentState.Get().forcedPitch=0f;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void render(GuiGraphics graphics, float tickDelta) {

    }

    @Override
    public String type() {
        return "pitch";
    }

    @Override
    public short getDuration() {
        return (short) (Config.baseEventDuration*2);
    }
}
