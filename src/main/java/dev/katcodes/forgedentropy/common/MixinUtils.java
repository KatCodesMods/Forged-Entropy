package dev.katcodes.forgedentropy.common;

import dev.katcodes.forgedentropy.EntropyTags;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class MixinUtils {

    public static Item getRandomItem(Level level) {
        Item item = BuiltInRegistries.ITEM.getRandom(level.random).get().value();
        if(item.builtInRegistryHolder().is(EntropyTags.ItemTags.DOES_NOT_DROP_RANDOMLY))
            item = getRandomItem(level);
        return item.requiredFeatures().isSubsetOf(level.enabledFeatures()) ? item : getRandomItem(level);
    }
}
