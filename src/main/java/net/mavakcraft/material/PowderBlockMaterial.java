package net.mavakcraft.material;

import javax.annotation.Nonnull;

import net.mavakcraft.datagenerator.ModBlockLootProvider;
import net.mavakcraft.datagenerator.ModBlockStateProvider;
import net.mavakcraft.datagenerator.ModBlockTagProvider;
import net.mavakcraft.datagenerator.ModEnglishLanguageProvider;
import net.mavakcraft.registry.ModBlocksDeferredRegister;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ColoredFallingBlock;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredBlock;

public class PowderBlockMaterial extends Material {
	public DeferredBlock<ColoredFallingBlock> block;

	@Nonnull String name;
	int color;
	MapColor mapColor;
	int lightLevel;

	public PowderBlockMaterial(@Nonnull String name, int color, @Nonnull MapColor mapColor, int lightLevel) {
		this.name = name;
		this.mapColor = mapColor;
		this.color = color;
		this.lightLevel = lightLevel;
	}

	@Override
	public void onRegisterBlocks(ModBlocksDeferredRegister register) {
		block = register.registerSimpleFallingBlock(
			name + "_block", color,
			(props) -> props.mapColor(mapColor).lightLevel(state -> lightLevel), true, CreativeModeTabs.BUILDING_BLOCKS
		);
	}

	@Override
	public void onGenerateLoot(ModBlockLootProvider provider) {
		provider.dropSelf(block.get());
	}

	@Override
	public void onGenerateBlockStates(ModBlockStateProvider provider) {
		provider.simpleBlockWithItem(block.get());
	}

	@Override
	public void onGenerateEnglishNames(ModEnglishLanguageProvider provider) {
		String englishName = provider.idToTitle(name);
		provider.add(block.get(), "Block of " + englishName);
	}

	@Override
	protected void onGenerateBlockTags(ModBlockTagProvider provider) {
		TagKey<Block> materialStorageBlocks = createBlockTag("storage_blocks/" + name);
		provider.tag(Tags.Blocks.STORAGE_BLOCKS)
			.addTag(materialStorageBlocks);

		provider.tag(materialStorageBlocks)
			.add(block.get());
		provider.tag(BlockTags.MINEABLE_WITH_SHOVEL)
			.add(block.get());
	}
}
