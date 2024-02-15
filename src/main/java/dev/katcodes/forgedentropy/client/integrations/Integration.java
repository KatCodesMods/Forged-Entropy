package dev.katcodes.forgedentropy.client.integrations;

import java.util.List;

public interface Integration {
    void start();

    void stop();

    void sendPoll(int voteID, List<String> events);

    void sendMessage(String message);

    int getColor(int alpha);
}
