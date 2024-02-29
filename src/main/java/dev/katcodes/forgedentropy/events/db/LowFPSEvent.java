package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.events.AbstractTimedEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class LowFPSEvent extends AbstractTimedEvent {
    Minecraft minecraft;
    private int fps = 0;

    @OnlyIn(Dist.CLIENT)
    @Override
    public void initClient() {
        super.initClient();
        minecraft = Minecraft.getInstance();
        fps = this.minecraft.options.framerateLimit().get();
        this.minecraft.options.framerateLimit().set(10);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void endClient() {
        super.endClient();
        this.minecraft=Minecraft.getInstance();
        this.minecraft.options.framerateLimit().set(fps);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void render(GuiGraphics graphics, float tickDelta) {

    }

    @Override
    public short getDuration() {
        return (short) Config.baseEventDuration;
    }

    @Override
    public String type() {
        return "fps";
    }

    @Override
    public boolean isDisabledByAccessibilityMode() {
        return true;
    }
}
