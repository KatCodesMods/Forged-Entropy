package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.events.AbstractTimedEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;

public class ConstantInteractingEvent extends AbstractTimedEvent {
    private boolean hasScreenOpen = false;
    private boolean hadScreenOpenLastTick = false;
    private int afterScreenClosedCooldown = 0;

    @Override
    public void tickClient() {
        super.tickClient();
        if(this.afterScreenClosedCooldown>0) {
            this.afterScreenClosedCooldown--;
            return;
        }

        Minecraft client = Minecraft.getInstance();
        this.hadScreenOpenLastTick = this.hasScreenOpen;
        this.hasScreenOpen = client.screen != null && !(client.screen instanceof InventoryScreen);

        if(this.hadScreenOpenLastTick && !this.hasScreenOpen) {
            this.afterScreenClosedCooldown=10;
            client.options.keyUse.setDown(false);
        } else
            client.options.keyUse.setDown(true);
    }

    @Override
    public void endClient() {
        super.endClient();
        Minecraft.getInstance().options.keyUse.setDown(false);
    }

    @Override
    public void render(GuiGraphics graphics, float tickDelta) {

    }

    @Override
    public String type() {
        return "use";
    }

    @Override
    public short getDuration() {
        return (short) Config.baseEventDuration;
    }
}
