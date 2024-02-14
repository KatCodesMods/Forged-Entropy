package dev.katcodes.forgedentropy.datagen;

import dev.katcodes.forgedentropy.EntropyTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ForgedEntropyEnchantmentTagsProvider extends TagsProvider<Enchantment> {
    protected ForgedEntropyEnchantmentTagsProvider(PackOutput p_256596_, CompletableFuture<HolderLookup.Provider> p_256513_, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_256596_, Registries.ENCHANTMENT,p_256513_,modId,existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(EntropyTags.EnchantmentTags.DO_NOT_ENCHANT_WITH).add(Enchantments.BINDING_CURSE.builtInRegistryHolder().key(),Enchantments.VANISHING_CURSE.builtInRegistryHolder().key());


    }
}
