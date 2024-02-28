package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.events.AbstractInstantEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.HashMap;

public class InfestationEvent extends AbstractInstantEvent {
    private static HashMap<Block,Block> _blockConversion = new HashMap<Block,Block>() {
        {
            put(Blocks.CHISELED_STONE_BRICKS, Blocks.INFESTED_CHISELED_STONE_BRICKS);
            put(Blocks.COBBLESTONE, Blocks.INFESTED_COBBLESTONE);
            put(Blocks.CRACKED_STONE_BRICKS, Blocks.INFESTED_CRACKED_STONE_BRICKS);
            put(Blocks.DEEPSLATE, Blocks.INFESTED_DEEPSLATE);
            put(Blocks.MOSSY_COBBLESTONE, Blocks.INFESTED_MOSSY_STONE_BRICKS);
            put(Blocks.STONE, Blocks.INFESTED_STONE);
            put(Blocks.STONE_BRICKS, Blocks.INFESTED_STONE_BRICKS);
        }
    };
    @Override
    public void initPlayer(ServerPlayer player) {
        super.initPlayer(player);
        var rng = player.getRandom();
        var level = player.level();
        var startPos = player.blockPosition().offset(-32,-32,-32);
        var endPos = player.blockPosition().offset(64,64,64);
        for(int ix=startPos.getX();ix<endPos.getX();ix++) {
            for (int iy=startPos.getY();iy < endPos.getY(); iy++) {
                for(int iz=startPos.getZ(); iz < endPos.getZ(); iz++) {
                    if(rng.nextDouble() < 0.5d)
                        continue;
                    var blockPos = new BlockPos(ix,iy,iz);
                    var block = level.getBlockState(blockPos).getBlock();
                    if(_blockConversion.containsKey(block))
                        level.setBlockAndUpdate(blockPos,_blockConversion.get(block).defaultBlockState());
                }
            }
        }

    }
}
