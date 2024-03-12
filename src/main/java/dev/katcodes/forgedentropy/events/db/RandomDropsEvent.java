package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.CurrentState;
import dev.katcodes.forgedentropy.events.AbstractTimedEvent;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class RandomDropsEvent extends AbstractTimedEvent {
    @Override
    public void init() {
        super.init();
        CurrentState.Get().randomDrops = true;
    }

    @Override
    public void end() {
        super.end();
        CurrentState.Get().randomDrops = false;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void render(GuiGraphics graphics, float tickDelta) {

    }

    @Override
    public String type() {
        return "drops";
    }

    @Override
    public short getDuration() {
        return (short) Config.baseEventDuration;
    }
}
