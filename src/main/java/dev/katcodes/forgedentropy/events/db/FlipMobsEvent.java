package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.CurrentState;
import dev.katcodes.forgedentropy.events.AbstractTimedEvent;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class FlipMobsEvent extends AbstractTimedEvent {
    @Override
    public void initClient() {
        super.initClient();
        CurrentState.Get().flipEntities=true;
    }

    @Override
    public void endClient() {
        super.endClient();
        CurrentState.Get().flipEntities=false;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void render(GuiGraphics graphics, float tickDelta) {

    }

    @Override
    public short getDuration() {
        return (short)(Config.baseEventDuration*1.5);
    }
}
