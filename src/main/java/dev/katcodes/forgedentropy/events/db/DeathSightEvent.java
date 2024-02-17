package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.EntropyTags;
import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.events.AbstractTimedEvent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class DeathSightEvent extends AbstractTimedEvent {

    @Override
    public void initPlayer(ServerPlayer player) {
        var rayVector = player.getLookAngle().normalize().scale(64);
        var fromVector = player.getEyePosition();
        var toVector = fromVector.add(rayVector);
        var box = new AABB(player.position().add(64,64,64),player.position().subtract(64,64,64));
        var hitRes = ProjectileUtil.getEntityHitResult(player,fromVector,toVector,box, x -> true, 2048);
        if(hitRes!=null) {
            var difficulty = player.level().getDifficulty();
            var dmg = difficulty == Difficulty.HARD? 3 : difficulty == Difficulty.NORMAL? 5 : 7;
            var entity = hitRes.getEntity();
            if(entity instanceof LivingEntity &&! entity.getType().is(EntropyTags.EntityTypeTags.DO_NOT_DAMAGE))
            {
                entity.hurt(entity.damageSources().playerAttack(player),dmg);
            }
        }
        super.initPlayer(player);
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
        return "sight";
    }
}
