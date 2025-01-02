package net.mavakcraft.datagenerator;

import java.util.Set;

import net.mavakcraft.MavakCraft;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

/**
 * Generates loot tables for blocks.
 */
public class ModBlockLootProvider extends BlockLootSubProvider {
	public ModBlockLootProvider(HolderLookup.Provider registries) {
		super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
	}

	@Override
	protected void generate() {
		dropSelf(MavakCraft.GLOWING_OBSIDIAN.get());
		dropSelf(MavakCraft.CHARCOAL_BLOCK.get());
		dropSelf(MavakCraft.ROSE.get());
		dropSelf(MavakCraft.BLUE_ROSE.get());
		dropSelf(MavakCraft.GLOWSTONE_DUST_BLOCK.get());
		dropSelf(MavakCraft.GUNPOWDER_BLOCK.get());
		dropSelf(MavakCraft.SUGAR_BLOCK.get());
		dropSelf(MavakCraft.SALT_BLOCK.get());
		dropSelf(MavakCraft.BYTE_BLOCK.get());
	}

	@Override
	protected Iterable<Block> getKnownBlocks() {
		return MavakCraft.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
	}
}
