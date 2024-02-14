package dev.katcodes.forgedentropy.networking;

import dev.katcodes.forgedentropy.NetworkingIdentifiers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class NewPollPacket implements CustomPacketPayload {

    public int voteID;
    public List<String> events;


    public NewPollPacket(int voteID, List<String> events) {
        this.voteID=voteID;
        this.events=events;
    }
    public NewPollPacket(FriendlyByteBuf friendlyByteBuf) {

        this.voteID=friendlyByteBuf.readInt();
        int size=friendlyByteBuf.readInt();
        events=new ArrayList<>(size);
        for(int i=0;i<size;i++)
            events.add(friendlyByteBuf.readUtf());

    }
    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeInt(voteID);
        friendlyByteBuf.writeInt(events.size());
        for(String event: events)
            friendlyByteBuf.writeUtf(event);
    }

    @Override
    public ResourceLocation id() {
        return NetworkingIdentifiers.NEW_POLL;
    }
}
