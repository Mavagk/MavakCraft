package net.mavakcraft.datagenerator;

import java.util.concurrent.CompletableFuture;

import javax.annotation.Nonnull;

import org.jetbrains.annotations.Nullable;

import net.mavakcraft.Materials;
import net.mavakcraft.MavakCraft;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

/**
 * Generates tags for blocks, such as what tool is needed to mine them.
 */
public class ModBlockTagProvider extends BlockTagsProvider {
	public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, MavakCraft.MODID, existingFileHelper);
	}

	@Override
	protected void addTags(@Nonnull HolderLookup.Provider provider) {
		tag(BlockTags.MINEABLE_WITH_PICKAXE)
			.add(MavakCraft.GLOWING_OBSIDIAN.get());
		tag(BlockTags.MINEABLE_WITH_HOE)
			.add(MavakCraft.NETHER_WART_BLOCK.get());
		tag(BlockTags.WOOL)
			.add(MavakCraft.FEATHER_BLOCK.get());
		tag(BlockTags.NEEDS_DIAMOND_TOOL)
			.add(MavakCraft.GLOWING_OBSIDIAN.get());
		for (int dyeColorId = 0; dyeColorId < 16; dyeColorId++) {
			tag(dyeBlockTag(DyeColor.byId(dyeColorId)))
				.add(MavakCraft.DYE_BLOCKS[dyeColorId].get());
		}
		Materials.generateBlockTags(this);
	}

	static TagKey<?>[] dyeBlockTags = {
		Tags.Blocks.DYED_WHITE, Tags.Blocks.DYED_ORANGE, Tags.Blocks.DYED_MAGENTA, Tags.Blocks.DYED_LIGHT_BLUE, Tags.Blocks.DYED_YELLOW, Tags.Blocks.DYED_LIME,
		Tags.Blocks.DYED_PINK, Tags.Blocks.DYED_GRAY, Tags.Blocks.DYED_LIGHT_GRAY, Tags.Blocks.DYED_CYAN, Tags.Blocks.DYED_PURPLE, Tags.Blocks.DYED_BLUE,
		Tags.Blocks.DYED_BROWN, Tags.Blocks.DYED_GREEN, Tags.Blocks.DYED_RED, Tags.Blocks.DYED_BLACK
	};
	
	@SuppressWarnings("unchecked")
	public static TagKey<Block> dyeBlockTag(DyeColor dyeColor) {
		return (TagKey<Block>)dyeBlockTags[dyeColor.getId()];
	}

	@Override
	public IntrinsicHolderTagsProvider.IntrinsicTagAppender<Block> tag(@Nonnull TagKey<Block> tag) {
		return super.tag(tag);
	}

	public static TagKey<Block> createBlockTag(String name) {
		return BlockTags.create(ResourceLocation.fromNamespaceAndPath("c", name));
	}
}
