package net.mavakcraft.datagenerator;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.Nullable;

import net.mavakcraft.MavakCraft;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockTagProvider extends BlockTagsProvider {
	public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, MavakCraft.MODID, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {
		tag(BlockTags.MINEABLE_WITH_PICKAXE)
			.add(MavakCraft.GLOWING_OBSIDIAN.get());
		tag(BlockTags.NEEDS_DIAMOND_TOOL)
			.add(MavakCraft.GLOWING_OBSIDIAN.get());
		tag(BlockTags.SMALL_FLOWERS)
			.add(MavakCraft.ROSE.get())
			.add(MavakCraft.BLUE_ROSE.get());
	}
}
