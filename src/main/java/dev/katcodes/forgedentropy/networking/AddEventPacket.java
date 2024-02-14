package dev.katcodes.forgedentropy.networking;

import dev.katcodes.forgedentropy.NetworkingIdentifiers;
import dev.katcodes.forgedentropy.events.ChaosEvent;
import dev.katcodes.forgedentropy.events.ChaosEventRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public class AddEventPacket implements CustomPacketPayload {

    public ChaosEvent event;
    public AddEventPacket(FriendlyByteBuf friendlyByteBuf) {
        event = ChaosEventRegistry.get(friendlyByteBuf.readUtf());
        if(event !=null)
            event.readExtraData(friendlyByteBuf);
    }

    public AddEventPacket(ChaosEvent event) {
        this.event = event;
    }
    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeUtf(ChaosEventRegistry.getEventId(event));
        event.writeExtraData(friendlyByteBuf);
    }

    @Override
    public ResourceLocation id() {
        return NetworkingIdentifiers.ADD_EVENT;
    }
}
