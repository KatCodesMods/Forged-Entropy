package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.events.AbstractTimedEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.lwjgl.system.MathUtil;

import java.util.Random;

import static net.minecraft.util.Mth.clamp;

public class DVDEvent extends AbstractTimedEvent {
    double x = 0;
    double y = 0;
    double velX = 0;
    double velY = 0;
    int size = 125;
    Random random = new Random();
    Minecraft client;

    @OnlyIn(Dist.CLIENT)
    @Override
    public void initClient() {
        super.initClient();
        velX = getRandomSpeed();
        velY = getRandomSpeed();
        client = Minecraft.getInstance();
        ForgedEntropyMod.LOGGER.info("Scale isntance: {}",client.getWindow().getGuiScale());
        if(client.getWindow().getGuiScale()>=3) {
            size=100;
        }

    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void endClient() {
        super.endClient();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void render(GuiGraphics graphics, float tickDelta) {
        renderDVDOverlay(graphics,tickDelta);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void tickClient() {
        y+=velY;
        x+=velX;
        int height = client.getWindow().getGuiScaledHeight();
        int width = client.getWindow().getGuiScaledWidth();
        if(y+size > height || y < 0) {
            y= clamp(y,0,height-size);
            velY = (velY > 0 ? -1 : 1 ) * (getRandomSpeed());
        }

        if(x+size > width || x < 0) {
            x= clamp(x,0,width-size);
            velX = (velX > 0 ? -1 : 1 ) * (getRandomSpeed());
        }
        super.tickClient();
    }

    @Override
    public String type() {
        return "DVD";
    }

    @Override
    public short getDuration() {
        return (short)(Config.baseEventDuration*0.75);
    }

    @OnlyIn(Dist.CLIENT)
    private void renderDVDOverlay(GuiGraphics graphics, float tickDelta) {
        if(client==null)
            return;
        int height = client.getWindow().getGuiScaledHeight();
        int width = client.getWindow().getGuiScaledWidth();
        int topSize = Mth.floor(y);
        int leftSize = Mth.floor(x);
        int bottomSize = Mth.floor(y+size);
        int rightSize = Mth.floor(x+size);
        graphics.fill(0,0,width,topSize, FastColor.ARGB32.color(255,0,0,0));
        graphics.fill(0,0,leftSize,height, FastColor.ARGB32.color(255,0,0,0));
        graphics.fill(0,height,width,bottomSize, FastColor.ARGB32.color(255,0,0,0));
        graphics.fill(width,0,rightSize,height, FastColor.ARGB32.color(255,0,0,0));

    }

    private double getRandomSpeed() {
        return random.nextDouble() * 8 + 2d;
    }
}
