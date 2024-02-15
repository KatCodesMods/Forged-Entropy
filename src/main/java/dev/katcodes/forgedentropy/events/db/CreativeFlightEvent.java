package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.events.AbstractTimedEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class CreativeFlightEvent extends AbstractTimedEvent {


    @OnlyIn(Dist.CLIENT)
    @Override
    public void initClient() {
        super.initClient();
        Player player = Minecraft.getInstance().player;
        if(player!=null) {
            if(!(player.isCreative() || player.isSpectator())) {
                player.getAbilities().mayfly = true;
            }
        }
    }


    @OnlyIn(Dist.CLIENT)
    @Override
    public void endClient() {
        super.endClient();
        Player player = Minecraft.getInstance().player;
        if(player!=null) {
            if(!(player.isCreative() || player.isSpectator())) {
                player.getAbilities().mayfly = false;
            }
        }
    }


    @Override
    public void initPlayer(ServerPlayer player) {
        if(!(player.isCreative() || player.isSpectator()))
            player.getAbilities().mayfly = true;
        super.initPlayer(player);
    }

    @Override
    public void endPlayer(ServerPlayer player) {
        if(!(player.isCreative() || player.isSpectator()))
            player.getAbilities().mayfly = false;
        super.endPlayer(player);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void render(GuiGraphics graphics, float tickDelta) {

    }

    @Override
    public short getDuration() {
        return (short) (Config.baseEventDuration * 0.75f);
    }
}
