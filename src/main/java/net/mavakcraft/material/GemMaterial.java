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
	public DeferredBlock<Block> materialBlock;

	public DeferredItem<Item> gem;

	@Nonnull String name;
	@Nonnull String englishName;
	int xpMin;
	int xpMax;
	MapColor mapColor;

	public GemMaterial(@Nonnull String name, int xpMin, int xpMax, MapColor mapColor) {
		this.name = name;
		this.xpMin = xpMin;
		this.xpMax = xpMax;
		this.mapColor = mapColor;
		this.englishName = StringUtils.capitalize(name).replace('_', ' ');
	}

	@Override
	public void registerBlocks(ModBlocksDeferredRegister register) {
		ore = register.registerSimpleOre(name, xpMin, xpMax);
		deepslateOre = register.registerSimpleDeepslateOre(name, xpMin, xpMax);
		materialBlock = register.registerSimpleMaterialBlock(name, mapColor);
	}

	@Override
	public void registerItems(ModItemsDeferredRegister register) {
		gem = register.registerSimpleItem(name, CreativeModeTabs.INGREDIENTS);
	}

	@Override
	public void generateLoot(ModBlockLootProvider provider) {
		provider.dropSelf(materialBlock.get());
		provider.dropOreDrops(ore.get(), gem.get());
		provider.dropOreDrops(deepslateOre.get(), gem.get());
	}

	@Override
	public void generateBlockStates(ModBlockStateProvider provider) {
		provider.simpleBlockWithItem(ore.get());
		provider.simpleBlockWithItem(deepslateOre.get());
		provider.simpleBlockWithItem(materialBlock.get());
	}

	@Override
	public void generateItemModels(ModItemModelProvider provider) {
		provider.basicItem(gem.get());
	}

	@Override
	public void generateRecipes(ModRecipeProvider provider) {
		provider.recipesForItemStorageBlock(gem.get(), materialBlock.asItem());
		provider.oreSmeltingRecipe(ore.asItem(), gem.get(), 1, 200, RecipeCategory.MISC);
		provider.oreSmeltingRecipe(deepslateOre.asItem(), gem.get(), 1, 200, RecipeCategory.MISC);
	}

	@Override
	public void generateBlockTags(ModBlockTagProvider provider) {
		provider.tag(BlockTags.MINEABLE_WITH_PICKAXE)
			.add(ore.get())
			.add(deepslateOre.get())
			.add(materialBlock.get());
		provider.tag(BlockTags.NEEDS_IRON_TOOL)
			.add(ore.get())
			.add(deepslateOre.get())
			.add(materialBlock.get());
	}

	@Override
	public void generateEnglishName(ModEnglishLanguageProvider provider) {
		provider.add(ore.get(), englishName + " Ore");
		provider.add(deepslateOre.get(), "Deepslate " + englishName + " Ore");
		provider.add(materialBlock.get(), "Block of " + englishName);
		provider.add(gem.get(), englishName);
	}
}
