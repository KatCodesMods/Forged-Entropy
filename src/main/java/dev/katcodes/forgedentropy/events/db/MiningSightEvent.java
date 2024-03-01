package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.EntropyTags;
import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.events.AbstractTimedEvent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class MiningSightEvent extends AbstractTimedEvent {

    @Override
    public void tick() {
        if(getTickCount()  % 2 ==0) {
            for(var serverPlayerEntity: ForgedEntropyMod.eventHandler.getActivePlayers()) {
                var hitRes = serverPlayerEntity.pick(64, 1, false);
                if(hitRes.getType() == HitResult.Type.BLOCK) {
                    var blockPos = ((BlockHitResult) hitRes).getBlockPos();
                    var blockState = serverPlayerEntity.level().getBlockState(blockPos);
                    var level = serverPlayerEntity.level();
                    if(!level.getBlockState(blockPos).is(EntropyTags.BlockTags.NOT_REPLACED_BY_EVENTS))
                        level.destroyBlock(blockPos, true,serverPlayerEntity);
                }
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
        return (short) (Config.baseEventDuration);
    }

    @Override
    public String type() {
        return "sight";
    }
}
