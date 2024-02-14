package dev.katcodes.forgedentropy.client;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.events.ChaosEvent;
import dev.katcodes.forgedentropy.events.ChaosEventRegistry;
import dev.katcodes.forgedentropy.networking.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public class ClientNetworkHandler {
    private static ClientNetworkHandler networkHandler;

    public static ClientNetworkHandler Get() {
        if(networkHandler==null)
            networkHandler=new ClientNetworkHandler();
        return networkHandler;
    }
    private ClientNetworkHandler() {

    }
    public void end_event(final EndEventPacket packet, final PlayPayloadContext context) {
        if(ForgedEntropyClient.clientEventHandler==null)
            return;
        ChaosEvent event = ForgedEntropyClient.clientEventHandler.currentEvents.get(packet.getEventNum());
        if(event!=null)
            event.endClient();

    }

    public void join_confirm(final JoinConfirmPacket packet, final PlayPayloadContext context){
        ForgedEntropyClient.clientEventHandler=new ClientEventHandler(packet.getTimerDuration(),packet.getBaseEventDuration(),packet.isServerIntegrations());
    }

    public void join_sync(final JoinSyncPacket packet, final PlayPayloadContext context) {
        if(ForgedEntropyClient.clientEventHandler==null)
            return;
        if(ForgedEntropyClient.clientEventHandler.currentEvents.size() == packet.eventList.size())
            return;
        for(JoinSyncPacket.JoinSyncEvent syncEvent: packet.eventList) {
            ChaosEvent event = ChaosEventRegistry.get(syncEvent.eventName());
            if(event == null)
                continue;
            event.setEnded(syncEvent.eventEnded());
            event.setTickCount(syncEvent.tickCount());
            if(syncEvent.tickCount() > 0 && ! syncEvent.eventEnded() && !(event.isDisabledByAccessibilityMode() && Config.accessibilityMode))
                event.initClient();
            ForgedEntropyClient.clientEventHandler.currentEvents.add(event);
        }
    }
    public void tick(final TickPacket packet, final PlayPayloadContext context) {
        if(ForgedEntropyClient.clientEventHandler==null)
            return;

        ForgedEntropyClient.clientEventHandler.tick(packet.eventCountDown);

    }

    public void remove_first(final RemoveFirstPacket packet, PlayPayloadContext context) {
        if(ForgedEntropyClient.clientEventHandler==null)
            return;
        if(!ForgedEntropyClient.clientEventHandler.currentEvents.isEmpty())
            ForgedEntropyClient.clientEventHandler.remove(0);
    }

    public void remove_ended(final RemoveEndedPacket packet, PlayPayloadContext context) {
        if(ForgedEntropyClient.clientEventHandler==null)
            return;
        if(!ForgedEntropyClient.clientEventHandler.currentEvents.isEmpty())
            ForgedEntropyClient.clientEventHandler.currentEvents.removeIf(ChaosEvent::hasEnded);

    }

    public void add_event(final AddEventPacket packet, PlayPayloadContext context) {
        if(ForgedEntropyClient.clientEventHandler == null)
            return;
        ForgedEntropyClient.clientEventHandler.AddEvent(packet.event);
    }

    public void update_poll(final PollStatePacket packet, PlayPayloadContext context) {
        if(ForgedEntropyClient.clientEventHandler==null)
            return;
        ForgedEntropyClient.clientEventHandler.updatePollStatus(packet.voteId,packet.totalVotes,packet.totalVotesCount);
    }

    public void new_poll(final NewPollPacket packet, PlayPayloadContext context) {
        if(ForgedEntropyClient.clientEventHandler==null)
            return;
        ForgedEntropyClient.clientEventHandler.newPoll(packet.voteID,packet.events);
    }

    @SubscribeEvent
    public void disconnect(ClientPlayerNetworkEvent.LoggingOut event) {
        if(ForgedEntropyClient.clientEventHandler==null)
            return;
        ForgedEntropyClient.clientEventHandler.endChaos();
        ForgedEntropyClient.clientEventHandler=null;
    }
    @SubscribeEvent
    public void connect(ClientPlayerNetworkEvent.LoggingIn event) {
        PacketDistributor.SERVER.noArg().send(new JoinHandshakePacket());
    }

    public void join_handshake(JoinHandshakePacket packet, final PlayPayloadContext context) {

    }

}
