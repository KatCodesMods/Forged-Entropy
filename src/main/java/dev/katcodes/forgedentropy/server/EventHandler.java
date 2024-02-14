package dev.katcodes.forgedentropy.server;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.CurrentState;
import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.events.ChaosEvent;
import dev.katcodes.forgedentropy.events.ChaosEventRegistry;
import dev.katcodes.forgedentropy.networking.AddEventPacket;
import dev.katcodes.forgedentropy.networking.RemoveFirstPacket;
import dev.katcodes.forgedentropy.networking.TickPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;

public class EventHandler {
    public List<ChaosEvent> currentEvents = new ArrayList<>();
    public VotingServer voting;
    private boolean started = false;
    private short eventCountDown;
    public MinecraftServer server;

    public void init(MinecraftServer currentServer) {
        resetTimer();
        if(Config.integrations) {
            voting = new VotingServer();
            voting.enable();
        }
        this.started = true;
        this.server = currentServer;
    }

    public void tick(boolean noNewEvents) {
        if(!this.started)
            return;
        if(eventCountDown>Config.timerDuration/ CurrentState.Get().timeMultiplier)
            resetTimer();

        if(eventCountDown==0) {
            if(currentEvents.size()>3) {
                if(currentEvents.get(0).hasEnded()) {
                    RemoveFirstPacket removeFirstPacket=new RemoveFirstPacket();
                    PacketDistributor.ALL.noArg().send(removeFirstPacket);
                    currentEvents.remove(0);
                }
            }
            if(!noNewEvents) {
                ChaosEvent event;
                if(Config.integrations) {
                    if(voting.events.isEmpty()) {
                        ForgedEntropyMod.LOGGER.info("[Chat Integrations] No random event available");
                        event=null;
                    } else {
                        int winner = voting.getWinner();
                        if(winner == -1 || winner == 3)
                            event=getRandomEvent(currentEvents);
                        else
                            event=voting.events.get(winner);
                        ForgedEntropyMod.LOGGER.info("[Chat Integrations] Winner event: " + ChaosEventRegistry.getTranslationKey(event));
                    }
                } else {
                    event = getRandomEvent(currentEvents);
                }

                runEvent(event);
                if(Config.integrations)
                    voting.newPoll();

                resetTimer();
                if(event!=null)
                    ForgedEntropyMod.LOGGER.info("New Event: " + ChaosEventRegistry.getTranslationKey(event) + " total duration: " + event.getDuration());
                else
                    ForgedEntropyMod.LOGGER.info("No new event found");
            }
        }
        for(ChaosEvent event: currentEvents){
            if(!event.hasEnded())
                event.tick();
        }
        TickPacket tickPacket = new TickPacket(eventCountDown);
        PacketDistributor.ALL.noArg().send(tickPacket);

        eventCountDown--;
    }

    public boolean runEvent(ChaosEvent event) {
        if(event!=null) {
            event.init();
            currentEvents.add(event);
            sendEventToPlayers(event);
            return true;
        }
        return false;
    }

    private void sendEventToPlayers(ChaosEvent event) {
        AddEventPacket addEventPacket=new AddEventPacket(event);
        PacketDistributor.ALL.noArg().send(addEventPacket);
    }

    public ChaosEvent getRandomEvent(List<ChaosEvent> eventArray) {
        return ChaosEventRegistry.getRandomDifferentEvent(eventArray);
    }
    public void endChaos() {
        if(voting!=null)
            voting.disable();

        currentEvents.forEach(ChaosEvent::end);
    }

    public void endChaosPlayer(Player entity) {
        currentEvents.forEach(event -> {
            if(!event.hasEnded())
                event.endPlayer(entity);
        });
    }

    public void resetTimer() {
        eventCountDown = (short) (Config.timerDuration / CurrentState.Get().timeMultiplier);
    }

    public List<ServerPlayer> getActivePlayers() {
        return server.getPlayerList().getPlayers();
    }
}
