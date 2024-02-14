package dev.katcodes.forgedentropy.server;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.VotingMode;
import dev.katcodes.forgedentropy.events.ChaosEvent;
import dev.katcodes.forgedentropy.events.ChaosEventRegistry;
import dev.katcodes.forgedentropy.networking.NewPollPacket;
import dev.katcodes.forgedentropy.networking.PollStatePacket;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VotingServer {
    private final int size=4;
    public List<ChaosEvent> events;
    int[] totalVotes;
    int voteId=-1;
    int totalVoteCount=0;
    boolean enabled=false;

    public void enable() {
        this.enabled = true;
        newPoll();
    }

    public void disable() {
        this.enabled=false;
    }

    public void receiveVotes(int voteId, int[] votes) {
        if(this.voteId!=voteId) {
            ForgedEntropyMod.LOGGER.warn("Vote skipped, VoteID does not match ({} != {})",this.voteId,voteId);
            return;
        }

        if(votes.length==this.totalVotes.length) {
            for(int i=0;i<votes.length;i++){
                this.totalVotes[i]+=votes[i];
                this.totalVoteCount+=votes[i];
            }
        }
        this.sendPollStatusToPlayers();
    }

    public int getWinner() {
        if(Config.votingMode== VotingMode.Majority) {
            return getWinnerByMajority();
        }
        else {
            return getWinnerByPercentage();
        }
    }
    public int getWinnerByMajority() {
        int bigger = 0 , biggerIndex = -1;
        for(int i=0;i<size;i++) {
            int current = totalVotes[i];
            if(current > bigger) {
                bigger = current;
                biggerIndex = i;
            }
        }
        if(bigger == 0)
            return -1;
        return biggerIndex;
    }

    public int getWinnerByPercentage() {
        Random random = new Random();
        if(totalVoteCount <=0)
            return -1;
        int vote = random.nextInt(1,totalVoteCount+1);
        int voteRange = 0;
        for(int i=0;i<size;i++) {
            voteRange+=totalVotes[i];
            if(vote<=voteRange) {
                return i;
            }
        }
        return -1;
    }

    public void newPoll() {
        this.totalVotes=new int[size];
        this.events=getRandomEvents(size-1);
        this.totalVoteCount=0;
        this.voteId++;
        this.sendNewPoll();
    }

    private List<ChaosEvent> getRandomEvents(int size) {
        List<ChaosEvent> newEvents=new ArrayList<>();
        List<ChaosEvent> currentEvents = ForgedEntropyMod.eventHandler.currentEvents;
        for(int i=0;i<size;i++) {
            ChaosEvent newEvent = ChaosEventRegistry.getRandomDifferentEvent(currentEvents);
            if(newEvent!=null)
                newEvents.add(newEvent);
        }
        return newEvents;
    }

    public void sendNewPoll() {
        PacketDistributor.ALL.noArg().send(getNewPollPacket());
    }
    public void sendNewPollToPlayer(ServerPlayer serverPlayer) {
        PacketDistributor.PLAYER.with(serverPlayer).send(getNewPollPacket());
    }

    public NewPollPacket getNewPollPacket() {
        NewPollPacket newPollPacket;
        if(events.isEmpty())
        {
            newPollPacket=new NewPollPacket(voteId,List.of("No Event"));
        } else {
            newPollPacket = new NewPollPacket(voteId,events.stream().map(ChaosEventRegistry::getTranslationKey).toList());
        }
        return newPollPacket;
    }

    public void sendPollStatusToPlayers() {
        PollStatePacket pollStatePacket=new PollStatePacket(voteId,totalVotes,totalVoteCount);
        PacketDistributor.ALL.noArg().send(pollStatePacket);
    }
}
