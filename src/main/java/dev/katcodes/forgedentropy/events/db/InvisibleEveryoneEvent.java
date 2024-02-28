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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

public class InvisibleEveryoneEvent extends AbstractTimedEvent {

    @Override
    public void tick() {

        EventHandler eventHandler = ForgedEntropyMod.eventHandler;
        List<ServerLevel> levels = new ArrayList<>();
        for(var player: eventHandler.getActivePlayers()){
            if(!levels.contains(player.serverLevel()))
                levels.add(player.serverLevel());

        }
        for(var level: levels) {
            for(var entity: level.getAllEntities()) {
                if(shouldBeInvisible(entity)) {
                    ((LivingEntity)entity).addEffect(new MobEffectInstance(MobEffects.INVISIBILITY,2));
                }
            }
        }
        super.tick();
    }

    public boolean shouldBeInvisible(Entity entity){
        return entity instanceof LivingEntity && !entity.getType().is(EntropyTags.EntityTypeTags.NOT_INVISIBLE);
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
        return "invisibility";
    }
}
