package net.mavakcraft.datagenerator;

import java.util.Set;

import javax.annotation.Nonnull;

import net.mavakcraft.Blocks;
import net.mavakcraft.Materials;
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
		dropSelf(Blocks.GLOWING_OBSIDIAN.get());
		dropSelf(Blocks.CHARCOAL_BLOCK.get());
		dropSelf(Blocks.FLINT_BLOCK.get());
		dropSelf(Blocks.TURTLE_SCUTE_BLOCK.get());
		dropSelf(Blocks.NETHERITE_SCRAP_BLOCK.get());
		dropSelf(Blocks.NETHER_STAR_BLOCK.get());
		dropSelf(Blocks.LEATHER_BLOCK.get());
		dropSelf(Blocks.RABBIT_HIDE_BLOCK.get());
		dropSelf(Blocks.NETHER_WART_BLOCK.get());
		dropSelf(Blocks.FEATHER_BLOCK.get());
		dropSelf(Blocks.ROSE.get());
		dropSelf(Blocks.BLUE_ROSE.get());
		dropSelf(Blocks.GLOWSTONE_DUST_BLOCK.get());
		dropSelf(Blocks.BLAZE_POWDER_BLOCK.get());
		dropSelf(Blocks.GUNPOWDER_BLOCK.get());
		dropSelf(Blocks.SUGAR_BLOCK.get());
		dropSelf(Blocks.SALT_BLOCK.get());
		dropSelf(Blocks.BYTE_BLOCK.get());
		dropSelf(Blocks.PRIMARY_INPUT_BYTE_BLOCK.get());
		dropSelf(Blocks.SECONDARY_INPUT_BYTE_BLOCK.get());
		dropSelf(Blocks.WRAPPING_ADD_SUB_BYTE_BLOCK.get());
		dropSelf(Blocks.WRAPPING_MULT_DIV_BYTE_BLOCK.get());
		for (int dyeColorId = 0; dyeColorId < 16; dyeColorId++) {
			dropSelf(Blocks.DYE_BLOCKS[dyeColorId].get());
		}
		Materials.generateDrops(this);
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
		return Blocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
	}
}
