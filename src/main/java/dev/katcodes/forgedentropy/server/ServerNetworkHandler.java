package dev.katcodes.forgedentropy.server;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.events.ChaosEvent;
import dev.katcodes.forgedentropy.events.ChaosEventRegistry;
import dev.katcodes.forgedentropy.networking.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerNegotiationEvent;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

import java.util.ArrayList;
import java.util.List;

public class ServerNetworkHandler {
    private static ServerNetworkHandler networkHandler;

    public static ServerNetworkHandler Get() {
        if(networkHandler==null)
            networkHandler = new ServerNetworkHandler();
        return networkHandler;
    }

    private ServerNetworkHandler() {

    }

    public void end_event(final EndEventPacket packet, final PlayPayloadContext context) {

    }

    public void join_confirm(final JoinConfirmPacket packet,final PlayPayloadContext context){

    }

    public void join_sync(final JoinSyncPacket packet, final PlayPayloadContext context) {

    }
    public void tick(final TickPacket packet, final PlayPayloadContext context) {

    }

    public void remove_first(final RemoveFirstPacket packet, PlayPayloadContext context) {

    }
    public void remove_ended(final RemoveEndedPacket packet, PlayPayloadContext context) {

    }
    public void add_event(final AddEventPacket packet, PlayPayloadContext context) {

    }

    public void update_poll(final PollStatePacket packet, PlayPayloadContext context) {

    }
    public void new_poll(final NewPollPacket packet, PlayPayloadContext context) {

    }

    @SubscribeEvent
    public void disconnect(PlayerEvent.PlayerLoggedOutEvent event) {

        if(ForgedEntropyMod.eventHandler==null)
            return;
        ForgedEntropyMod.eventHandler.endChaosPlayer(event.getEntity());
        if(ServerLifecycleHooks.getCurrentServer().getPlayerCount()<=1) {
            ForgedEntropyMod.eventHandler.endChaos();
            ForgedEntropyMod.eventHandler=null;

        }

    }

    public void join_handshake(JoinHandshakePacket packet, final PlayPayloadContext context) {
        JoinConfirmPacket joinConfirmPacket =new JoinConfirmPacket((short) Config.timerDuration, (short) Config.baseEventDuration,Config.integrations);
        context.replyHandler().send(joinConfirmPacket);
        if(ForgedEntropyMod.eventHandler==null) {
            ForgedEntropyMod.eventHandler=new EventHandler();
            ForgedEntropyMod.eventHandler.init(ServerLifecycleHooks.getCurrentServer());
        }
        List<JoinSyncPacket.JoinSyncEvent> events = new ArrayList<>(ForgedEntropyMod.eventHandler.currentEvents.size());
        for(ChaosEvent event:ForgedEntropyMod.eventHandler.currentEvents) {
            events.add(new JoinSyncPacket.JoinSyncEvent(ChaosEventRegistry.getEventId(event),event.hasEnded(),event.getTickCount()));
        }
        JoinSyncPacket joinSyncPacket=new JoinSyncPacket(events);
        context.replyHandler().send(joinSyncPacket);
    }
}
