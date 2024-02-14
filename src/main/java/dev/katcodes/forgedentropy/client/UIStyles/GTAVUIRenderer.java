package dev.katcodes.forgedentropy.client.UIStyles;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.FastColor;

import static java.lang.Math.floor;

public class GTAVUIRenderer implements UIRenderer{

    //todo: Add voting client to client
    public GTAVUIRenderer() {

    }

    @Override
    public void renderTimer(GuiGraphics guiGraphics, int width, double time, double timerDuration) {

        guiGraphics.fill(0,0,width,10,150 << 24);
        guiGraphics.fill(0,0, (int) floor(width*(time/timerDuration)),10, FastColor.ARGB32.color(255,70,150,70)); //(this.votingClient != null ? votingClient.getColor(255) : ColorHelper.Argb.getArgb(255,70,150,70)));
    }
}
