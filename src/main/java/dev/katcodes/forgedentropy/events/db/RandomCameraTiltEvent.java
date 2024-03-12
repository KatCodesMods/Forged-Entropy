package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.CurrentState;
import dev.katcodes.forgedentropy.events.AbstractTimedEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.RandomSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class RandomCameraTiltEvent extends AbstractTimedEvent {


    @Override
    public void initClient() {
        super.initClient();
        RandomSource random = Minecraft.getInstance().level.random;
        CurrentState.Get().cameraRoll = random.nextInt(360) + random.nextFloat();
    }

    @Override
    public void endClient() {
        CurrentState.Get().cameraRoll = 0f;
        super.endClient();
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

    @Override
    public boolean isDisabledByAccessibilityMode() {
        return true;
    }
}
