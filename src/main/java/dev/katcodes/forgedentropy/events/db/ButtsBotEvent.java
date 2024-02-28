package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.events.AbstractInstantEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.phys.Vec3;

import java.util.stream.Collectors;

public class ButtsBotEvent extends AbstractInstantEvent {

    private Vec3 getEdgeOfBlock(BlockPos pPos, BlockPos pOther) {
        double d0 = 0.5;
        double d1 = 0.5 * (double) Mth.sign((double)(pOther.getX() - pPos.getX()));
        double d2 = 0.5 * (double)Mth.sign((double)(pOther.getZ() - pPos.getZ()));
        return Vec3.atBottomCenterOf(pOther).add(d1, 0.0, d2);
    }

    @Override
    public void initPlayer(ServerPlayer player) {
        super.initPlayer(player);
        Goat goat = EntityType.GOAT.spawn(player.serverLevel(),player.blockPosition(), MobSpawnType.SPAWN_EGG);
        goat.setScreamingGoat(true);
        ForgedEntropyMod.LOGGER.info("Running task {}", goat.getBrain().getActiveActivities().stream().map(Activity::getName).collect(Collectors.joining(",")));

        goat.getBrain().eraseMemory(MemoryModuleType.RAM_COOLDOWN_TICKS);
        goat.getBrain().setMemory(MemoryModuleType.RAM_TARGET,this.getEdgeOfBlock(goat.blockPosition(),player.blockPosition()));
    }
}
