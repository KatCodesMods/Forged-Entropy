package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.events.AbstractTimedEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

public class EntityMagnetEvent extends ForcefieldEvent {

    @Override
    public Vec3 getVelocity(BlockPos relativePos) {
        return new Vec3(relativePos.getX(),relativePos.getY(),relativePos.getZ());
    }
}
