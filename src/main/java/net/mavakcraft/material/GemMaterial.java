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

public class GemMaterial extends Material {
	public DeferredBlock<DropExperienceBlock> ore;
	public DeferredBlock<DropExperienceBlock> deepslateOre;
	@Nonnull public MaterialBlockMaterial materialBlock;

	public DeferredItem<Item> gem;

	@Nonnull String name;
	@Nonnull String englishName;
	int xpMin;
	int xpMax;
	@Nonnull MapColor mapColor;
	@Nonnull TagKey<Block> toolNeeded;

	public GemMaterial(@Nonnull String name, int xpMin, int xpMax, @Nonnull MapColor mapColor, TagKey<Block> toolNeeded) {
		this.name = name;
		this.xpMin = xpMin;
		this.xpMax = xpMax;
		this.mapColor = mapColor;
		this.englishName = StringUtils.capitalize(name).replace('_', ' ');
		this.toolNeeded = toolNeeded;
		materialBlock = addSubMaterial(new MaterialBlockMaterial(name, mapColor, toolNeeded));
	}

	@Override
	public void onRegisterBlocks(ModBlocksDeferredRegister register) {
		ore = register.registerSimpleOre(name, xpMin, xpMax);
		deepslateOre = register.registerSimpleDeepslateOre(name, xpMin, xpMax);
	}

	@Override
	public void onRegisterItems(ModItemsDeferredRegister register) {
		gem = register.registerSimpleItem(name, CreativeModeTabs.INGREDIENTS);
	}

	@Override
	public void onGenerateLoot(ModBlockLootProvider provider) {
		provider.dropOreDrops(ore.get(), gem.get());
		provider.dropOreDrops(deepslateOre.get(), gem.get());
	}

	@Override
	public void onGenerateBlockStates(ModBlockStateProvider provider) {
		provider.simpleBlockWithItem(ore.get());
		provider.simpleBlockWithItem(deepslateOre.get());
	}

	@Override
	public void onGenerateItemModels(ModItemModelProvider provider) {
		provider.basicItem(gem.get());
	}

	@Override
	public void onGenerateRecipes(ModRecipeProvider provider) {
		provider.recipesForItemStorageBlock(gem.get(), materialBlock.block.asItem());
		provider.oreSmeltingRecipe(ore.asItem(), gem.get(), 1, 200, RecipeCategory.MISC);
		provider.oreSmeltingRecipe(deepslateOre.asItem(), gem.get(), 1, 200, RecipeCategory.MISC);
	}

	@Override
	public void onGenerateBlockTags(ModBlockTagProvider provider) {
		provider.tag(BlockTags.MINEABLE_WITH_PICKAXE)
			.add(ore.get())
			.add(deepslateOre.get());
		provider.tag(toolNeeded)
			.add(ore.get())
			.add(deepslateOre.get());
	}

	@Override
	public void onGenerateEnglishNames(ModEnglishLanguageProvider provider) {
		provider.add(ore.get(), englishName + " Ore");
		provider.add(deepslateOre.get(), "Deepslate " + englishName + " Ore");
		provider.add(gem.get(), englishName);
	}
}
