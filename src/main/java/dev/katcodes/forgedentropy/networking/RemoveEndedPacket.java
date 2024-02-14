package dev.katcodes.forgedentropy.networking;

import dev.katcodes.forgedentropy.NetworkingIdentifiers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public class RemoveEndedPacket implements CustomPacketPayload {

    public RemoveEndedPacket(FriendlyByteBuf friendlyByteBuf) {

    }
    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {

    }

    @Override
    public ResourceLocation id() {
        return NetworkingIdentifiers.REMOVE_ENDED;
    }
}
