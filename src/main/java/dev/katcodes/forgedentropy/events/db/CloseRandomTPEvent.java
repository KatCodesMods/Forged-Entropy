package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.events.AbstractInstantEvent;
import net.minecraft.server.MinecraftServer;
import net.neoforged.fml.javafmlmod.FMLModContainer;
import net.neoforged.fml.loading.FMLConfig;

public class CloseRandomTPEvent extends AbstractInstantEvent {
    int count = 0;
    MinecraftServer server;
    @Override
    public void init() {
        server= ForgedEntropyMod.eventHandler.server;
        ForgedEntropyMod.eventHandler.getActivePlayers().forEach(serverPlayer ->  {
            serverPlayer.stopRiding();
            server.getCommands().performPrefixedCommand(server.createCommandSourceStack(),"spreadplayers "+serverPlayer.getX()+" "+serverPlayer.getZ()+" 0 50 false "+serverPlayer.getName().getString());

        });
    }
}
