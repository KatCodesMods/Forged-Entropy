package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.events.AbstractInstantEvent;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.server.level.ServerPlayer;
import dev.katcodes.forgedentropy.events.db.FakeTeleportEvent.TeleportInfo;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.HashMap;
import java.util.Map;

public class FakeFakeTeleportEvent extends AbstractInstantEvent {
    private final Map<ServerPlayer, TeleportInfo> positionsBeforeFakeTeleport = new HashMap<>();
    private FakeTeleportEvent fakeTeleportEvent = new FakeTeleportEvent();
    private int ticksAfterSecondTeleport = 0;

    @Override
    public void init() {
        super.init();
        fakeTeleportEvent.init();
    }

    @Override
    public void tick() {
        super.tick();
        if(!fakeTeleportEvent.teleportEvent.hasEnded())
            fakeTeleportEvent.teleportEvent.tick();
        else if(++fakeTeleportEvent.ticksAfterFirstTeleport==FakeTeleportEvent.TICKS_UNTIL_TELEPORT_BACK) {
            FakeTeleportEvent.savePositions(positionsBeforeFakeTeleport);
            FakeTeleportEvent.loadPositions(fakeTeleportEvent.originalPositions);
        }
        else if(fakeTeleportEvent.ticksAfterFirstTeleport > FakeTeleportEvent.TICKS_UNTIL_TELEPORT_BACK && ++ ticksAfterSecondTeleport == FakeTeleportEvent.TICKS_UNTIL_TELEPORT_BACK)
            FakeTeleportEvent.loadPositions(positionsBeforeFakeTeleport);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void tickClient() {
        super.tickClient();
        if(!fakeTeleportEvent.teleportEvent.hasEnded())
            fakeTeleportEvent.teleportEvent.tickClient();

    }

    @Override
    public void renderQueueItem(GuiGraphics graphics, float tickDelta, int x, int y) {
        if(hasEnded())
            super.renderQueueItem(graphics, tickDelta, x, y);
        else
            fakeTeleportEvent.renderQueueItem(graphics,tickDelta,x,y);
    }

    @Override
    public boolean hasEnded() {
        return fakeTeleportEvent.hasEnded() && ticksAfterSecondTeleport > FakeTeleportEvent.TICKS_UNTIL_TELEPORT_BACK;
    }

    @Override
    public void writeExtraData(ByteBuf buf) {
        fakeTeleportEvent.writeExtraData(buf);
    }

    @Override
    public void readExtraData(ByteBuf buf) {
        fakeTeleportEvent.readExtraData(buf);
    }
}
