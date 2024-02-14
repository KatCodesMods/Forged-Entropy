package dev.katcodes.forgedentropy.networking;

import dev.katcodes.forgedentropy.NetworkingIdentifiers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public class TickPacket implements CustomPacketPayload {
    public short eventCountDown;
    public TickPacket(short eventCountDown) {
        this.eventCountDown=eventCountDown;
    }

    public TickPacket(FriendlyByteBuf friendlyByteBuf) {
        this.eventCountDown=friendlyByteBuf.readShort();
    }
    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeShort(eventCountDown);
    }

    @Override
    public ResourceLocation id() {
        return NetworkingIdentifiers.TICK;
    }
}
