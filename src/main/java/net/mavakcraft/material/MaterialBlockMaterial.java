package net.mavakcraft.material;

import javax.annotation.Nonnull;

import net.mavakcraft.datagenerator.ModBlockLootProvider;
import net.mavakcraft.datagenerator.ModBlockStateProvider;
import net.mavakcraft.datagenerator.ModBlockTagProvider;
import net.mavakcraft.datagenerator.ModEnglishLanguageProvider;
import net.mavakcraft.datagenerator.ModItemTagProvider;
import net.mavakcraft.registry.ModBlocksDeferredRegister;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredBlock;

public class MaterialBlockMaterial extends Material {
	public DeferredBlock<Block> block;

	@Nonnull String name;
	@Nonnull MapColor mapColor;
	@Nonnull TagKey<Block> toolNeeded;

	public MaterialBlockMaterial(@Nonnull String name, @Nonnull MapColor mapColor, TagKey<Block> toolNeeded) {
		this.name = name;
		this.mapColor = mapColor;
		this.toolNeeded = toolNeeded;
	}

	@Override
	public void onRegisterBlocks(ModBlocksDeferredRegister register) {
		block = register.registerSimpleMaterialBlock(name, mapColor);
	}

	@Override
	public void onGenerateLoot(ModBlockLootProvider provider) {
		provider.dropSelf(block.get());
	}

	@Override
	public void onGenerateBlockStates(ModBlockStateProvider provider) {
		provider.blockWithTextureWithItem(block.get(), name + "/block");
	}

	@Override
	public void onGenerateBlockTags(ModBlockTagProvider provider) {
		TagKey<Block> materialStorageBlocks = ModBlockTagProvider.createBlockTag("storage_blocks/" + name);
		provider.tag(Tags.Blocks.STORAGE_BLOCKS)
			.addTag(materialStorageBlocks);

		provider.tag(materialStorageBlocks)
			.add(block.get());
		provider.tag(BlockTags.MINEABLE_WITH_PICKAXE)
			.add(block.get());
		provider.tag(toolNeeded)
			.add(block.get());
	}

	@Override
	public void onGenerateItemTags(ModItemTagProvider provider) {
		TagKey<Item> materialStorageBlocks = ModItemTagProvider.createItemTag("storage_blocks/" + name);
		provider.tag(Tags.Items.STORAGE_BLOCKS)
			.addTag(materialStorageBlocks);

		provider.tag(materialStorageBlocks)
			.add(block.asItem());
	}

	@Override
	public void onGenerateEnglishNames(ModEnglishLanguageProvider provider) {
		String englishName = provider.idToTitle(name);
		provider.add(block.get(), "Block of " + englishName);
	}
}
