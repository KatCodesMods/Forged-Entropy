package dev.katcodes.forgedentropy.networking;

import dev.katcodes.forgedentropy.NetworkingIdentifiers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public class EndEventPacket implements CustomPacketPayload {

    public int getEventNum()
    {
        return eventNum;
    }
    protected int eventNum=0;

    public EndEventPacket(int eventNum) {
        this.eventNum=eventNum;
    }
    public EndEventPacket(final FriendlyByteBuf byteBuf) {
        eventNum=byteBuf.readInt();
    }
    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeInt(eventNum);
    }

    @Override
    public ResourceLocation id() {
        return NetworkingIdentifiers.END_EVENT;
    }
}
