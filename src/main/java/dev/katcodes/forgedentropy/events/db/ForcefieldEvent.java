package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.EntropyTags;
import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.events.AbstractTimedEvent;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.EntityTypePredicate;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.function.Predicate;

public class ForcefieldEvent extends AbstractTimedEvent {
    private static final Predicate<Entity> ALLOWED_ENTITY = EntitySelector.ENTITY_STILL_ALIVE.and(entity -> !entity.getType().is(EntropyTags.EntityTypeTags.IGNORED_BY_FORCEFIELD_AND_ENTITY_MAGNET));


    @Override
    public void initPlayer(ServerPlayer player) {
        player.level().getEntitiesOfClass(Entity.class, new AABB(player.blockPosition()).inflate(5),ALLOWED_ENTITY).forEach(entity -> {
            entity.addDeltaMovement(getVelocity(player.blockPosition().subtract(entity.blockPosition())));
        });
        super.initPlayer(player);
    }

    public Vec3 getVelocity(BlockPos relativePos) {
        return new Vec3(-relativePos.getX(), -relativePos.getY(), -relativePos.getZ()).scale(0.25);
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
