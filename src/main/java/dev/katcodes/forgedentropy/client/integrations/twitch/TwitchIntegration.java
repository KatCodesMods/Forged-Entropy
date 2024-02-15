package dev.katcodes.forgedentropy.client.integrations.twitch;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.client.ForgedEntropyClient;
import dev.katcodes.forgedentropy.client.VotingClient;
import dev.katcodes.forgedentropy.client.integrations.Integration;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.util.FastColor;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.cap.EnableCapHandler;
import org.pircbotx.exception.IrcException;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.JoinEvent;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PingEvent;

import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class TwitchIntegration extends ListenerAdapter implements Integration {
    private final Configuration config;
    private PircBotX ircChatBot;
    private ExecutorService botExecutor;
    private final VotingClient votingClient;
    private long lastJoinMessage = 0;

    public TwitchIntegration(VotingClient client) {
        this.votingClient = client;
        config = new Configuration.Builder()
                .setAutoNickChange(false)
                .setOnJoinWhoEnabled(false)
                .setCapEnabled(true)
                .addCapHandler(new EnableCapHandler("twitch.tv/tags"))
                .addCapHandler(new EnableCapHandler("twitch.tv/commands"))
                .addCapHandler(new EnableCapHandler("twitch.tv/membership"))
                .setEncoding(StandardCharsets.UTF_8)
                .addServer("irc.chat.twitch.tv", 6697)
                .setSocketFactory(SSLSocketFactory.getDefault())
                .setName(Config.IntegrationSettings.TwitchSettings.channel.toLowerCase())
                .setServerPassword(Config.IntegrationSettings.TwitchSettings.authToken.startsWith("oauth:") ? Config.IntegrationSettings.TwitchSettings.authToken : "oauth:"+Config.IntegrationSettings.TwitchSettings.authToken)
                .addAutoJoinChannel("#"+Config.IntegrationSettings.TwitchSettings.channel.toLowerCase())
                .addListener(this)
                .setAutoSplitMessage(false)
                .buildConfiguration();

        this.start();
    }


    @Override
    public void start() {
        this.ircChatBot = new PircBotX(config);
        botExecutor = Executors.newCachedThreadPool();
        botExecutor.execute(() -> {
            try {
                this.ircChatBot.startBot();
            } catch (IOException e) {
                ForgedEntropyMod.LOGGER.error("IO Exception while starting bot",e);
            } catch(IrcException e) {
                ForgedEntropyMod.LOGGER.error("IRC Exception while starting bot",e);
            }
        });
    }

    @Override
    public void stop() {
        ircChatBot.stopBotReconnect();
        ircChatBot.close();
        botExecutor.shutdown();
    }

    @Override
    public void onMessage(MessageEvent event) throws Exception {
        ForgedEntropyClient.clientEventHandler.votingClient.processVote(event.getMessage(),event.getUser().getLogin());
        super.onMessage(event);
    }

    @Override
    public void onJoin(JoinEvent event) throws Exception {
        long currentTime = System.currentTimeMillis();
        if(currentTime - lastJoinMessage>30000) {
            votingClient.sendMessage("Connected to Forged Entropy Mod");
            lastJoinMessage=currentTime;
        }
    }

    @Override
    public void onPing(PingEvent event) throws Exception {
        super.onPing(event);
        ForgedEntropyClient.LOGGER.info("Received ping from twitch, answering");
        ircChatBot.sendRaw().rawLineNow(String.format("PONG: %s\r\n",event.getPingValue()));

    }


    @Override
    public void sendPoll(int voteID, List<String> events) {
        int altOffset = voteID % 2 == 0 ? 4 : 0;
        StringBuilder stringBuilder = new StringBuilder("Current poll:");
        for (int i = 0; i < events.size(); i++)
            stringBuilder.append(String.format("[ %d - %s ] ", 1 + i + altOffset, I18n.get(events.get(i))));

        ircChatBot.sendIRC().message("#" + Config.IntegrationSettings.TwitchSettings.channel.toLowerCase(), "/me [Entropy Bot] " + stringBuilder);
    }

    @Override
    public void sendMessage(String message) {
        ircChatBot.sendIRC().message("#" + Config.IntegrationSettings.TwitchSettings.channel.toLowerCase(), "/me [Entropy Bot] " + message);
    }

    @Override
    public int getColor(int alpha) {
        return FastColor.ARGB32.color(alpha,145,70,255);
    }
}
