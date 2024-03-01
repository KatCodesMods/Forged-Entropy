package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.EntropyTags;
import dev.katcodes.forgedentropy.events.AbstractInstantEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Blocks;

public class PitEvent extends AbstractInstantEvent {

    @Override
    public void initPlayer(ServerPlayer player) {
        super.initPlayer(player);
        BlockPos pos= player.blockPosition();
        int x=pos.getX(), y=pos.getY(), z=pos.getZ();
        for(int h=y-50; h<=319;h++) {
            for (int i=-9;i<=9;i++) {
                for(int j=-9;j<=9;j++) {
                    BlockPos currentPos = new BlockPos(x+i,h,z+j);
                    if(Mth.abs(i) > 2 || Mth.abs(j) > 2){
                        if(!player.level().getBlockState(currentPos).is(EntropyTags.BlockTags.NOT_REPLACED_BY_EVENTS)) {
                            if(h < y-45) {
                                player.level().setBlockAndUpdate(currentPos, Blocks.WATER.defaultBlockState());
                            } else {
                                player.level().setBlockAndUpdate(currentPos, Blocks.AIR.defaultBlockState());
                            }
                        }
                    }
                }
            }
        }
        player.stopRiding();
        player.moveTo(pos.getX(),pos.getY(),pos.getZ());
    }
}
