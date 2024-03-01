package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.CurrentState;
import dev.katcodes.forgedentropy.events.AbstractTimedEvent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.RandomSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.Random;

public class MouseDriftingEvent extends AbstractTimedEvent {
    Random random = new Random();

    @OnlyIn(Dist.CLIENT)
    @Override
    public void initClient() {
        super.initClient();
        CurrentState.Get().mouseDrifting = true;
        CurrentState.Get().mouseDriftingSignX = random.nextBoolean() ? -1:1;
        CurrentState.Get().mouseDriftingSignY = random.nextBoolean() ? -1:1;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void endClient() {
        super.endClient();
        CurrentState.Get().mouseDrifting = false;
        CurrentState.Get().mouseDriftingSignX = 0;
        CurrentState.Get().mouseDriftingSignY = 0;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void render(GuiGraphics graphics, float tickDelta) {

    }

    @Override
    public short getDuration() {
        return (short) (Config.baseEventDuration * 1.5);
    }
}
