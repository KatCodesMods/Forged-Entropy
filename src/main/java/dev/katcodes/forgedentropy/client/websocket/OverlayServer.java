package dev.katcodes.forgedentropy.client.websocket;

import dev.katcodes.forgedentropy.ForgedEntropyMod;
import net.minecraft.client.resources.language.I18n;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OverlayServer {
    public OverlayWebsocket overlayWebsocket;
    private ExecutorService botExecutor;

    public OverlayServer() {
        this.start();
    }

    private void start() {
        this.overlayWebsocket=new OverlayWebsocket(new InetSocketAddress("localhost",9091));
        botExecutor = Executors.newCachedThreadPool();
        botExecutor.execute(()->{
            try {
                this.overlayWebsocket.start();
            } catch(Exception e) {
                ForgedEntropyMod.LOGGER.error("Exception while starting overlay server, ",e);
            }
        });
    }

    public void stop() {
        try {
            this.overlayWebsocket.stop();
        } catch (Exception e) {
            ForgedEntropyMod.LOGGER.error("Exception while stopping overlay server, ",e);
        }
        botExecutor.shutdown();
    }

    public void onNewVote(int voteID, List<String> events) {
        int altOffset = voteID % 2 == 0 ? 5 : 1;
        //if(EntropyClient.getInstance().integrationsSettings.integrationType==2)
        //            altOffset=1;
        List<OverlayVoteOption> options=new ArrayList<>();
        for(int i=0;i<events.size();i++)
            options.add(new OverlayVoteOption(I18n.get(events.get(i)),new String[] {Integer.toString(i+altOffset)},0));
        this.overlayWebsocket.NewVoting(options);

    }

    public void updateVoting(int voteID, List<String> events, int[] votes) {
        //boolean showVotes = EntropyClient.getInstance().integrationsSettings.showCurrentPercentage;
        boolean showVotes = true;
        int altOffset = voteID % 2 == 0 ? 5 : 1;
        List<OverlayVoteOption> options=new ArrayList<>();
        for(int i=0;i<events.size();i++)
            options.add(new OverlayVoteOption(I18n.get(events.get(i)),new String[] {Integer.toString(i+altOffset)},showVotes? votes[i] : 0));
        this.overlayWebsocket.UpdateVoting(options);

    }

    public void onVoteEnd()
    {
        this.overlayWebsocket.EndVoting();
    }
}
