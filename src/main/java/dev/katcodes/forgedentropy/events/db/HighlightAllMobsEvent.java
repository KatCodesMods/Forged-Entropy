package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.EntropyTags;
import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.events.AbstractTimedEvent;
import dev.katcodes.forgedentropy.server.EventHandler;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Mob;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

public class HighlightAllMobsEvent extends AbstractTimedEvent {

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void tick() {
        EventHandler eventHandler = ForgedEntropyMod.eventHandler;
        List<ServerLevel> worlds = new ArrayList<>();
        for(var player: eventHandler.getActivePlayers()) {
            if(!worlds.contains(player.serverLevel()))
                worlds.add(player.serverLevel());

        }
        for(var world: worlds) {
            for(var entity: world.getAllEntities()) {
                if(entity instanceof Mob && !entity.getType().is(EntropyTags.EntityTypeTags.DO_NOT_HIGHLIGHT))
                    ((Mob)entity).addEffect(new MobEffectInstance(MobEffects.GLOWING,2));
            }
        }
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
}
