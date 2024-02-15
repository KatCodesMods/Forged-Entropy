package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.events.AbstractInstantEvent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.fml.javafmlmod.FMLModContainer;
import net.neoforged.fml.loading.FMLConfig;

public class CloseRandomTPEvent extends AbstractInstantEvent {
    int count = 0;
    MinecraftServer server;
    @Override
    public void init() {
        server= ForgedEntropyMod.eventHandler.server;
        super.init();
    }

    @Override
    public void initPlayer(ServerPlayer player) {
        player.stopRiding();
        server.getCommands().performPrefixedCommand(server.createCommandSourceStack(),"spreadplayers "+player.getX()+" "+player.getZ()+" 0 50 false "+player.getName().getString());
        super.initPlayer(player);
    }
}
