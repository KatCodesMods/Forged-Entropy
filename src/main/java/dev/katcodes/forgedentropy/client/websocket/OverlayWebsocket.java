package dev.katcodes.forgedentropy.client.websocket;

import com.google.gson.Gson;
import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.VotingMode;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class OverlayWebsocket extends WebSocketServer {
    public Gson gson;
    public OverlayWebsocket(InetSocketAddress address) {
        super(address);
        this.setReuseAddr(true);
        gson=new Gson();
    }

    public void EndVoting() {
        Request("END",new ArrayList<>());
    }
    public void NewVoting(List<OverlayVoteOption> options) {
        Request("CREATE", options);
    }
    public void UpdateVoting(List<OverlayVoteOption> options) {
        Request("UPDATE", options);
    }

    private void Request(String request, List<OverlayVoteOption> voteOptions) {
        int totalVotes = 0;
        for(var option: voteOptions) {
            totalVotes+=option.value();
        }
        OverlayVoteOption[] options=new OverlayVoteOption[voteOptions.size()];
        voteOptions.toArray(options);
        OverlayMessage msg =new OverlayMessage(request,totalVotes, Config.votingMode== VotingMode.Majority?"MAJORITY":"PERCENTAGE",options);
        String broadcastString = gson.toJson(msg);
        this.broadcast(broadcastString);
    }
    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {

    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {

    }

    @Override
    public void onMessage(WebSocket conn, String message) {

    }

    @Override
    public void onError(WebSocket conn, Exception ex) {

    }

    @Override
    public void onStart() {

    }


}
