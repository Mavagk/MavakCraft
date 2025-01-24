package net.mavakcraft.material;

import javax.annotation.Nonnull;

import net.mavakcraft.datagenerator.ModBlockStateProvider;
import net.mavakcraft.datagenerator.ModBlockTagProvider;
import net.mavakcraft.datagenerator.ModEnglishLanguageProvider;
import net.mavakcraft.datagenerator.ModItemTagProvider;
import net.mavakcraft.registry.ModBlocksDeferredRegister;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredBlock;

public class OverworldOresMaterial extends Material {
	public DeferredBlock<DropExperienceBlock> ore;
	public DeferredBlock<DropExperienceBlock> deepslateOre;

	@Nonnull String name;
	int xpMin;
	int xpMax;
	@Nonnull TagKey<Block> toolNeeded;

	public OverworldOresMaterial(@Nonnull String name, int xpMin, int xpMax, TagKey<Block> toolNeeded) {
		this.name = name;
		this.toolNeeded = toolNeeded;
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
		provider.blockWithTextureWithItem(ore.get(), name + "/ore");
		provider.blockWithTextureWithItem(deepslateOre.get(), name + "/deepslate_ore");
	}

	@Override
	protected void onGenerateBlockTags(ModBlockTagProvider provider) {
		TagKey<Block> oreBlocks = ModBlockTagProvider.createBlockTag("ores/" + name);
		provider.tag(Tags.Blocks.ORES)
			.addTag(oreBlocks);

		provider.tag(oreBlocks)
			.add(ore.get())
			.add(deepslateOre.get());
		provider.tag(BlockTags.MINEABLE_WITH_PICKAXE)
			.add(ore.get())
			.add(deepslateOre.get());
		provider.tag(BlockTags.OVERWORLD_CARVER_REPLACEABLES)
			.add(ore.get())
			.add(deepslateOre.get());
		provider.tag(toolNeeded)
			.add(ore.get())
			.add(deepslateOre.get());
		provider.tag(Tags.Blocks.ORES_IN_GROUND_STONE)
			.add(ore.get());
		provider.tag(Tags.Blocks.ORES_IN_GROUND_DEEPSLATE)
			.add(deepslateOre.get());
	}

	@Override
	protected void onGenerateItemTags(ModItemTagProvider provider) {
		TagKey<Item> oreBlocks = ModItemTagProvider.createItemTag("ores/" + name);
		provider.tag(Tags.Items.ORES)
			.addTag(oreBlocks);

		provider.tag(oreBlocks)
			.add(ore.asItem())
			.add(deepslateOre.asItem());
		provider.tag(Tags.Items.ORES_IN_GROUND_STONE)
			.add(ore.asItem());
		provider.tag(Tags.Items.ORES_IN_GROUND_DEEPSLATE)
			.add(deepslateOre.asItem());
	}

	@Override
	protected void onGenerateEnglishNames(ModEnglishLanguageProvider provider) {
		String englishName = provider.idToTitle(name);
		provider.add(ore.get(), englishName + " Ore");
		provider.add(deepslateOre.get(), "Deepslate " + englishName + " Ore");
	}
}
