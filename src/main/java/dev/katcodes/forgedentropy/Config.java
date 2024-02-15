package dev.katcodes.forgedentropy;

import dev.katcodes.forgedentropy.client.integrations.Integration;
import dev.katcodes.forgedentropy.client.integrations.IntegrationTypes;
import dev.katcodes.forgedentropy.events.ChaosEventRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = ForgedEntropyMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static void InitializeGeneral()
    {
        BUILDER.push("General");
        TIMER_DURATION = BUILDER.comment("Length of time in ticks between events").defineInRange("timerDuration",900,0,Integer.MAX_VALUE);
        BASE_EVENT_DURATION = BUILDER.comment("Base length of time for events in ticks").defineInRange("baseDuration",600,0,Integer.MAX_VALUE);
        INTEGRATIONS = BUILDER.comment("Are integrations enabled?").define("integrations", false);
        VOTING_MODE = BUILDER.comment("Highest vote wins or chance of votes").defineEnum("votingMethod",VotingMode.Proportional);
        UI_STYLE = BUILDER.comment("Style for the UI").defineEnum("uiStyle",UIStyle.GTAV);
        DISABLED_EVENTS = BUILDER.comment("Disabled Events").defineListAllowEmpty("disabledEvents",List.of(""),Config::validateEventName);
        ACCESSIBILITY = BUILDER.comment("Remove flashy effects").define("accessibilityMode", false);
        BUILDER.pop();
    }
    private static void InitializeIntegration()
    {
        BUILDER.push("Integrations");
        IntegrationSettings.INTEGRATION_TYPE = BUILDER.comment("what kind of voting should be enabled").defineEnum("integrationType",IntegrationTypes.None);
        IntegrationSettings.SEND_CHAT_MESSAGES = BUILDER.comment("Should the vote options be sent as a chat message").define("sendChatMessages",false);
        IntegrationSettings.SHOW_CURRENT_PERCENTAGE=BUILDER.comment("Should the current vote percentage be shown? (Note effects the overlay too)").define("showCurrentPercentage",true);
        IntegrationSettings.SHOW_UPCOMING_EVENTS=BUILDER.comment("Show the upcoming events on minecraft screen").define("showUpcomingEvents",false);
        BUILDER.push("Twitch");
        IntegrationSettings.TwitchSettings.AUTH_TOKEN= BUILDER.comment("Twitch oauth token").define("authToken","");
        IntegrationSettings.TwitchSettings.CHANNEL=BUILDER.comment("Twitch channel to watch/vote in").define("channel","");
        BUILDER.pop();
        BUILDER.pop();
    }
    static {
        InitializeGeneral();
        InitializeIntegration();
    }



    private static ModConfigSpec.IntValue TIMER_DURATION;
    private static ModConfigSpec.IntValue BASE_EVENT_DURATION;

    private static ModConfigSpec.BooleanValue INTEGRATIONS;
    private static ModConfigSpec.EnumValue<VotingMode> VOTING_MODE;
    private static ModConfigSpec.EnumValue<UIStyle> UI_STYLE;
    private static ModConfigSpec.ConfigValue<List<? extends String>> DISABLED_EVENTS;
    private static ModConfigSpec.BooleanValue ACCESSIBILITY;


    static final ModConfigSpec SPEC = BUILDER.build();

    public static int timerDuration;
    public static int baseEventDuration;
    public static boolean integrations;
    public static VotingMode votingMode;
    public static UIStyle uiStyle;
    public static List<String> disabledEvents;
    public static boolean accessibilityMode;

    public static class IntegrationSettings {
        public static IntegrationTypes integrationType;
        public static boolean sendChatMessages = true;
        public static boolean showCurrentPercentage = true;

        public static boolean showUpcomingEvents = true;

        private static ModConfigSpec.EnumValue<IntegrationTypes> INTEGRATION_TYPE;
        private static  ModConfigSpec.BooleanValue SEND_CHAT_MESSAGES;
        private static ModConfigSpec.BooleanValue SHOW_CURRENT_PERCENTAGE;
        private static ModConfigSpec.BooleanValue SHOW_UPCOMING_EVENTS;

        public static class TwitchSettings {
            public static String authToken;
            public static String channel;
            private static ModConfigSpec.ConfigValue<String> AUTH_TOKEN;
            private static ModConfigSpec.ConfigValue<String> CHANNEL;
        }
    }

    private static boolean validateEventName(final Object obj) {
        return obj instanceof String; // && ChaosEventRegistry.EntropyEvents.keySet().contains();
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {

        timerDuration = TIMER_DURATION.get();
        baseEventDuration = BASE_EVENT_DURATION.get();
        integrations = INTEGRATIONS.get();
        votingMode = VOTING_MODE.get();
        uiStyle = UI_STYLE.get();
        disabledEvents = new ArrayList<>(DISABLED_EVENTS.get());
        accessibilityMode = ACCESSIBILITY.get();
        IntegrationSettings.integrationType=IntegrationSettings.INTEGRATION_TYPE.get();
        IntegrationSettings.sendChatMessages= IntegrationSettings.SEND_CHAT_MESSAGES.get();
        IntegrationSettings.showCurrentPercentage=IntegrationSettings.SHOW_CURRENT_PERCENTAGE.get();
        IntegrationSettings.showUpcomingEvents=IntegrationSettings.SHOW_UPCOMING_EVENTS.get();
        IntegrationSettings.TwitchSettings.authToken=IntegrationSettings.TwitchSettings.AUTH_TOKEN.get();
        IntegrationSettings.TwitchSettings.channel= IntegrationSettings.TwitchSettings.CHANNEL.get();

    }
}
