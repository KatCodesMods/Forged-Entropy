package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.EntropyTags;
import dev.katcodes.forgedentropy.events.AbstractInstantEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Blocks;

public class PlaceLavaBlockEvent extends AbstractInstantEvent  {

    @Override
    public void initPlayer(ServerPlayer player) {
        super.initPlayer(player);
        if(player.level().getBlockState(player.blockPosition()).is(EntropyTags.BlockTags.NOT_REPLACED_BY_EVENTS))
            return;
        player.level().setBlockAndUpdate(player.blockPosition(), Blocks.LAVA.defaultBlockState());
    }
}
