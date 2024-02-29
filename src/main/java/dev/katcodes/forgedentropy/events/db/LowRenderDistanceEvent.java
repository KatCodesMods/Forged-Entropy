package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.events.AbstractTimedEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class LowRenderDistanceEvent extends AbstractTimedEvent {
    Minecraft minecraft;
    private int viewDistance = 0;

    @Override
    public void initClient() {
        super.initClient();
        minecraft = Minecraft.getInstance();
        viewDistance = this.minecraft.options.renderDistance().get();
        this.minecraft.options.renderDistance().set(getRenderDistance());
    }

    protected int getRenderDistance() {
        return 2;
    }

    @Override
    public void endClient() {
        super.endClient();
        minecraft = Minecraft.getInstance();
        this.minecraft.options.renderDistance().set(viewDistance);
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
        return "renderDistance";
    }
}
