package dev.katcodes.forgedentropy.datagen;

import net.minecraft.data.DataGenerator;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class ForgedEntropyDataGen {


    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ForgedEntropyBlockTagsProvider blockTagsProvider = new ForgedEntropyBlockTagsProvider(gen.getPackOutput(),event.getLookupProvider(),"forged_entropy",event.getExistingFileHelper());
        event.getGenerator().addProvider(event.includeServer(),blockTagsProvider);

        ForgedEntropyItemTagsProvider itemTagsProvider=new ForgedEntropyItemTagsProvider(gen.getPackOutput(),event.getLookupProvider(), blockTagsProvider.contentsGetter(),"forged_entropy",event.getExistingFileHelper());
        event.getGenerator().addProvider(event.includeServer(), itemTagsProvider);
        event.getGenerator().addProvider(event.includeServer(), new ForgedEntropyEnchantmentTagsProvider(gen.getPackOutput(),event.getLookupProvider(),"forged_entropy",event.getExistingFileHelper()));
        event.getGenerator().addProvider(event.includeServer(),new ForgedEntropyEntityTypeTagsProvider(gen.getPackOutput(),event.getLookupProvider(),"forged_entropy",event.getExistingFileHelper()));
    }

}
