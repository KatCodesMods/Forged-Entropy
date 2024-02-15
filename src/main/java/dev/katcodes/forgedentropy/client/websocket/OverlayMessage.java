package dev.katcodes.forgedentropy.client.websocket;

public record OverlayMessage(String request, int totalVotes, String votingMode, OverlayVoteOption[] voteOptions){
}

