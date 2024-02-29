package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.events.AbstractTimedEvent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LagEvent extends AbstractTimedEvent {
    RandomSource random;
    boolean saved_pos;
    int countdown;
    Map<ServerPlayer, BlockPos> player_positions;

    @Override
    public void init() {
        super.init();
        random=RandomSource.create();
        saved_pos=false;
        countdown=0;
        player_positions=new HashMap<>();
    }

    @Override
    public void tick() {
        if(countdown > 0){
            countdown--;
            super.tick();
            return;
        }
        if(saved_pos) {
            ForgedEntropyMod.eventHandler.getActivePlayers().forEach(serverPlayer -> {

                if(player_positions.containsKey(serverPlayer)) {
                    BlockPos pos = serverPlayer.blockPosition();
                    serverPlayer.stopRiding();
                    serverPlayer.teleportTo(pos.getX(),pos.getY(),pos.getZ());
                }
            });
            saved_pos=false;
        } else {
            ForgedEntropyMod.eventHandler.getActivePlayers().forEach(serverPlayer -> {
                player_positions.put(serverPlayer,serverPlayer.blockPosition());
            });
            saved_pos=true;
        }
        countdown = random.nextIntBetweenInclusive(10,40);
        super.tick();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void render(GuiGraphics graphics, float tickDelta) {

    }

    @Override
    public short getDuration() {
        return (short) Config.baseEventDuration;
    }

    @Override
    public String type() {
        return "movement";
    }

    @Override
    public boolean isDisabledByAccessibilityMode() {
        return true;
    }
}
