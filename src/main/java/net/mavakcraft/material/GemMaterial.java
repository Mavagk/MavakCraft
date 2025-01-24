package net.mavakcraft.material;

import javax.annotation.Nonnull;

import net.mavakcraft.datagenerator.ModBlockLootProvider;
import net.mavakcraft.datagenerator.ModEnglishLanguageProvider;
import net.mavakcraft.datagenerator.ModItemModelProvider;
import net.mavakcraft.datagenerator.ModItemTagProvider;
import net.mavakcraft.datagenerator.ModRecipeProvider;
import net.mavakcraft.registry.ModItemsDeferredRegister;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredItem;

public class GemMaterial extends Material {
	@Nonnull public MaterialBlockMaterial materialBlock;
	@Nonnull public OverworldOresMaterial ores;

	public DeferredItem<Item> gem;

	@Nonnull String name;

	public GemMaterial(@Nonnull String name, int xpMin, int xpMax, @Nonnull MapColor mapColor, TagKey<Block> toolNeeded) {
		this.name = name;
		materialBlock = addSubMaterial(new MaterialBlockMaterial(name, mapColor, toolNeeded));
		ores = addSubMaterial(new OverworldOresMaterial(name, 0, 0, toolNeeded));
	}

	@Override
	public void onRegisterItems(ModItemsDeferredRegister register) {
		gem = register.registerSimpleItem(name, CreativeModeTabs.INGREDIENTS);
	}

	@Override
	public void onGenerateLoot(ModBlockLootProvider provider) {
		provider.dropOreDrops(ores.ore.get(), gem.get());
		provider.dropOreDrops(ores.deepslateOre.get(), gem.get());
	}

	@Override
	public void onGenerateItemModels(ModItemModelProvider provider) {
		provider.basicItem(gem.get());
	}

	@Override
	public void onGenerateRecipes(ModRecipeProvider provider) {
		provider.recipesForItemStorageBlock(gem.get(), materialBlock.block.asItem());
		provider.oreSmeltingRecipe(ores.ore.asItem(), gem.get(), 1, 200, RecipeCategory.MISC);
		provider.oreSmeltingRecipe(ores.deepslateOre.asItem(), gem.get(), 1, 200, RecipeCategory.MISC);
	}

	@Override
	public void onGenerateEnglishNames(ModEnglishLanguageProvider provider) {
		String englishName = provider.idToTitle(name);
		provider.add(gem.get(), englishName);
	}

	@Override
	protected void onGenerateItemTags(ModItemTagProvider provider) {
		TagKey<Item> gemTag = ModItemTagProvider.createItemTag("gems/" + name);
		provider.tag(Tags.Items.GEMS)
			.addTag(gemTag);

		provider.tag(gemTag)
			.add(gem.get());
	}
}
