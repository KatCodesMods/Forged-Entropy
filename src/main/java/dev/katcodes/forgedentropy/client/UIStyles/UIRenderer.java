package dev.katcodes.forgedentropy.client.UIStyles;

import net.minecraft.client.gui.GuiGraphics;

public interface UIRenderer {
    public void renderTimer(GuiGraphics guiGraphics, int width, double time, double timerDuration);
}
