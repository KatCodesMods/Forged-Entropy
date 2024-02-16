package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.events.AbstractInstantEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.ClipBlockStateContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.util.Constants;

import java.util.Random;

public class FarRandomTPEvent extends AbstractInstantEvent {
    int count = 0;
    MinecraftServer server;

    @Override
    public void init() {
        super.init();
        Random random=new Random();
        BlockPos randomLocation = new BlockPos(random.nextInt(5000)-2500,0,random.nextInt(5000)-2500);
        server= ForgedEntropyMod.eventHandler.server;
        ForgedEntropyMod.eventHandler.getActivePlayers().forEach(serverPlayer -> {
            serverPlayer.stopRiding();
            server.getCommands().performPrefixedCommand(server.createCommandSourceStack(),"spreadplayers "+randomLocation.getX()+" "+randomLocation.getZ()+" 0 120 false "+serverPlayer.getName().getString());
            serverPlayer.level().destroyBlock(serverPlayer.blockPosition(),false);
            serverPlayer.level().destroyBlock(serverPlayer.blockPosition().above(),false);
            BlockHitResult blockHitResult=serverPlayer.level().clip(new ClipContext(serverPlayer.position(),serverPlayer.position().subtract(0,-6,0),ClipContext.Block.OUTLINE,ClipContext.Fluid.ANY,serverPlayer));
            if(blockHitResult.getType()== HitResult.Type.MISS || serverPlayer.level().getBlockState(blockHitResult.getBlockPos()).liquid()) {
                serverPlayer.level().setBlock(serverPlayer.blockPosition().below(), Blocks.STONE.defaultBlockState(), 3);

            }
        });
    }

    @Override
    public void tick() {
        if(count<=2) {
            if(count==2) {
                ForgedEntropyMod.eventHandler.getActivePlayers().forEach(serverPlayer -> {
                    serverPlayer.level().destroyBlock(serverPlayer.blockPosition(),false);
                    serverPlayer.level().destroyBlock(serverPlayer.blockPosition().above(),false);
                    BlockHitResult blockHitResult=serverPlayer.level().clip(new ClipContext(serverPlayer.position(),serverPlayer.position().subtract(0,-6,0),ClipContext.Block.OUTLINE,ClipContext.Fluid.ANY,serverPlayer));
                    if(blockHitResult.getType()== HitResult.Type.MISS || serverPlayer.level().getBlockState(blockHitResult.getBlockPos()).liquid()) {
                        serverPlayer.level().setBlock(serverPlayer.blockPosition().below(), Blocks.STONE.defaultBlockState(), 3);

                    }
                });
            }
            count++;
        }
    }

    @Override
    public boolean hasEnded() {
        return count>2;
    }
}
