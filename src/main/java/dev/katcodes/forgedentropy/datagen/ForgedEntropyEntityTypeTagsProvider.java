package dev.katcodes.forgedentropy.datagen;

import dev.katcodes.forgedentropy.EntropyTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ForgedEntropyEntityTypeTagsProvider extends EntityTypeTagsProvider {
    public ForgedEntropyEntityTypeTagsProvider(PackOutput p_256095_, CompletableFuture<HolderLookup.Provider> p_256572_, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_256095_, p_256572_, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(EntropyTags.EntityTypeTags.DO_NOT_EXPLODE).add(EntityType.PLAYER,EntityType.ENDER_DRAGON);
        this.tag(EntropyTags.EntityTypeTags.DO_NOT_IGNITE).add(EntityType.ENDER_DRAGON);
        this.tag(EntropyTags.EntityTypeTags.IGNORED_BY_FORCEFIELD_AND_ENTITY_MAGNET).add(
                EntityType.AREA_EFFECT_CLOUD,
                EntityType.END_CRYSTAL,
                EntityType.GLOW_ITEM_FRAME,
                EntityType.ITEM_FRAME,
                EntityType.LEASH_KNOT,
                EntityType.LIGHTNING_BOLT,
                EntityType.MARKER,
                EntityType.PAINTING,
                EntityType.PLAYER
        );
    }
}
