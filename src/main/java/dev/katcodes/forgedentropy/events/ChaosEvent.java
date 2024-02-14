package dev.katcodes.forgedentropy.events;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public interface ChaosEvent {

    default void init() {

    }

    @OnlyIn(Dist.CLIENT)
    default void initClient() {
    }

    void end();

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
