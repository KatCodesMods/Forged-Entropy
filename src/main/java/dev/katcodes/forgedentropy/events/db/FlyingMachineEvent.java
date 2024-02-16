package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.EntropyTags;
import dev.katcodes.forgedentropy.events.AbstractInstantEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DirectionalBlock;

public class FlyingMachineEvent extends AbstractInstantEvent {

    @Override
    public void initPlayer(ServerPlayer player) {
        super.initPlayer(player);
        if(player.getRandom().nextInt(2) == 0) {
            spawnEastWest(player.serverLevel(),player.blockPosition().offset(1,2,-4));
        } else
            spawnNorthSouth(player.serverLevel(),player.blockPosition().offset(-4,2,1));
    }

    private void spawnNorthSouth(ServerLevel serverLevel, BlockPos startPos) {
        if (!tryClearArea(serverLevel, startPos, 8, 4)) return;

        serverLevel.setBlockAndUpdate(startPos.offset(3, 1, 1), Blocks.STICKY_PISTON.defaultBlockState().setValue(DirectionalBlock.FACING, Direction.SOUTH));
        serverLevel.setBlockAndUpdate(startPos.offset(4, 1, 1), Blocks.SLIME_BLOCK.defaultBlockState());
        serverLevel.setBlockAndUpdate(startPos.offset(5, 1, 1), Blocks.SLIME_BLOCK.defaultBlockState());
        serverLevel.setBlockAndUpdate(startPos.offset(6, 1, 1), Blocks.OBSERVER.defaultBlockState().setValue(DirectionalBlock.FACING, Direction.EAST));

        serverLevel.setBlockAndUpdate(startPos.offset(1, 1, 2), Blocks.OBSERVER.defaultBlockState().setValue(DirectionalBlock.FACING, Direction.WEST));
        serverLevel.setBlockAndUpdate(startPos.offset(2, 1, 2), Blocks.SLIME_BLOCK.defaultBlockState());
        serverLevel.setBlockAndUpdate(startPos.offset(3, 1, 2), Blocks.SLIME_BLOCK.defaultBlockState());
        serverLevel.setBlockAndUpdate(startPos.offset(4, 1, 2), Blocks.STICKY_PISTON.defaultBlockState().setValue(DirectionalBlock.FACING, Direction.NORTH));

        serverLevel.setBlockAndUpdate(startPos.offset(4, 2, 1), Blocks.OBSERVER.defaultBlockState().setValue(DirectionalBlock.FACING, Direction.EAST));
        serverLevel.setBlockAndUpdate(startPos.offset(5, 2, 1), Blocks.REDSTONE_LAMP.defaultBlockState());

        serverLevel.setBlockAndUpdate(startPos.offset(2, 2, 2), Blocks.REDSTONE_LAMP.defaultBlockState());
        serverLevel.setBlockAndUpdate(startPos.offset(3, 2, 2), Blocks.OBSERVER.defaultBlockState().setValue(DirectionalBlock.FACING, Direction.WEST));
    }

    private void spawnEastWest(ServerLevel serverLevel, BlockPos startPos) {
        if (!tryClearArea(serverLevel, startPos, 4, 8)) return;

        serverLevel.setBlockAndUpdate(startPos.offset(1, 1, 3), Blocks.STICKY_PISTON.defaultBlockState().setValue(DirectionalBlock.FACING, Direction.EAST));
        serverLevel.setBlockAndUpdate(startPos.offset(1, 1, 4), Blocks.SLIME_BLOCK.defaultBlockState());
        serverLevel.setBlockAndUpdate(startPos.offset(1, 1, 5), Blocks.SLIME_BLOCK.defaultBlockState());
        serverLevel.setBlockAndUpdate(startPos.offset(1, 1, 6), Blocks.OBSERVER.defaultBlockState().setValue(DirectionalBlock.FACING, Direction.SOUTH));

        serverLevel.setBlockAndUpdate(startPos.offset(2, 1, 1), Blocks.OBSERVER.defaultBlockState().setValue(DirectionalBlock.FACING, Direction.NORTH));
        serverLevel.setBlockAndUpdate(startPos.offset(2, 1, 2), Blocks.SLIME_BLOCK.defaultBlockState());
        serverLevel.setBlockAndUpdate(startPos.offset(2, 1, 3), Blocks.SLIME_BLOCK.defaultBlockState());
        serverLevel.setBlockAndUpdate(startPos.offset(2, 1, 4), Blocks.STICKY_PISTON.defaultBlockState().setValue(DirectionalBlock.FACING, Direction.WEST));

        serverLevel.setBlockAndUpdate(startPos.offset(1, 2, 4), Blocks.OBSERVER.defaultBlockState().setValue(DirectionalBlock.FACING, Direction.SOUTH));
        serverLevel.setBlockAndUpdate(startPos.offset(1, 2, 5), Blocks.REDSTONE_LAMP.defaultBlockState());

        serverLevel.setBlockAndUpdate(startPos.offset(2, 2, 2), Blocks.REDSTONE_LAMP.defaultBlockState());
        serverLevel.setBlockAndUpdate(startPos.offset(2, 2, 3), Blocks.OBSERVER.defaultBlockState().setValue(DirectionalBlock.FACING, Direction.NORTH));
    }

    private boolean tryClearArea(ServerLevel world, BlockPos startPos, int i, int i2) {
        for (int ix = 0; ix < i; ix++) {
            for (int iy = 0; iy < 4; iy++) {
                for (int iz = 0; iz < i2; iz++) {
                    var blockPos = startPos.offset(ix, iy, iz);
                    var currentBlock = world.getBlockState(blockPos);
                    if (currentBlock.is(EntropyTags.BlockTags.NOT_REPLACED_BY_EVENTS))
                        return false;

                    // Make sure flying machine have space to spawn
                    world.setBlockAndUpdate(blockPos,Blocks.AIR.defaultBlockState());
                }
            }
        }
        return true; // Do not spawn flying machine at all
    }


}
