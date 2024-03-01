package dev.katcodes.forgedentropy.events.db;

import com.mojang.blaze3d.platform.InputConstants;
import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.events.AbstractTimedEvent;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class NoUseKeyEvent extends AbstractTimedEvent {
    private InputConstants.Key boundUseKey;

    @Override
    public void initClient() {
        Options options = Minecraft.getInstance().options;
        boundUseKey = options.keyUse.getKey();
        options.keyUse.setKey(InputConstants.UNKNOWN);
        options.keyUse.setDown(false);
        KeyMapping.resetMapping();
        super.initClient();
    }


    @Override
    public void endClient() {

        Options options = Minecraft.getInstance().options;
        options.setKey(options.keyUse, boundUseKey);
        KeyMapping.resetMapping();
        super.endClient();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void render(GuiGraphics graphics, float tickDelta) {

    }

    @Override
    public String type() {
        return "attack";
    }

    @Override
    public short getDuration() {
        return (short) (Config.baseEventDuration);
    }
}
