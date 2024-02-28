package dev.katcodes.forgedentropy.events.db;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Monster;

public class InvisibleHostileMobsEvent extends InvisibleEveryoneEvent {

    @Override
    public boolean shouldBeInvisible(Entity entity) {
        return entity instanceof Monster && super.shouldBeInvisible(entity);
    }
}
