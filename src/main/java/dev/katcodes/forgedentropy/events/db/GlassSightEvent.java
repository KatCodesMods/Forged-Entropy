package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.EntropyTags;
import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.events.AbstractTimedEvent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.Tags;

public class GlassSightEvent extends AbstractTimedEvent {

    @Override
    public void tick() {
        super.tick();
        if(tickCount%5==0) {

            ForgedEntropyMod.eventHandler.getActivePlayers().forEach(serverPlayer -> {
                var hitRes = serverPlayer.pick(64,1,false);
                if(hitRes.getType()== HitResult.Type.BLOCK) {
                    var blockHitRes = (BlockHitResult)hitRes;

                    if(!serverPlayer.level().getBlockState(blockHitRes.getBlockPos()).is(EntropyTags.BlockTags.NOT_REPLACED_BY_EVENTS)) {
                        BuiltInRegistries.BLOCK.getTag(Tags.Blocks.GLASS).ifPresentOrElse(
                                glassBlocks -> {
                                    serverPlayer.level().setBlockAndUpdate(blockHitRes.getBlockPos(),glassBlocks.getRandomElement(serverPlayer.getRandom()).get().value().defaultBlockState());
                                },
                                () -> {
                                    serverPlayer.level().setBlockAndUpdate(blockHitRes.getBlockPos(), Blocks.GLASS.defaultBlockState());
                                }
                        );
                    }
                }
            });
        }
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
