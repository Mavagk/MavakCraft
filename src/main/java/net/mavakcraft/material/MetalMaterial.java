package net.mavakcraft.material;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.StringUtils;

import net.mavakcraft.datagenerator.ModBlockLootProvider;
import net.mavakcraft.datagenerator.ModBlockStateProvider;
import net.mavakcraft.datagenerator.ModBlockTagProvider;
import net.mavakcraft.datagenerator.ModEnglishLanguageProvider;
import net.mavakcraft.datagenerator.ModItemModelProvider;
import net.mavakcraft.datagenerator.ModRecipeProvider;
import net.mavakcraft.registry.ModBlocksDeferredRegister;
import net.mavakcraft.registry.ModItemsDeferredRegister;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

public class MetalMaterial extends Material {
	public DeferredBlock<DropExperienceBlock> ore;
	public DeferredBlock<DropExperienceBlock> deepslateOre;
	public DeferredBlock<Block> rawBlock;
	@Nonnull public MaterialBlockMaterial materialBlock;

	public DeferredItem<Item> rawMetal;
	public DeferredItem<Item> ingot;
	public DeferredItem<Item> nugget;

	@Nonnull String name;
	@Nonnull String englishName;
	@Nonnull MapColor mapColor;
	@Nonnull MapColor rawColor;
	@Nonnull TagKey<Block> toolNeeded;

	public MetalMaterial(@Nonnull String name, @Nonnull MapColor mapColor, @Nonnull MapColor rawColor, TagKey<Block> toolNeeded) {
		this.name = name;
		this.mapColor = mapColor;
		this.rawColor = rawColor;
		this.toolNeeded = toolNeeded;
		this.englishName = StringUtils.capitalize(name).replace('_', ' ');
		materialBlock = addSubMaterial(new MaterialBlockMaterial(name, mapColor, toolNeeded));
	}

	@Override
	public void onRegisterBlocks(ModBlocksDeferredRegister register) {
		ore = register.registerSimpleOre(name, 0, 0);
		deepslateOre = register.registerSimpleDeepslateOre(name, 0, 0);
		rawBlock = register.registerSimpleRawBlock(name, rawColor);
	}

	@Override
	public void onRegisterItems(ModItemsDeferredRegister register) {
		ingot = register.registerSimpleItem(name + "_ingot", CreativeModeTabs.INGREDIENTS);
		rawMetal = register.registerSimpleItem("raw_" + name, CreativeModeTabs.INGREDIENTS);
		nugget = register.registerSimpleItem(name + "_nugget", CreativeModeTabs.INGREDIENTS);
	}

	@Override
	public void onGenerateLoot(ModBlockLootProvider provider) {
		provider.dropSelf(rawBlock.get());
		provider.dropOreDrops(ore.get(), rawMetal.get());
		provider.dropOreDrops(deepslateOre.get(), rawMetal.get());
	}

	@Override
	public void onGenerateBlockStates(ModBlockStateProvider provider) {
		provider.simpleBlockWithItem(ore.get());
		provider.simpleBlockWithItem(deepslateOre.get());
		provider.simpleBlockWithItem(rawBlock.get());
	}

	@Override
	public void onGenerateItemModels(ModItemModelProvider provider) {
		provider.basicItem(ingot.get());
		provider.basicItem(nugget.get());
		provider.basicItem(rawMetal.get());
	}

	@Override
	public void onGenerateRecipes(ModRecipeProvider provider) {
		provider.recipesForItemStorageBlock(ingot.get(), materialBlock.block.asItem());
		provider.recipesForItemStorageBlock(rawMetal.get(), rawBlock.asItem());
		provider.recipesForNugget(nugget.get(), ingot.get());
		provider.oreSmeltingRecipe(ore.asItem(), ingot.get(), 1, 200, RecipeCategory.MISC);
		provider.oreSmeltingRecipe(deepslateOre.asItem(), ingot.get(), 1, 200, RecipeCategory.MISC);
		provider.oreSmeltingRecipe(rawMetal.asItem(), ingot.get(), 1, 200, RecipeCategory.MISC);
	}

	@Override
	public void onGenerateBlockTags(ModBlockTagProvider provider) {
		provider.tag(BlockTags.MINEABLE_WITH_PICKAXE)
			.add(ore.get())
			.add(deepslateOre.get())
			.add(rawBlock.get());
		provider.tag(toolNeeded)
			.add(ore.get())
			.add(deepslateOre.get())
			.add(rawBlock.get());
	}

	@Override
	public void onGenerateEnglishNames(ModEnglishLanguageProvider provider) {
		provider.add(ore.get(), englishName + " Ore");
		provider.add(deepslateOre.get(), "Deepslate " + englishName + " Ore");
		provider.add(rawBlock.get(), "Block of Raw " + englishName);
		provider.add(ingot.get(), englishName + " Ingot");
		provider.add(rawMetal.get(), "Raw " + englishName);
		provider.add(nugget.get(), englishName + " Nugget");
	}
}
