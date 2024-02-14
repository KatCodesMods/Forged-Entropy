package dev.katcodes.forgedentropy.events;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.CurrentState;
import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.events.db.HideEventsEvent;
import dev.katcodes.forgedentropy.networking.EndEventPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FastColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

import java.util.List;

public abstract class AbstractTimedEvent implements ChaosEvent {
    protected short tickCount = 0;
    protected boolean hasEnded = false;

    @Override
    public void renderQueueItem(GuiGraphics graphics, float tickDelta, int x, int y) {
        if(CurrentState.Get().doNotShowEvents && !(this instanceof HideEventsEvent))
            return;
        if(CurrentState.Get().doNotShowEvents)
            y = 20;
        Component name = Component.translatable(ChaosEventRegistry.getTranslationKey(this));
        if(isDisabledByAccessibilityMode() && Config.accessibilityMode)
            name.getStyle().withStrikethrough(true);
        int size = Minecraft.getInstance().font.width(name);
        graphics.drawCenteredString(Minecraft.getInstance().font,name,Minecraft.getInstance().getWindow().getGuiScaledWidth()-size-40,y, FastColor.ARGB32.color(255,255, 255, 255));
        if(!this.hasEnded()) {
            graphics.fill(Minecraft.getInstance().getWindow().getGuiScaledWidth()-35,y+1, Minecraft.getInstance().getWindow().getGuiScaledWidth()-5,y+8,FastColor.ARGB32.color(150,70, 70, 70));
            graphics.fill(Minecraft.getInstance().getWindow().getGuiScaledWidth()-35,y+1, (int) (Minecraft.getInstance().getWindow().getGuiScaledWidth()-35 + Math.floor(30 * (getTickCount() / (double) getDuration()))),y+8,FastColor.ARGB32.color(200,255, 255, 255));
        }
    }

    @Override
    public void tick() {
        tickCount++;
        if(tickCount >= this.getDuration()) {
            List<ChaosEvent> currentEvents = ForgedEntropyMod.eventHandler.currentEvents;
            for(int i=0;i<currentEvents.size();i++) {
                if(currentEvents.get(i).equals(this)) {
                    EndEventPacket pack=new EndEventPacket(i);
                    PacketDistributor.ALL.noArg().send(pack);
                }
            }
            this.end();;
        }
    }

    @Override
    public void end() {
        this.hasEnded = true;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void tickClient() {
        tickCount++;
        if(tickCount > this.getDuration())
            this.endClient();
    }

    @Override
    public short getTickCount() {
        return tickCount;
    }

    @Override
    public void setTickCount(short tickCount) {
        this.tickCount = tickCount;
    }

    @Override
    public boolean hasEnded() {
        return hasEnded;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void endClient() {
        hasEnded=true;
    }

    @Override
    public void setEnded(boolean ended) {
        this.hasEnded=ended;
    }
}
