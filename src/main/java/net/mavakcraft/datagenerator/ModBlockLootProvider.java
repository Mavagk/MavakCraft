package net.mavakcraft.datagenerator;

import java.util.Set;

import javax.annotation.Nonnull;

import net.mavakcraft.Materials;
import net.mavakcraft.MavakCraft;
import net.mavakcraft.block.BerryBushBlock;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

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

	public void dropBerryBushDrops(@Nonnull BerryBushBlock bushBlock) {
		HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
		add(bushBlock, block -> this.applyExplosionDecay(
			block,
			LootTable.lootTable()
				.withPool(LootPool.lootPool()
					.when(
						LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
							.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BerryBushBlock.AGE, 3))
					)
					.add(LootItem.lootTableItem(block.asItem()))
					.apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F)))
					.apply(ApplyBonusCount.addUniformBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))
				)
				.withPool(LootPool.lootPool()
					.when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
						.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BerryBushBlock.AGE, 2))
					)
					.add(LootItem.lootTableItem(block.asItem()))
					.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
					.apply(ApplyBonusCount.addUniformBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))
				)
		));
	}

	public void dropOreDrops(@Nonnull Block block, @Nonnull Item item) {
		this.add(block, super.createOreDrop(block, item));
	}

	@Override
	public Iterable<Block> getKnownBlocks() {
		return MavakCraft.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
	}
}
