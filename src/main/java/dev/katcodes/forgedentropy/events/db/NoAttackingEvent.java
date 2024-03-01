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
import net.neoforged.neoforge.client.event.InputEvent;

public class NoAttackingEvent extends AbstractTimedEvent {
    private InputConstants.Key boundAttackKey;

    @Override
    public void initClient() {
        Options options = Minecraft.getInstance().options;
        boundAttackKey = options.keyAttack.getKey();
        options.keyAttack.setKey(InputConstants.UNKNOWN);
        options.keyAttack.setDown(false);
        KeyMapping.resetMapping();
        super.initClient();
    }


    @Override
    public void endClient() {

        Options options = Minecraft.getInstance().options;
        options.setKey(options.keyAttack, boundAttackKey);
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
