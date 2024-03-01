package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.EntropyTags;
import dev.katcodes.forgedentropy.events.AbstractInstantEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DirectionalBlock;

public class NoiseMachineEvent extends AbstractInstantEvent {
    private int numModules = 2;

    @Override
    public void initPlayer(ServerPlayer player) {
        super.initPlayer(player);
        ServerLevel level = player.serverLevel();
        BlockPos pos = player.blockPosition().above(15);
        boolean placeable = false;
        outerloop:
            for(int retries = 3; retries >= 0 ; retries --) {
                for(int ix = -2 ; ix <= 2 ; ix++ ) {
                    for(int iy = -1; iy <= 2; iy++) {
                        for(int iz = -((numModules / 2) + 1);iz<= (numModules+1) / 2; iz++) {
                            var testPos = pos.offset(ix, iy, iz);
                            var currentBlock = level.getBlockState(testPos);
                            if(currentBlock.is(EntropyTags.BlockTags.NOT_REPLACED_BY_EVENTS)) {
                                pos=pos.above(4);
                                continue outerloop;
                            }
                        }
                    }
                }
                placeable = true;
                break;
            }
            if(!placeable) return;

            pos = pos.north((numModules / 2));
            for(int iz = numModules; iz > 0;iz--) {
                level.setBlockAndUpdate(pos, Blocks.OBSERVER.defaultBlockState().setValue(DirectionalBlock.FACING, Direction.EAST));
                level.setBlockAndUpdate(pos.west(1),Blocks.OBSIDIAN.defaultBlockState());
                level.setBlockAndUpdate(pos.east(1),Blocks.BELL.defaultBlockState());
                level.setBlockAndUpdate(pos.above(1), Blocks.OBSERVER.defaultBlockState().setValue(DirectionalBlock.FACING, Direction.WEST));
                level.setBlockAndUpdate(pos.above(1).east(1), Blocks.OBSIDIAN.defaultBlockState());
                level.setBlockAndUpdate(pos.above(1).west(1), Blocks.BELL.defaultBlockState());
                for(int i = -2 ; i <= 2; i++) {
                    level.setBlockAndUpdate(pos.offset(i,-1,0),Blocks.OBSIDIAN.defaultBlockState());
                    level.setBlockAndUpdate(pos.offset(i,2,0),Blocks.OBSIDIAN.defaultBlockState());
                    if(Mth.abs(i) == 2) {
                        level.setBlockAndUpdate(pos.offset(i,0,0),Blocks.OBSIDIAN.defaultBlockState());
                        level.setBlockAndUpdate(pos.offset(i,1,0),Blocks.OBSIDIAN.defaultBlockState());
                    }
                }
                pos = pos.south();
            }
            for(int iz = 0; iz < 2; iz++) {
                for(int ix = -2; ix <= 2 ; ix++) {
                    for(int iy = -1; iy <= 2; iy++) {
                        level.setBlockAndUpdate(pos.offset(ix,iy,0),Blocks.OBSIDIAN.defaultBlockState());
                    }
                }
                pos=pos.north(numModules+1);
            }
    }
}
