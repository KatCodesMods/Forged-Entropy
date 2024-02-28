package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.events.AbstractTimedEvent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.GameRules;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class InfiniteLavaEvent extends AbstractTimedEvent {

    @Override
    public void init() {
        super.init();
        MinecraftServer server = ForgedEntropyMod.eventHandler.server;
        server.getGameRules().getRule(GameRules.RULE_LAVA_SOURCE_CONVERSION).set(true,server);
    }

    @Override
    public void end() {
        super.end();
        MinecraftServer server = ForgedEntropyMod.eventHandler.server;
        server.getGameRules().getRule(GameRules.RULE_LAVA_SOURCE_CONVERSION).set(false,server);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void render(GuiGraphics graphics, float tickDelta) {

    }

    @Override
    public short getDuration() {
        return (short) Config.baseEventDuration;
    }
}
