package net.mavakcraft.material;

import javax.annotation.Nonnull;

import net.mavakcraft.MavakCraft;
import net.mavakcraft.datagenerator.ModBlockLootProvider;
import net.mavakcraft.datagenerator.ModBlockStateProvider;
import net.mavakcraft.datagenerator.ModBlockTagProvider;
import net.mavakcraft.datagenerator.ModEnglishLanguageProvider;
import net.mavakcraft.registry.ModBlocksDeferredRegister;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;

public class WoodLikeItemBlockMaterial extends Material {
	public DeferredBlock<Block> block;

	@Nonnull String name;
	@Nonnull String englishName;
	MapColor mapColor;
	int lightLevel;

	public WoodLikeItemBlockMaterial(@Nonnull String name, @Nonnull MapColor mapColor, int lightLevel) {
		this.name = name;
		this.mapColor = mapColor;
		this.englishName = MavakCraft.idToTitle(name);
		this.lightLevel = lightLevel;
	}

	@Override
	public void onRegisterBlocks(ModBlocksDeferredRegister register) {
		block = register.registerSimpleBlock(name + "_block", (props) -> props
			.instrument(NoteBlockInstrument.BASS)
			.strength(2.0F, 3.0F)
			.ignitedByLava()
			.requiresCorrectToolForDrops()
			.lightLevel(blockState -> lightLevel),
		true, CreativeModeTabs.BUILDING_BLOCKS);
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
		provider.add(block.get(), "Block of " + englishName);
	}

	@Override
	protected void onGenerateBlockTags(ModBlockTagProvider provider) {
		provider.tag(BlockTags.MINEABLE_WITH_PICKAXE)
			.add(block.get());
	}
}
