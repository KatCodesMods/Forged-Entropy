package dev.katcodes.forgedentropy;

import com.mojang.logging.LogUtils;
import dev.katcodes.forgedentropy.client.ClientEventHandler;
import dev.katcodes.forgedentropy.client.ClientNetworkHandler;
import dev.katcodes.forgedentropy.client.ForgedEntropyClient;
import dev.katcodes.forgedentropy.datagen.ForgedEntropyDataGen;
import dev.katcodes.forgedentropy.events.ChaosEvent;
import dev.katcodes.forgedentropy.events.ChaosEventRegistry;
import dev.katcodes.forgedentropy.networking.*;
import dev.katcodes.forgedentropy.server.EventHandler;
import dev.katcodes.forgedentropy.server.ServerNetworkHandler;
import dev.katcodes.forgedentropy.server.commands.EntropyCommands;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;
import net.neoforged.neoforge.registries.*;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ForgedEntropyMod.MODID)
public class ForgedEntropyMod
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "forged_entropy";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public static EventHandler eventHandler;

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public ForgedEntropyMod(IEventBus modEventBus)
    {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::gatherData);
        modEventBus.addListener(this::registerNetwork);




        ChaosEventRegistry.Register();

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);
        NeoForge.EVENT_BUS.register(ClientNetworkHandler.Get());
        NeoForge.EVENT_BUS.register(ServerNetworkHandler.Get());

        NeoForge.EVENT_BUS.register(ForgedEntropyClient.class);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    @SubscribeEvent
    public void registerCommands(RegisterCommandsEvent event){
        EntropyCommands.register(event.getDispatcher());
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

    }



    public void gatherData(GatherDataEvent event)
    {
        ForgedEntropyDataGen.gatherData(event);
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if(eventHandler!=null)
            eventHandler.tick(false);
    }
    public void registerNetwork(RegisterPayloadHandlerEvent event) {
        final IPayloadRegistrar registrar=event.registrar(MODID);
        registrar.play(NetworkingIdentifiers.END_EVENT, EndEventPacket::new,handler -> handler.client(ClientNetworkHandler.Get()::end_event).server(ServerNetworkHandler.Get()::end_event));
        registrar.play(NetworkingIdentifiers.JOIN_CONFIRM, JoinConfirmPacket::new, handler -> handler.client(ClientNetworkHandler.Get()::join_confirm).server(ServerNetworkHandler.Get()::join_confirm));
        registrar.play(NetworkingIdentifiers.JOIN_SYNC, JoinSyncPacket::new, handler -> handler.client(ClientNetworkHandler.Get()::join_sync).server(ServerNetworkHandler.Get()::join_sync));
        registrar.play(NetworkingIdentifiers.TICK, TickPacket::new, handler -> handler.client(ClientNetworkHandler.Get()::tick).server(ServerNetworkHandler.Get()::tick));
        registrar.play(NetworkingIdentifiers.REMOVE_FIRST, RemoveFirstPacket::new, handler -> handler.client(ClientNetworkHandler.Get()::remove_first).server(ServerNetworkHandler.Get()::remove_first));
        registrar.play(NetworkingIdentifiers.REMOVE_ENDED, RemoveEndedPacket::new, handler -> handler.client(ClientNetworkHandler.Get()::remove_ended).server(ServerNetworkHandler.Get()::remove_ended));
        registrar.play(NetworkingIdentifiers.ADD_EVENT, AddEventPacket::new, handler -> handler.client(ClientNetworkHandler.Get()::add_event).server(ServerNetworkHandler.Get()::add_event));
        registrar.play(NetworkingIdentifiers.POLL_STATUS,PollStatePacket::new,handler -> handler.client(ClientNetworkHandler.Get()::update_poll).server(ServerNetworkHandler.Get()::update_poll));
        registrar.play(NetworkingIdentifiers.JOIN_HANDSHAKE,JoinHandshakePacket::new,handler -> handler.client(ClientNetworkHandler.Get()::join_handshake).server(ServerNetworkHandler.Get()::join_handshake));
    }
    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {

            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
            ForgedEntropyClient.getInstance().initialize();

        }
    }
}
