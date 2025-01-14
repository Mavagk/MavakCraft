package net.mavakcraft.datagenerator;

import java.util.concurrent.CompletableFuture;

import javax.annotation.Nonnull;

import org.jetbrains.annotations.Nullable;

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
			.add(MavakCraft.GLOWING_OBSIDIAN.get());
		tag(BlockTags.MINEABLE_WITH_HOE)
			.add(MavakCraft.NETHER_WART_BLOCK.get());
		tag(BlockTags.WOOL)
			.add(MavakCraft.FEATHER_BLOCK.get());
		tag(BlockTags.NEEDS_DIAMOND_TOOL)
			.add(MavakCraft.GLOWING_OBSIDIAN.get());
		Materials.generateBlockTags(this);
	}

	@Override
	public IntrinsicHolderTagsProvider.IntrinsicTagAppender<Block> tag(@Nonnull TagKey<Block> tag) {
		return super.tag(tag);
    }
}
