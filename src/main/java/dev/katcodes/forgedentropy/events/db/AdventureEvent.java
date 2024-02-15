package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.events.AbstractTimedEvent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class AdventureEvent extends AbstractTimedEvent {


    @Override
    public void initPlayer(ServerPlayer player) {
        player.setGameMode(GameType.ADVENTURE);
        super.initPlayer(player);
    }

    @Override
    public void endPlayer(ServerPlayer player) {
        player.setGameMode(GameType.SURVIVAL);
        super.endPlayer(player);

    }


    @Override
    public void tick() {
        super.tick();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void render(GuiGraphics graphics, float tickDelta) {

    }

    @Override
    public short getDuration() {
        return (short) (Config.baseEventDuration*1.f);
    }
}
