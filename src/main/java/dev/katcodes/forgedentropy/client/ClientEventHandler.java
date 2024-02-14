package dev.katcodes.forgedentropy.client;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.CurrentState;
import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.UIStyle;
import dev.katcodes.forgedentropy.client.UIStyles.GTAVUIRenderer;
import dev.katcodes.forgedentropy.client.UIStyles.UIRenderer;
import dev.katcodes.forgedentropy.events.ChaosEvent;
import dev.katcodes.forgedentropy.events.ChaosEventRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

import java.util.ArrayList;
import java.util.List;

public class ClientEventHandler {
    public List<ChaosEvent> currentEvents = new ArrayList<>();
    short timerDuration;
    UIRenderer renderer = null;
    final short timerDurationFinal;
    public short eventCountDown;
    boolean serverIntegrations;

    public ClientEventHandler(short timerDuration, short baseEventDuration, boolean serverIntegrations) {
        this.timerDuration=timerDuration;
        this.timerDurationFinal=timerDuration;
        this.eventCountDown=timerDuration;
        this.serverIntegrations = serverIntegrations;
        Config.baseEventDuration = baseEventDuration;

        //todo: Add integrations here

        if(Config.uiStyle == UIStyle.GTAV) {
            //todo: once voting client, pass it here
            renderer = new GTAVUIRenderer();
        }
    }

    public void tick(short eventCountDown) {
        this.timerDuration = (short) (timerDurationFinal / CurrentState.Get().timeMultiplier);
        this.eventCountDown = eventCountDown;
        if (eventCountDown % 10 == 0 && false) {//&& votingClient == null) {
            // todo: send votes
        }
        if(!Minecraft.getInstance().player.isSpectator()) {
            for(ChaosEvent event: currentEvents) {
                if(!event.hasEnded())
                    event.tickClient();
            }
        }
    }

    public void render(GuiGraphics drawContext, float tickDelta) {
        currentEvents.forEach(event ->{
                if(!event.hasEnded() && !Minecraft.getInstance().player.isSpectator())
                    event.render(drawContext,tickDelta);
        });

        //todo: check if debug screen
        //if(Minecraft.getInstance().getDebugOverlay())
        //  return;
        double time = timerDuration - eventCountDown;
        int width = Minecraft.getInstance().getWindow().getGuiScaledWidth();
        renderer.renderTimer(drawContext,width,time,timerDuration);

        for(int i=0;i< currentEvents.size();i++) {
            currentEvents.get(i).renderQueueItem(drawContext,tickDelta,width-200,20+(i*13));
        }

        // todo: Render poll if needed
    }
    public void remove(int i) {
        currentEvents.remove(i);
    }

    public void updatePollStatus(int voteId, int[] totalVotes, int totalVotesCount) {

    }

    public void newPoll(int voteID, List<String> events) {

    }

    public void endChaos() {
        ForgedEntropyMod.LOGGER.info("Ending events...");
        currentEvents.forEach(eventPair -> {
            if(!eventPair.hasEnded())
                eventPair.endClient();
        });

        //todo: End voting

        //todo: Reset settings
    }

    public void AddEvent(ChaosEvent event) {
        if(!Minecraft.getInstance().player.isSpectator() && !(event.isDisabledByAccessibilityMode() && Config.accessibilityMode))
            event.initClient();
        currentEvents.add(event);
    }
}
