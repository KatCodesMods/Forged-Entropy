package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.CurrentState;
import dev.katcodes.forgedentropy.events.AbstractTimedEvent;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class ForceFrontViewEvent extends AbstractTimedEvent {

    @OnlyIn(Dist.CLIENT)
    @Override
    public void initClient() {
        super.initClient();
        CurrentState.Get().thirdPersonView=true;
        CurrentState.Get().frontView=true;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void endClient() {
        super.endClient();
        CurrentState.Get().thirdPersonView=false;
        CurrentState.Get().frontView=false;
    }

    @Override
    public String type() {
        return "camera";
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void render(GuiGraphics graphics, float tickDelta) {

    }

    @Override
    public short getDuration() {
        return (short) Config.baseEventDuration;
    }
}
