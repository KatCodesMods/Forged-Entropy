package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.events.AbstractInstantEvent;
import dev.katcodes.forgedentropy.events.ChaosEvent;
import dev.katcodes.forgedentropy.events.ChaosEventRegistry;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.*;
import java.util.function.Supplier;

public class FakeTeleportEvent extends AbstractInstantEvent {
    private static final List<Supplier<ChaosEvent>> TELEPORT_EVENTS = Arrays.asList(CloseRandomTPEvent::new, FarRandomTPEvent::new);//, SkyBlockEvent::new, SkyEvent::new, Teleport0Event::new, TeleportHeavenEvent::new);
    public static final int TICKS_UNTIL_TELEPORT_BACK = 100;
    final Map<ServerPlayer,TeleportInfo> originalPositions = new HashMap<>();
    ChaosEvent teleportEvent = TELEPORT_EVENTS.get(new Random().nextInt(TELEPORT_EVENTS.size())).get();
    int ticksAfterFirstTeleport = 0;

    @Override
    public void init() {
        super.init();
        savePositions(originalPositions);
        teleportEvent.init();
    }

    @Override
    public void tick() {
        if(!teleportEvent.hasEnded())
            teleportEvent.tick();
        else if(++ticksAfterFirstTeleport == TICKS_UNTIL_TELEPORT_BACK) {

            loadPositions(originalPositions);
        }
        super.tick();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void tickClient() {
        if(!teleportEvent.hasEnded())
            teleportEvent.tickClient();
        else
        {}
        //    ticksAfterFirstTeleport++;
        super.tickClient();
    }

    @Override
    public void renderQueueItem(GuiGraphics graphics, float tickDelta, int x, int y) {
        if(hasEnded())
            super.renderQueueItem(graphics, tickDelta, x, y);
        else
            teleportEvent.renderQueueItem(graphics, tickDelta, x, y);
    }

    @Override
    public boolean hasEnded() {
        return teleportEvent.hasEnded() && ticksAfterFirstTeleport > TICKS_UNTIL_TELEPORT_BACK;
    }

    @Override
    public void writeExtraData(ByteBuf buf) {
        ((FriendlyByteBuf)buf).writeUtf(ChaosEventRegistry.getEventId(teleportEvent));
    }

    @Override
    public void readExtraData(ByteBuf buf) {
        teleportEvent = ChaosEventRegistry.get(((FriendlyByteBuf)buf).readUtf());
    }

    public static void savePositions(Map<ServerPlayer, TeleportInfo> positions) {
        positions.clear();
        for(ServerPlayer player: ForgedEntropyMod.eventHandler.getActivePlayers()) {
            BlockPos pos = player.blockPosition();
            positions.put(player,new TeleportInfo(pos.getX(),pos.getY(),pos.getZ(),player.getYHeadRot(),player.getXRot()));
        }
    }

    public static void loadPositions(Map<ServerPlayer, TeleportInfo> positions) {
        for(ServerPlayer player:ForgedEntropyMod.eventHandler.getActivePlayers()) {
            TeleportInfo info = positions.get(player);
            player.fallDistance=0;
            player.teleportTo(player.serverLevel(),info.x,info.y,info.z,info.yaw,info.pitch);
        }
    }


    public static record  TeleportInfo(int x, int y, int z, float yaw, float pitch) {}
}
