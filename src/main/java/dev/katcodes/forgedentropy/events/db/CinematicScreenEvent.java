package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.CurrentState;
import dev.katcodes.forgedentropy.events.AbstractTimedEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import org.joml.Math;

public class CinematicScreenEvent extends AbstractTimedEvent {
    public boolean originalCamera;

    @Override
    public void initClient() {
        super.initClient();
        originalCamera=Minecraft.getInstance().options.smoothCamera;
        Minecraft.getInstance().options.smoothCamera=true;
        CurrentState.Get().forcedFov=true;
        CurrentState.Get().fov=60;
    }

    @Override
    public void tickClient() {
        Minecraft.getInstance().options.smoothCamera=true;
    }

    @Override
    public void endClient() {
        this.hasEnded=true;
        Minecraft.getInstance().options.smoothCamera=originalCamera;
        CurrentState.Get().forcedFov=false;
        CurrentState.Get().fov=0;
    }

    @Override
    public void render(GuiGraphics graphics, float tickDelta) {
        int borderHeight = (int) Math.floor(Minecraft.getInstance().getWindow().getGuiScaledHeight()*0.12f);
        graphics.fill(0,0,Minecraft.getInstance().getWindow().getGuiScaledWidth(),borderHeight,255 << 24);
        graphics.fill(0,Minecraft.getInstance().getWindow().getGuiScaledHeight()-borderHeight,Minecraft.getInstance().getWindow().getGuiScaledWidth(),Minecraft.getInstance().getWindow().getGuiScaledHeight(),255 << 24);
    }

    @Override
    public String type() {
        return "screenAspect";
    }

    @Override
    public short getDuration() {
        return (short)Config.baseEventDuration;
    }
}
