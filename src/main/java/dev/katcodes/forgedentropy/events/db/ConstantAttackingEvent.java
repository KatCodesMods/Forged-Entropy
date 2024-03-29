package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.events.AbstractTimedEvent;
import dev.katcodes.forgedentropy.mixin.MinecraftClientAccessor;
import dev.katcodes.forgedentropy.mixin.MultiPlayerGameModeAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.InteractionHand;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class ConstantAttackingEvent extends AbstractTimedEvent {

    @OnlyIn(Dist.CLIENT)
    @Override
    public void tickClient() {
        super.tickClient();

        Minecraft client = Minecraft.getInstance();
        switch (client.hitResult.getType()) {
            case BLOCK:
                ((MinecraftClientAccessor)client).setMissTime(0);
                ((MinecraftClientAccessor)client).callContinueAttack(true);
                return;
            case ENTITY:
                if(client.player.getAttackStrengthScale(0.0f)>=1.0f) {
                    ((MinecraftClientAccessor)client).callStartAttack();
                }
                break;
            case MISS:
                client.player.swing(InteractionHand.MAIN_HAND);
        }
        cancelBlockBreaking(client);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void endClient() {
        cancelBlockBreaking(Minecraft.getInstance());
        this.hasEnded=true;
    }

    @OnlyIn(Dist.CLIENT)
    private void cancelBlockBreaking(Minecraft instance) {
        ((MultiPlayerGameModeAccessor)instance.gameMode).setIsDestroying(true);
        ((MinecraftClientAccessor)instance).callContinueAttack(false);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void render(GuiGraphics graphics, float tickDelta) {

    }

    @Override
    public String type() {
        return "attack";
    }

    @Override
    public short getDuration() {
        return (short) Config.baseEventDuration;
    }
}
