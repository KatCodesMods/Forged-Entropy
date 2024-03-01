package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.EntropyTags;
import dev.katcodes.forgedentropy.events.AbstractInstantEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Blocks;

public class PoolEvent extends AbstractInstantEvent {
    @Override
    public void initPlayer(ServerPlayer player) {
        super.initPlayer(player);
        ServerLevel level=player.serverLevel();
        int x =player.getBlockX(), y=player.getBlockY(), z=player.getBlockZ();
        for(int i=y; i > y-6;i--) {
            for(int j=-4;j<5;j++) {
                for(int k=-4;k<5;k++) {
                    BlockPos pos = new BlockPos(x+j,i,z+k);
                    if(level.getBlockState(pos).is(EntropyTags.BlockTags.NOT_REPLACED_BY_EVENTS))
                        continue;
                    if(i==y-5)
                    {
                        if(j%2==0) {
                            level.setBlockAndUpdate(pos,(k%2==0)? Blocks.MAGMA_BLOCK.defaultBlockState():Blocks.SOUL_SAND.defaultBlockState());
                        } else {
                            level.setBlockAndUpdate(pos,(k%2!=0)? Blocks.MAGMA_BLOCK.defaultBlockState():Blocks.SOUL_SAND.defaultBlockState());
                        }
                    } else {
                        level.setBlockAndUpdate(pos,(j==-4 ||j==4 ||k==-4 ||k==4) ? Blocks.COBBLESTONE.defaultBlockState() :Blocks.WATER.defaultBlockState());
                    }
                }
            }
        }
    }
}
