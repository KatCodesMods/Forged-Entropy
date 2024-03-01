package dev.katcodes.forgedentropy;

import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;

public class EntropyTags {
    public static class BlockTags {
        public static final TagKey<Block> IGNORED_BY_MIDAS_TOUCH = net.minecraft.tags.BlockTags.create(new ResourceLocation("forged_entropy","ignored_by_midas_touch"));
        public static final TagKey<Block> NOT_REPLACED_BY_EVENTS = net.minecraft.tags.BlockTags.create(new ResourceLocation("forged_entropy","not_replaced_by_events"));
        public static final TagKey<Block> NOT_REPLACED_BY_ZEUS_ULT = net.minecraft.tags.BlockTags.create(new ResourceLocation("forged_entropy","not_replaced_by_zeus_ult"));
        public static final TagKey<Block> SHOWN_DURING_XRAY = net.minecraft.tags.BlockTags.create(new ResourceLocation("forged_entropy","shown_during_xray"));
        public static final TagKey<Block> VOID_SIGHT_BREAKS = net.minecraft.tags.BlockTags.create(new ResourceLocation("forged_entropy","void_sight_breaks"));

    }

    public static class EnchantmentTags {
        public static final TagKey<Enchantment> DO_NOT_ENCHANT_WITH=TagKey.create(Registries.ENCHANTMENT,new ResourceLocation("forged_entropy", "do_not_enchant_with"));
        public static final TagKey<Enchantment> DO_NOT_REMOVE=TagKey.create(Registries.ENCHANTMENT,new ResourceLocation("forged_entropy", "do_not_remove"));

    }

    public static class EntityTypeTags {
        public static final TagKey<EntityType<?>> DO_NOT_DAMAGE = TagKey.create(Registries.ENTITY_TYPE,new ResourceLocation("forged_entropy", "do_not_damage"));
        public static final TagKey<EntityType<?>> DO_NOT_EXPLODE = TagKey.create(Registries.ENTITY_TYPE,new ResourceLocation("forged_entropy", "do_not_explode"));
        public static final TagKey<EntityType<?>> DO_NOT_FLING = TagKey.create(Registries.ENTITY_TYPE,new ResourceLocation("forged_entropy", "do_not_fling"));
        public static final TagKey<EntityType<?>> DO_NOT_HIGHLIGHT = TagKey.create(Registries.ENTITY_TYPE,new ResourceLocation("forged_entropy", "do_not_highlight"));
        public static final TagKey<EntityType<?>> DO_NOT_IGNITE = TagKey.create(Registries.ENTITY_TYPE,new ResourceLocation("forged_entropy", "do_not_ignite"));
        public static final TagKey<EntityType<?>> DO_NOT_LEVITATE = TagKey.create(Registries.ENTITY_TYPE,new ResourceLocation("forged_entropy", "do_not_levitate"));
        public static final TagKey<EntityType<?>> IGNORED_BY_FORCEFIELD_AND_ENTITY_MAGNET = TagKey.create(Registries.ENTITY_TYPE,new ResourceLocation("forged_entropy", "ignored_by_forcefield_and_entity_magnet"));
        public static final TagKey<EntityType<?>> IGNORED_BY_MIDAS_TOUCH = TagKey.create(Registries.ENTITY_TYPE,new ResourceLocation("forged_entropy", "ignored_by_midas_touch"));
        public static final TagKey<EntityType<?>> NO_RAINBOW_TRAIL = TagKey.create(Registries.ENTITY_TYPE,new ResourceLocation("forged_entropy", "no_rainbow_trail"));
        public static final TagKey<EntityType<?>> NOT_INVISIBLE = TagKey.create(Registries.ENTITY_TYPE,new ResourceLocation("forged_entropy", "not_invisible"));
    }

    public class ItemTags {
        public static final TagKey<Item> BANNED = net.minecraft.tags.ItemTags.create(new ResourceLocation("forged_entropy","banned"));
        public static final TagKey<Item> DOES_NOT_DROP_RANDOMLY = net.minecraft.tags.ItemTags.create(new ResourceLocation("forged_entropy","does_not_drop_randomly"));
        public static final TagKey<Item> DOES_NOT_RAIN = net.minecraft.tags.ItemTags.create(new ResourceLocation("forged_entropy","does_not_rain"));
        public static final TagKey<Item> DO_NOT_CURSE = net.minecraft.tags.ItemTags.create(new ResourceLocation("forged_entropy","do_not_curse"));
        public static final TagKey<Item> DO_NOT_DAMAGE = net.minecraft.tags.ItemTags.create(new ResourceLocation("forged_entropy","do_not_damage"));
        public static final TagKey<Item> IGNORED_BY_MIDAS_TOUCH = net.minecraft.tags.ItemTags.create(new ResourceLocation("forged_entropy","ignored_by_midas_touch"));
        public static final TagKey<Item> MIDAS_TOUCH_GOLDEN_ITEMS = net.minecraft.tags.ItemTags.create(new ResourceLocation("forged_entropy","midas_touch_golden_items"));
        public static final TagKey<Item> UNFIXABLE = net.minecraft.tags.ItemTags.create(new ResourceLocation("forged_entropy","unfixable"));
    }
}
