package net.mavakcraft.datagenerator;

import java.util.concurrent.CompletableFuture;

import javax.annotation.Nonnull;

import org.jetbrains.annotations.Nullable;

import net.mavakcraft.Materials;
import net.mavakcraft.MavakCraft;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemTagProvider extends ItemTagsProvider {
	public ModItemTagProvider(
		PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
		CompletableFuture<TagsProvider.TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper
	) {
		super(output, lookupProvider, blockTags, MavakCraft.MODID, existingFileHelper);
	}

	@Override
	protected void addTags(@Nonnull Provider provider) {
		for (int dyeColorId = 0; dyeColorId < 16; dyeColorId++) {
			tag(dyeBlockTag(DyeColor.byId(dyeColorId)))
				.add(MavakCraft.DYE_BLOCKS[dyeColorId].get().asItem());
		}
		Materials.generateItemTags(this);
	}

	static TagKey<?>[] dyeItemTags = {
		Tags.Items.DYED_WHITE, Tags.Items.DYED_ORANGE, Tags.Items.DYED_MAGENTA, Tags.Items.DYED_LIGHT_BLUE, Tags.Items.DYED_YELLOW, Tags.Items.DYED_LIME,
		Tags.Items.DYED_PINK, Tags.Items.DYED_GRAY, Tags.Items.DYED_LIGHT_GRAY, Tags.Items.DYED_CYAN, Tags.Items.DYED_PURPLE, Tags.Items.DYED_BLUE,
		Tags.Items.DYED_BROWN, Tags.Items.DYED_GREEN, Tags.Items.DYED_RED, Tags.Items.DYED_BLACK
	};

	@SuppressWarnings("unchecked")
	public static TagKey<Item> dyeBlockTag(DyeColor dyeColor) {
		return (TagKey<Item>)dyeItemTags[dyeColor.getId()];
	}

	public IntrinsicHolderTagsProvider.IntrinsicTagAppender<Item> tag(@Nonnull TagKey<Item> tag) {
		return super.tag(tag);
	}

	public static TagKey<Item> createItemTag(String name) {
		return ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", name));
	}
}
