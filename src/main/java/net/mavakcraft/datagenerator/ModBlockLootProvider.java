package net.mavakcraft.datagenerator;

import java.util.Set;

import javax.annotation.Nonnull;

import net.mavakcraft.Materials;
import net.mavakcraft.MavakCraft;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
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
		dropSelf(MavakCraft.NETHER_WART_BLOCK.get());
		dropSelf(MavakCraft.FEATHER_BLOCK.get());
		dropSelf(MavakCraft.BYTE_BLOCK.get());
		dropSelf(MavakCraft.PRIMARY_INPUT_BYTE_BLOCK.get());
		dropSelf(MavakCraft.SECONDARY_INPUT_BYTE_BLOCK.get());
		dropSelf(MavakCraft.WRAPPING_ADD_SUB_BYTE_BLOCK.get());
		dropSelf(MavakCraft.WRAPPING_MULT_DIV_BYTE_BLOCK.get());
		for (int dyeColorId = 0; dyeColorId < 16; dyeColorId++) {
			dropSelf(MavakCraft.DYE_BLOCKS[dyeColorId].get());
		}
		Materials.generateLoot(this);
	}

	@Override
	public void dropSelf(@Nonnull Block block) {
		super.dropSelf(block);
	}

	public void dropOreDrops(@Nonnull Block block, @Nonnull Item item) {
		this.add(block, super.createOreDrop(block, item));
	}

	@Override
	public Iterable<Block> getKnownBlocks() {
		return MavakCraft.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
	}
}
