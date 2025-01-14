package net.mavakcraft.material;

import javax.annotation.Nonnull;

import net.mavakcraft.MavakCraft;
import net.mavakcraft.datagenerator.ModBlockStateProvider;
import net.mavakcraft.datagenerator.ModBlockTagProvider;
import net.mavakcraft.datagenerator.ModEnglishLanguageProvider;
import net.mavakcraft.registry.ModBlocksDeferredRegister;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.neoforged.neoforge.registries.DeferredBlock;

public class OverworldOresMaterial extends Material {
	public DeferredBlock<DropExperienceBlock> ore;
	public DeferredBlock<DropExperienceBlock> deepslateOre;

	@Nonnull String name;
	@Nonnull String englishName;
	int xpMin;
	int xpMax;
	@Nonnull TagKey<Block> toolNeeded;

	public OverworldOresMaterial(@Nonnull String name, int xpMin, int xpMax, TagKey<Block> toolNeeded) {
		this.name = name;
		this.toolNeeded = toolNeeded;
		this.englishName = MavakCraft.idToTitle(name);
		this.xpMin = xpMin;
		this.xpMax = xpMax;
	}

	@Override
	protected void onRegisterBlocks(ModBlocksDeferredRegister register) {
		ore = register.registerSimpleOre(name, 0, 0);
		deepslateOre = register.registerSimpleDeepslateOre(name, 0, 0);
	}

	@Override
	protected void onGenerateBlockStates(ModBlockStateProvider provider) {
		provider.simpleBlockWithItem(ore.get());
		provider.simpleBlockWithItem(deepslateOre.get());
	}

	@Override
	protected void onGenerateBlockTags(ModBlockTagProvider provider) {
		provider.tag(BlockTags.MINEABLE_WITH_PICKAXE)
			.add(ore.get())
			.add(deepslateOre.get());
		provider.tag(toolNeeded)
			.add(ore.get())
			.add(deepslateOre.get());
	}

	@Override
	protected void onGenerateEnglishNames(ModEnglishLanguageProvider provider) {
		provider.add(ore.get(), englishName + " Ore");
		provider.add(deepslateOre.get(), "Deepslate " + englishName + " Ore");
	}
}
