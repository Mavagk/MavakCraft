package net.mavakcraft.material;

import javax.annotation.Nonnull;
import net.mavakcraft.datagenerator.ModBlockLootProvider;
import net.mavakcraft.datagenerator.ModBlockStateProvider;
import net.mavakcraft.datagenerator.ModBlockTagProvider;
import net.mavakcraft.datagenerator.ModEnglishLanguageProvider;
import net.mavakcraft.datagenerator.ModItemModelProvider;
import net.mavakcraft.datagenerator.ModItemTagProvider;
import net.mavakcraft.datagenerator.ModRecipeProvider;
import net.mavakcraft.registry.ModBlocksDeferredRegister;
import net.mavakcraft.registry.ModItemsDeferredRegister;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

public class PureMetalMaterial extends Material {
	public DeferredBlock<Block> rawBlock;
	@Nonnull public MaterialBlockMaterial materialBlock;
	@Nonnull public OverworldOresMaterial ores;

	public DeferredItem<Item> rawMetal;
	public DeferredItem<Item> ingot;
	public DeferredItem<Item> nugget;

	@Nonnull String name;
	@Nonnull MapColor rawColor;
	@Nonnull TagKey<Block> toolNeeded;

	public PureMetalMaterial(@Nonnull String name, @Nonnull MapColor mapColor, @Nonnull MapColor rawColor, TagKey<Block> toolNeeded) {
		this.name = name;
		this.rawColor = rawColor;
		this.toolNeeded = toolNeeded;
		materialBlock = addSubMaterial(new MaterialBlockMaterial(name, mapColor, toolNeeded));
		ores = addSubMaterial(new OverworldOresMaterial(name, 0, 0, toolNeeded));
	}

	@Override
	public void onRegisterBlocks(ModBlocksDeferredRegister register) {
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
		provider.dropOreDrops(ores.ore.get(), rawMetal.get());
		provider.dropOreDrops(ores.deepslateOre.get(), rawMetal.get());
	}

	@Override
	public void onGenerateBlockStates(ModBlockStateProvider provider) {
		provider.blockWithTextureWithItem(rawBlock.get(), name + "/raw_block");
	}

	@Override
	public void onGenerateItemModels(ModItemModelProvider provider) {
		provider.itemWithPath(ingot.get(), name + "/ingot");
		provider.itemWithPath(nugget.get(), name + "/nugget");
		provider.itemWithPath(rawMetal.get(), name + "/raw");
	}

	@Override
	public void onGenerateRecipes(ModRecipeProvider provider) {
		provider.recipesForItemStorageBlock(ingot.get(), materialBlock.block.asItem());
		provider.recipesForItemStorageBlock(rawMetal.get(), rawBlock.asItem());
		provider.recipesForNugget(nugget.get(), ingot.get());
		provider.oreSmeltingRecipe(ores.ore.asItem(), ingot.get(), 1, 200, RecipeCategory.MISC);
		provider.oreSmeltingRecipe(ores.deepslateOre.asItem(), ingot.get(), 1, 200, RecipeCategory.MISC);
		provider.oreSmeltingRecipe(rawMetal.asItem(), ingot.get(), 1, 200, RecipeCategory.MISC);
	}

	@Override
	public void onGenerateBlockTags(ModBlockTagProvider provider) {
		TagKey<Block> materialStorageBlocks = ModBlockTagProvider.createBlockTag("storage_blocks/raw_" + name);
		provider.tag(Tags.Blocks.STORAGE_BLOCKS)
			.addTag(materialStorageBlocks);

		provider.tag(materialStorageBlocks)
			.add(rawBlock.get());
		provider.tag(BlockTags.MINEABLE_WITH_PICKAXE)
			.add(rawBlock.get());
		provider.tag(toolNeeded)
			.add(rawBlock.get());
	}

	@Override
	protected void onGenerateItemTags(ModItemTagProvider provider) {
		TagKey<Item> ingotTag = ModItemTagProvider.createItemTag("ingots/" + name);
		provider.tag(Tags.Items.INGOTS)
			.addTag(ingotTag);
		provider.tag(ingotTag)
			.add(ingot.get());

		TagKey<Item> nuggetTag = ModItemTagProvider.createItemTag("nuggets/" + name);
		provider.tag(Tags.Items.NUGGETS)
			.addTag(nuggetTag);
		provider.tag(nuggetTag)
			.add(nugget.get());

		TagKey<Item> rawMaterialTag = ModItemTagProvider.createItemTag("raw_materials/" + name);
		provider.tag(Tags.Items.RAW_MATERIALS)
			.addTag(rawMaterialTag);
		provider.tag(rawMaterialTag)
			.add(rawMetal.get());
	}

	@Override
	public void onGenerateEnglishNames(ModEnglishLanguageProvider provider) {
		String englishName = provider.idToTitle(name);
		provider.add(rawBlock.get(), "Block of Raw " + englishName);
		provider.add(ingot.get(), englishName + " Ingot");
		provider.add(rawMetal.get(), "Raw " + englishName);
		provider.add(nugget.get(), englishName + " Nugget");
	}
}
