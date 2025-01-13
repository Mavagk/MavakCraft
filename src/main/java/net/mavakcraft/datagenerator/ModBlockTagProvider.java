package net.mavakcraft.datagenerator;

import java.util.concurrent.CompletableFuture;

import javax.annotation.Nonnull;

import org.jetbrains.annotations.Nullable;

import net.mavakcraft.Blocks;
import net.mavakcraft.Materials;
import net.mavakcraft.MavakCraft;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
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
			.add(Blocks.GLOWING_OBSIDIAN.get())
			.add(Blocks.CHARCOAL_BLOCK.get())
			.add(Blocks.FLINT_BLOCK.get())
			.add(Blocks.TURTLE_SCUTE_BLOCK.get())
			.add(Blocks.NETHERITE_SCRAP_BLOCK.get())
			.add(Blocks.NETHER_STAR_BLOCK.get());
		tag(BlockTags.MINEABLE_WITH_AXE)
			.add(Blocks.LEATHER_BLOCK.get())
			.add(Blocks.RABBIT_HIDE_BLOCK.get());
		tag(BlockTags.MINEABLE_WITH_HOE)
			.add(Blocks.NETHER_WART_BLOCK.get());
		tag(BlockTags.WOOL)
			.add(Blocks.FEATHER_BLOCK.get());
		tag(BlockTags.NEEDS_DIAMOND_TOOL)
			.add(Blocks.GLOWING_OBSIDIAN.get());
		tag(BlockTags.SMALL_FLOWERS)
			.add(Blocks.ROSE.get())
			.add(Blocks.BLUE_ROSE.get());
		Materials.generateBlockTags(this);
	}

	@Override
	public IntrinsicHolderTagsProvider.IntrinsicTagAppender<Block> tag(@Nonnull TagKey<Block> tag) {
		return super.tag(tag);
    }
}
