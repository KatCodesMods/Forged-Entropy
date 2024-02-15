package dev.katcodes.forgedentropy.client;
import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.client.integrations.Integration;
import dev.katcodes.forgedentropy.client.integrations.IntegrationTypes;
import dev.katcodes.forgedentropy.client.websocket.OverlayServer;
import dev.katcodes.forgedentropy.networking.PollStatePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FastColor;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class VotingClient {
    private final int size = 4;
    List<String> events;
    int[] votes;
    int[] totalVotes;
    int voteID = -1;
    int pollWidth = 195;
    int totalVotesCount = 0;
    boolean enabled = false;
    Integration integration;

    OverlayServer overlayServer;
    boolean firstVote=true;

    // key is user identifier and value is last vote index
    private HashMap<String, Integer> voteMap = new HashMap<>();


    public void enable() {
        ForgedEntropyMod.LOGGER.info("Enabling the Voting Client");
        enabled = true;
        this.overlayServer = new OverlayServer();
    }


    public void disable() {
        enabled = false;
        integration.stop();
        overlayServer.stop();
    }

    public void removeVote(int index, String userId) {
        if (index >= 0 && index < 4) {
            if(voteMap.containsKey(userId) && voteMap.get(userId) == index) {
                votes[voteMap.get(userId)] -=1;
                totalVotes[voteMap.get(userId)] -=1;
                totalVotesCount -=  1;
                voteMap.remove(userId);
            }
        }
    }

    public void processVote(int index, String userId) {
        if (index >= 0 && index < 4) {

            if(voteMap.containsKey(userId)) {
                votes[voteMap.get(userId)] -= 1;
                totalVotes[voteMap.get(userId)] -= 1;
                totalVotesCount -= 1;
            }

            if(userId != null) {
                voteMap.put(userId, index);
            }

            votes[index] += 1;
            totalVotes[index] += 1;
            totalVotesCount += 1;
        }
    }
    public void processVote(String string, String userId) {
        if (enabled && string.trim().length() == 1) {
            int voteIndex = Integer.parseInt(string.trim()) + (voteID % 2 == 0 ? -4 : 0);
            if (voteIndex > 0 && voteIndex < 5) {
                processVote(voteIndex - 1, userId);
            }
        }
    }
    public void newPoll(int voteID, int size, List<String> events) {
        ForgedEntropyMod.LOGGER.info("New Poll: {}}",voteID);
        ArrayList<String> pollEvents=new ArrayList<>();
        pollEvents.addAll(events);
        voteMap.clear();
        if(firstVote){
            firstVote=false;
        } else {
            this.overlayServer.onVoteEnd();
        }
        if (this.size == size) {
            this.voteID = voteID;
            this.events = pollEvents;
            this.events.add("entropy.voting.randomEvent");
            totalVotesCount = 0;
            votes = new int[4];
            totalVotes = new int[4];
            sendPoll(voteID,events);
            int max = 200;
            for (String key : events){
                int width = Minecraft.getInstance().font.width(Component.literal((5) + ": ").append(Component.translatable(key)));
                if(width > max)
                    max = width;
            }
            pollWidth = max;
        }
    }

    public void render(GuiGraphics graphics) {
        if(Config.IntegrationSettings.showUpcomingEvents) {
            graphics.drawString(Minecraft.getInstance().font, Component.translatable("entropy.voting.total", this.totalVotesCount), 10, 20, FastColor.ARGB32.color(255, 255, 255, 255));

            for (int i = 0; i < 4; i++) {
                renderPollElement(graphics, i);
            }
        }
    }
    public void renderPollElement(GuiGraphics graphics, int i) {
        if(this.events==null || this.events.size()<=i)
            return;
        double ratio = this.totalVotesCount > 0 ? (double) this.totalVotes[i] / this.totalVotesCount : 0;
        int altOffset = (this.voteID % 2)== 0 && Config.IntegrationSettings.integrationType!= IntegrationTypes.Discord ? 4:0;
        graphics.fill(10,31+(i*18),pollWidth+45+10,35 + (i*18)+10,FastColor.ARGB32.color(150,0,0,0));
        if(Config.IntegrationSettings.showCurrentPercentage){
            graphics.fill(10,31+(i*18), (int) (10+Math.floor((pollWidth+45)*ratio)),(35+(i*18)+10),this.getColor(150));

        }
        graphics.drawString(Minecraft.getInstance().font, Component.literal((1+i+altOffset)+": ").append(Component.translatable(this.events.get(i))),15,34+(i*18),FastColor.ARGB32.color(255,255,255,255));

        if(Config.IntegrationSettings.showCurrentPercentage) {
                Component percentage = Component.literal(Math.floor(ratio*100)+" %");
                graphics.drawString(Minecraft.getInstance().font, percentage,pollWidth+10+42-Minecraft.getInstance().font.width(percentage),34+(i*18),FastColor.ARGB32.color(255,255,255,255));


        }
    }


    public void sendPoll(int voteID, List<String> events) {
        if(Config.IntegrationSettings.sendChatMessages)
            integration.sendPoll(voteID,events);
        overlayServer.onNewVote(voteID,events);
    }
    public void updatePollStatus(int voteID, int[] totalVotes, int totalVoteCount) {
        if (this.voteID == voteID) {
            this.totalVotes = totalVotes;
            this.totalVotesCount = totalVoteCount;
        }
    }

    public void setIntegrations(Integration integration) {
        this.integration = integration;
    }


    public void sendMessage(String message) {
        if(Config.IntegrationSettings.sendChatMessages)
            integration.sendMessage(message);
    }

    public void sendVotes() {
        if(voteID==-1)
            return;

        PollStatePacket pollStatePacket=new PollStatePacket(voteID,votes,totalVotesCount);
        votes = new int[4];
        PacketDistributor.SERVER.noArg().send(pollStatePacket);

        overlayServer.updateVoting(voteID,events,totalVotes);

    }
    public int getColor(int alpha) {
        return integration.getColor(alpha);
    }
}
