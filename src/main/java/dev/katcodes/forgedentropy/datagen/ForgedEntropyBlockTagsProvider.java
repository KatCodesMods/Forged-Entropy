package dev.katcodes.forgedentropy.datagen;

import dev.katcodes.forgedentropy.EntropyTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.internal.NeoForgeBlockTagsProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ForgedEntropyBlockTagsProvider extends BlockTagsProvider {
    public ForgedEntropyBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, modId, existingFileHelper);
    }
    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(EntropyTags.BlockTags.IGNORED_BY_MIDAS_TOUCH)
                .addTag(EntropyTags.BlockTags.NOT_REPLACED_BY_EVENTS)
                .add(Blocks.AIR,
                        Blocks.GOLD_BLOCK,
                        Blocks.GOLD_ORE,
                        Blocks.RAW_GOLD_BLOCK,
                        Blocks.NETHER_GOLD_ORE);
        this.tag(EntropyTags.BlockTags.NOT_REPLACED_BY_EVENTS).add(Blocks.BEDROCK,Blocks.END_PORTAL,Blocks.END_PORTAL_FRAME);
        this.tag(EntropyTags.BlockTags.NOT_REPLACED_BY_ZEUS_ULT).add(Blocks.END_PORTAL,Blocks.END_PORTAL_FRAME);
        this.tag(EntropyTags.BlockTags.SHOWN_DURING_XRAY)
                .add(Blocks.COAL_ORE,
                        Blocks.IRON_ORE,
                        Blocks.RAW_IRON_BLOCK,
                        Blocks.IRON_BLOCK,
                        Blocks.RAW_COPPER_BLOCK,
                        Blocks.COPPER_BLOCK,
                        Blocks.RAW_GOLD_BLOCK,
                        Blocks.GOLD_BLOCK,
                        Blocks.DIAMOND_BLOCK,
                        Blocks.ANCIENT_DEBRIS,
                        Blocks.NETHERITE_BLOCK,
                        Blocks.LAPIS_BLOCK,
                        Blocks.EMERALD_BLOCK,
                        Blocks.REDSTONE_BLOCK,
                        Blocks.BEDROCK,
                        Blocks.OBSIDIAN).addOptionalTag(Tags.Blocks.ORES);
        this.tag(EntropyTags.BlockTags.VOID_SIGHT_BREAKS).add(Blocks.FURNACE).add(Blocks.BLAST_FURNACE).add(Blocks.SMOKER).addOptionalTag(Tags.Blocks.CHESTS).addOptionalTag(Tags.Blocks.BARRELS);
    }
}
