package net.mavakcraft.datagenerator;

import java.util.Set;

import net.mavakcraft.MavakCraft;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

public class BlockLootProvider extends BlockLootSubProvider {
	protected BlockLootProvider(HolderLookup.Provider registries) {
		super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
	}

	@Override
	protected void generate() {
		throw new UnsupportedOperationException("Unimplemented method 'generate'");
	}

	@Override
	protected Iterable<Block> getKnownBlocks() {
		return MavakCraft.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
	}
}
