package dev.katcodes.forgedentropy.networking;

import dev.katcodes.forgedentropy.NetworkingIdentifiers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public class PollStatePacket implements CustomPacketPayload {

    public int voteId;
    public int[] totalVotes;
    public int totalVotesCount;

    public PollStatePacket(int voteId, int[] totalVotes, int totalVotesCount) {
        this.voteId=voteId;
        this.totalVotes=totalVotes;
        this.totalVotesCount=totalVotesCount;
    }
    public PollStatePacket(FriendlyByteBuf byteBuf) {
        this.voteId=byteBuf.readInt();
        this.totalVotes=byteBuf.readVarIntArray();
        this.totalVotesCount= byteBuf.readInt();
    }
    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeInt(voteId);
        friendlyByteBuf.writeVarIntArray(totalVotes);
        friendlyByteBuf.writeInt(totalVotesCount);
    }

    @Override
    public ResourceLocation id() {
        return NetworkingIdentifiers.POLL_STATUS;
    }
}
