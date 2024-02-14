package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.EntropyTags;
import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.events.AbstractTimedEvent;
import net.minecraft.client.gui.GuiGraphics;

public class BulldozeEvent extends AbstractTimedEvent {
    @Override
    public void tick() {
        for(var player: ForgedEntropyMod.eventHandler.getActivePlayers()) {
            var world = player.getCommandSenderWorld();
            var playerBlockPos=player.blockPosition();
            for (int ix = -1; ix <= 1; ix++) {
                for (int iy = 0; iy <= 2; iy++) {
                    for (int iz = -1; iz <= 1; iz++) {
                        var blockPos = playerBlockPos.offset(ix,iy,iz);
                        var state = world.getBlockState(blockPos);
                        if (state.is(EntropyTags.BlockTags.NOT_REPLACED_BY_EVENTS))
                            continue;
                        world.destroyBlock(blockPos,true);
                    }
                }
            }
        }
        super.tick();
    }

    @Override
    public void render(GuiGraphics graphics, float tickDelta) {

    }

    @Override
    public short getDuration() {
        return (short) (Config.baseEventDuration * .5);
    }
}
