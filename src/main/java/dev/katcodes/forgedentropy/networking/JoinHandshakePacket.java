package dev.katcodes.forgedentropy.networking;

import dev.katcodes.forgedentropy.NetworkingIdentifiers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public class JoinHandshakePacket implements CustomPacketPayload {

    public JoinHandshakePacket() {}
    public JoinHandshakePacket(FriendlyByteBuf friendlyByteBuf) {

    }
    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {

    }

    @Override
    public ResourceLocation id() {
        return NetworkingIdentifiers.JOIN_HANDSHAKE;
    }
}
