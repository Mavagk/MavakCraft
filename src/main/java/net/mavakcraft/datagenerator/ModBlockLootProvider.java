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
		dropSelf(MavakCraft.FLINT_BLOCK.get());
		dropSelf(MavakCraft.TURTLE_SCUTE_BLOCK.get());
		dropSelf(MavakCraft.NETHERITE_SCRAP_BLOCK.get());
		dropSelf(MavakCraft.NETHER_STAR_BLOCK.get());
		dropSelf(MavakCraft.LEATHER_BLOCK.get());
		dropSelf(MavakCraft.RABBIT_HIDE_BLOCK.get());
		dropSelf(MavakCraft.NETHER_WART_BLOCK.get());
		dropSelf(MavakCraft.FEATHER_BLOCK.get());
		dropSelf(MavakCraft.ROSE.get());
		dropSelf(MavakCraft.BLUE_ROSE.get());
		dropSelf(MavakCraft.GLOWSTONE_DUST_BLOCK.get());
		dropSelf(MavakCraft.BLAZE_POWDER_BLOCK.get());
		dropSelf(MavakCraft.GUNPOWDER_BLOCK.get());
		dropSelf(MavakCraft.SUGAR_BLOCK.get());
		dropSelf(MavakCraft.SALT_BLOCK.get());
		dropSelf(MavakCraft.BYTE_BLOCK.get());
		dropSelf(MavakCraft.PRIMARY_INPUT_BYTE_BLOCK.get());
		dropSelf(MavakCraft.SECONDARY_INPUT_BYTE_BLOCK.get());
		dropSelf(MavakCraft.WRAPPING_ADD_SUB_BYTE_BLOCK.get());
		dropSelf(MavakCraft.WRAPPING_MULT_DIV_BYTE_BLOCK.get());
		for (int dyeColorId = 0; dyeColorId < 16; dyeColorId++) {
			dropSelf(MavakCraft.DYE_BLOCKS[dyeColorId].get());
		}
	}

	@Override
	protected Iterable<Block> getKnownBlocks() {
		return MavakCraft.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
	}
}
