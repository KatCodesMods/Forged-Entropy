package dev.katcodes.forgedentropy.events;

import dev.katcodes.forgedentropy.ForgedEntropyMod;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public interface ChaosEvent {

    default void init() {
        ForgedEntropyMod.eventHandler.getActivePlayers().forEach(this::initPlayer);
    }

    default void initPlayer(ServerPlayer player) {

    }

    @OnlyIn(Dist.CLIENT)
    default void initClient() {
    }

    default void end() {
        ForgedEntropyMod.eventHandler.getActivePlayers().forEach(this::endPlayer);
    }

    default void endPlayer(ServerPlayer player) {

    }

    @OnlyIn(Dist.CLIENT)
    void endClient();

    default void endPlayer(Player player){

    }

    @OnlyIn(Dist.CLIENT)
    void render(GuiGraphics graphics, float tickDelta);

    @OnlyIn(Dist.CLIENT)
    void renderQueueItem(GuiGraphics graphics, float tickDelta, int x, int y);

    void tick();

    @OnlyIn(Dist.CLIENT)
    void tickClient();

    short getTickCount();

    void setTickCount(short index);

    short getDuration();

    boolean hasEnded();

    void setEnded(boolean ended);

    default String type() {
        return "none";
    }

    default boolean isDisabledByAccessibilityMode() {
        return false;
    }

    default void writeExtraData(ByteBuf buf) {}

    @OnlyIn(Dist.CLIENT)
    default void readExtraData(ByteBuf buf) {}
}
