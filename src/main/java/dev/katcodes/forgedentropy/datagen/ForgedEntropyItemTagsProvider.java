package dev.katcodes.forgedentropy.datagen;

import dev.katcodes.forgedentropy.EntropyTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ForgedEntropyItemTagsProvider extends ItemTagsProvider {


    public ForgedEntropyItemTagsProvider(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_, CompletableFuture<TagLookup<Block>> p_275322_, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_275343_, p_275729_, p_275322_, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        this.tag(EntropyTags.ItemTags.BANNED)
                .add(Items.DEBUG_STICK,
                        Items.COMMAND_BLOCK,
                        Items.CHAIN_COMMAND_BLOCK,
                        Items.REPEATING_COMMAND_BLOCK,
                        Items.BARRIER,
                        Items.STRUCTURE_BLOCK,
                        Items.STRUCTURE_VOID);
        this.tag(EntropyTags.ItemTags.DOES_NOT_DROP_RANDOMLY).addTag(EntropyTags.ItemTags.BANNED);
        this.tag(EntropyTags.ItemTags.DOES_NOT_RAIN).addTag(EntropyTags.ItemTags.BANNED);
        this.tag(EntropyTags.ItemTags.IGNORED_BY_MIDAS_TOUCH).add(Items.AIR);
        this.tag(EntropyTags.ItemTags.MIDAS_TOUCH_GOLDEN_ITEMS).add(Items.GOLD_NUGGET).addOptionalTag(ItemTags.PIGLIN_LOVED);

    }
}
