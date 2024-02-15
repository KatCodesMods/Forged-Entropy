package dev.katcodes.forgedentropy.events;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.CurrentState;
import dev.katcodes.forgedentropy.ForgedEntropyMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.util.ColorRGBA;
import net.minecraft.util.FastColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import java.util.List;

public abstract class AbstractInstantEvent implements ChaosEvent{


    @Override
    public void endClient() {

    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void render(GuiGraphics graphics, float tickDelta) {

    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void renderQueueItem(GuiGraphics graphics, float tickDelta, int x, int y) {
        if(CurrentState.Get().doNotShowEvents)
            return;
        Component name = Component.translatable(ChaosEventRegistry.getTranslationKey(this));
        if(isDisabledByAccessibilityMode() && Config.accessibilityMode)
            name.getStyle().withStrikethrough(true);

        int size = Minecraft.getInstance().font.width(name);
        graphics.drawCenteredString(Minecraft.getInstance().font,name,Minecraft.getInstance().getWindow().getGuiScaledWidth()-size-40,y, FastColor.ARGB32.color(255,255, 255, 255));

    }

    @Override
    public void tick() {

    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void tickClient() {

    }


    @Override
    public short getTickCount() {
        return 0;
    }

    @Override
    public void setTickCount(short index) {

    }

    @Override
    public short getDuration() {
        return 0;
    }

    @Override
    public boolean hasEnded() {
        return true;
    }

    @Override
    public void setEnded(boolean ended) {

    }

}
