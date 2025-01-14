package net.mavakcraft.datagenerator;

import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;

import net.mavakcraft.Materials;
import net.mavakcraft.MavakCraft;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

/**
 * Generates recipes such as crafting recipes.
 */
public class ModRecipeProvider extends RecipeProvider {
	RecipeOutput recipeOutput;

	public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	@Override
	protected void buildRecipes(@Nonnull RecipeOutput recipeOutput) {
		this.recipeOutput = recipeOutput;
		shapelessRecipeForItemToItem(Materials.ROSE.flower.get().asItem(), Items.RED_DYE, RecipeCategory.MISC);
		shapelessRecipeForItemToItem(Materials.BLUE_ROSE.flower.get().asItem(), Items.BLUE_DYE, RecipeCategory.MISC);
		recipesForItemStorageBlock(Items.CHARCOAL, Materials.CHARCOAL_BLOCK.block.get().asItem());
		recipesForItemStorageBlock(Items.FLINT, Materials.FLINT_BLOCK.block.get().asItem());
		recipesForItemStorageBlock(Items.TURTLE_SCUTE, Materials.TURTLE_SCUTE_BLOCK.block.get().asItem());
		recipesForItemStorageBlock(Items.NETHERITE_SCRAP, Materials.NETHERITE_SCRAP_BLOCK.block.get().asItem());
		recipesForItemStorageBlock(Items.NETHER_STAR, Materials.NETHER_STAR_BLOCK.block.get().asItem());
		recipesForItemStorageBlock(Items.LEATHER, Materials.LEATHER_BLOCK.block.get().asItem());
		recipesForItemStorageBlock(Items.RABBIT_HIDE, Materials.RABBIT_HIDE_BLOCK.block.get().asItem());
		recipesForItemStorageBlock(Items.FEATHER, MavakCraft.FEATHER_BLOCK.get().asItem());
		recipesForItemStorageBlock(Items.NETHER_WART, MavakCraft.NETHER_WART_BLOCK.get().asItem());
		recipesForItemStorageBlock(Items.GLOWSTONE_DUST, Materials.GLOWSTONE_DUST_BLOCK.block.get().asItem());
		recipesForItemStorageBlock(Items.GUNPOWDER, Materials.GUNPOWDER_BLOCK.block.get().asItem());
		recipesForItemStorageBlock(Items.BLAZE_POWDER, Materials.BLAZE_POWDER_BLOCK.block.get().asItem());
		recipesForItemStorageBlock(Items.SUGAR, Materials.SUGAR_BLOCK.block.get().asItem());
		recipesForItemStorageBlock(MavakCraft.SALT.get(), Materials.SALT_BLOCK.block.get().asItem());
		for (int dyeColorId = 0; dyeColorId < 16; dyeColorId++) {
			recipesForItemStorageBlock(DyeItem.byColor(DyeColor.byId(dyeColorId)), MavakCraft.DYE_BLOCKS[dyeColorId].get().asItem());
		}
		Materials.generateRecipes(this);
	}
	
	public void oreSmeltingRecipe(Item from, Item to, float experience, int cookingTime, RecipeCategory category) {
		String fromName = BuiltInRegistries.ITEM.getKey(from).getPath();
		String toName = BuiltInRegistries.ITEM.getKey(to).getPath();
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(from), category, to, experience, cookingTime)
			.group(toName)
			.unlockedBy(getHasName(from), has(from))
			.save(recipeOutput, toName + "_smelted_from_" + fromName);
		SimpleCookingRecipeBuilder.blasting(Ingredient.of(from), category, to, experience, cookingTime / 2)
			.group(toName)
			.unlockedBy(getHasName(from), has(from))
			.save(recipeOutput, toName + "_blasted_from_" + fromName);
	}

	/**
	 * Build a shapeless crafting recipe that crafts an amount of an item into an amount of another item.
	 * @param category The tab the recipe should be placed into in the recipe book.
	 */
	public void shapelessRecipeForItemToItem(Item from, int fromCount, Item to, int toCount, RecipeCategory category) {
		ShapelessRecipeBuilder.shapeless(category, to, toCount)
			.requires(from, fromCount)
			.group(BuiltInRegistries.ITEM.getKey(to).getPath())
			.unlockedBy("has_" + BuiltInRegistries.ITEM.getKey(from).getPath(), has(from))
			.save(recipeOutput, BuiltInRegistries.ITEM.getKey(to).getPath() + "_from_" + BuiltInRegistries.ITEM.getKey(from).getPath());
	}

	/**
	 * Build a shapeless crafting recipe that crafts an amount of an item into another single item.
	 * @param category The tab the recipe should be placed into in the recipe book.
	 */
	public void shapelessRecipeForItemToItem(Item from, int fromCount, Item to, RecipeCategory category) {
		shapelessRecipeForItemToItem(from, fromCount, to, 1, category);
	}

	/**
	 * Build a shapeless crafting recipe that crafts a single item into an amount of another item.
	 * @param category The tab the recipe should be placed into in the recipe book.
	 */
	public void shapelessRecipeForItemToItem(Item from, Item to, int toCount, RecipeCategory category) {
		shapelessRecipeForItemToItem(from, 1, to, toCount, category);
	}

	/**
	 * Build a shapeless crafting recipe that crafts a single item into another single item.
	 * @param category The tab the recipe should be placed into in the recipe book.
	 */
	public void shapelessRecipeForItemToItem(Item from, Item to, RecipeCategory category) {
		shapelessRecipeForItemToItem(from, 1, to, 1, category);
	}

	/**
	 * Build a recipes for crafting an 9 of an item into it's storage block and vice versa.
	 * @param storageOf The item we craft 9 of into the block and that the block can be crafted back into.
	 * @param storageBlock The block we craft and can uncraft.
	 */
	public void recipesForItemStorageBlock(Item storageOf, Item storageBlock) {
		shapelessRecipeForItemToItem(storageOf, 9, storageBlock, RecipeCategory.BUILDING_BLOCKS);
		shapelessRecipeForItemToItem(storageBlock, storageOf, 9, RecipeCategory.MISC);
	}

	/**
	 * Build a recipes for crafting an 9 of a nugget into it's ingot and vice versa.
	 * @param nugget The nugget we craft 9 of into the ingot and that the ingot can be crafted back into.
	 * @param ingot The ingot we craft and can uncraft.
	 */
	public void recipesForNugget(Item nugget, Item ingot) {
		shapelessRecipeForItemToItem(nugget, 9, ingot, RecipeCategory.MISC);
		shapelessRecipeForItemToItem(ingot, nugget, 9, RecipeCategory.MISC);
	}
}
