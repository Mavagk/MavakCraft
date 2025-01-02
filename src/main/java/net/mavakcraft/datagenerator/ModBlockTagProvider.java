package net.mavakcraft.datagenerator;

import java.util.concurrent.CompletableFuture;

import javax.annotation.Nonnull;

import org.jetbrains.annotations.Nullable;

import net.mavakcraft.MavakCraft;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
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
			.add(MavakCraft.GLOWING_OBSIDIAN.get())
			.add(MavakCraft.CHARCOAL_BLOCK.get());
		tag(BlockTags.MINEABLE_WITH_SHOVEL)
			.add(MavakCraft.GLOWSTONE_DUST_BLOCK.get())
			.add(MavakCraft.GUNPOWDER_BLOCK.get())
			.add(MavakCraft.SUGAR_BLOCK.get())
			.add(MavakCraft.SALT_BLOCK.get());
		tag(BlockTags.NEEDS_DIAMOND_TOOL)
			.add(MavakCraft.GLOWING_OBSIDIAN.get());
		tag(BlockTags.SMALL_FLOWERS)
			.add(MavakCraft.ROSE.get())
			.add(MavakCraft.BLUE_ROSE.get());
	}
}
