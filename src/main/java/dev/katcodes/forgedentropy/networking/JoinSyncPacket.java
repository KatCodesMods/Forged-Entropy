package dev.katcodes.forgedentropy.networking;

import dev.katcodes.forgedentropy.NetworkingIdentifiers;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class JoinSyncPacket implements CustomPacketPayload {
    public record JoinSyncEvent(String eventName, boolean eventEnded, short tickCount){};

    public JoinSyncPacket() {
        eventList=new ArrayList<>();
    }
    public JoinSyncPacket(List<JoinSyncEvent> events) {
        this.eventList=events;
    }
    public JoinSyncPacket(FriendlyByteBuf friendlyByteBuf) {
        int size = friendlyByteBuf.readInt();
        eventList=new ArrayList<>(size);
        for(int i=0;i<size;i++) {
            JoinSyncEvent event=new JoinSyncEvent(friendlyByteBuf.readUtf(),friendlyByteBuf.readBoolean(),friendlyByteBuf.readShort());
            eventList.add(event);
        }
    }
    public List<JoinSyncEvent> eventList;
    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeInt(eventList.size());
        for (JoinSyncEvent event : eventList) {
           friendlyByteBuf.writeUtf(event.eventName);
            friendlyByteBuf.writeBoolean(event.eventEnded);
            friendlyByteBuf.writeShort(event.tickCount);
        }
    }

    @Override
    public ResourceLocation id() {
        return NetworkingIdentifiers.JOIN_SYNC;
    }
}
