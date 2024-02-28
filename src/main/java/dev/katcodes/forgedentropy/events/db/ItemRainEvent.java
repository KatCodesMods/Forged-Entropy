package dev.katcodes.forgedentropy.events.db;

import dev.katcodes.forgedentropy.Config;
import dev.katcodes.forgedentropy.EntropyTags;
import dev.katcodes.forgedentropy.ForgedEntropyMod;
import dev.katcodes.forgedentropy.client.ForgedEntropyClient;
import dev.katcodes.forgedentropy.events.AbstractTimedEvent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.Random;

public class ItemRainEvent extends AbstractTimedEvent {
    Random random;

    @Override
    public void init() {
        super.init();
        random = new Random();
    }


    @Override
    public void tick() {
        if(getTickCount() % 15 == 0 ) {
            for(int i=0;i<5;i++) {
                ForgedEntropyMod.eventHandler.getActivePlayers().forEach(serverPlayer -> {
                    Level level = serverPlayer.level();
                    ItemEntity item = new ItemEntity(level,serverPlayer.getX()+(random.nextInt(100)-50),serverPlayer.getY()+50+(random.nextInt(10)-5),serverPlayer.getZ()+(random.nextInt(100)-50),new ItemStack(getRandomItem(level),1));
                    level.addFreshEntity(item);
                });
            }
        }
        super.tick();
    }

    public Item getRandomItem(Level level) {
        Item item = BuiltInRegistries.ITEM.getRandom(RandomSource.create()).get().value();
        if(item.builtInRegistryHolder().is(EntropyTags.ItemTags.DOES_NOT_RAIN))
            item = getRandomItem(level);
        return item.requiredFeatures().isSubsetOf(level.enabledFeatures()) ? item : getRandomItem(level);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void render(GuiGraphics graphics, float tickDelta) {

    }

    @Override
    public String type() {
        return "rain";
    }

    @Override
    public short getDuration() {
        return (short) (Config.baseEventDuration);
    }
}
