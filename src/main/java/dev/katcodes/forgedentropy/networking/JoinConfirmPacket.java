package dev.katcodes.forgedentropy.networking;

import dev.katcodes.forgedentropy.NetworkingIdentifiers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public class JoinConfirmPacket implements CustomPacketPayload {

    short timerDuration;
    short baseEventDuration;
    boolean serverIntegrations;
    public JoinConfirmPacket(short timerDuration, short baseEventDuration, boolean serverIntegrations) {
        this.timerDuration=timerDuration;
        this.baseEventDuration=baseEventDuration;
        this.serverIntegrations=serverIntegrations;
    }
    public JoinConfirmPacket(FriendlyByteBuf friendlyByteBuf) {
        this.timerDuration=friendlyByteBuf.readShort();
        this.baseEventDuration=friendlyByteBuf.readShort();
        this.serverIntegrations=friendlyByteBuf.readBoolean();
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeShort(timerDuration);
        friendlyByteBuf.writeShort(baseEventDuration);
        friendlyByteBuf.writeBoolean(serverIntegrations);
    }

    @Override
    public ResourceLocation id() {
        return NetworkingIdentifiers.JOIN_CONFIRM;
    }

    public short getTimerDuration() {
        return timerDuration;
    }

    public short getBaseEventDuration() {
        return baseEventDuration;
    }

    public boolean isServerIntegrations() {
        return serverIntegrations;
    }
}
