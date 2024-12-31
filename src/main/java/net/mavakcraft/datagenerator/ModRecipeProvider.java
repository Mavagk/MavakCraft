package net.mavakcraft.datagenerator;

import java.util.concurrent.CompletableFuture;

import javax.annotation.Nonnull;

import net.mavakcraft.MavakCraft;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

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
		buildRecipesForItemToItem(MavakCraft.ROSE_ITEM.get(), Items.RED_DYE, RecipeCategory.MISC);
		buildRecipesForItemToItem(MavakCraft.BLUE_ROSE_ITEM.get(), Items.BLUE_DYE, RecipeCategory.MISC);
		buildRecipesForItemBlock(Items.CHARCOAL, MavakCraft.CHARCOAL_BLOCK_ITEM.get());
	}

	/**
	 * Build a shapeless crafting recipe that crafts an amount of an item into an amount of another item.
	 * @param category The tab the recipe should be placed into in the recipe book.
	 */
	void buildRecipesForItemToItem(Item from, int fromCount, Item to, int toCount, RecipeCategory category) {
		ShapelessRecipeBuilder.shapeless(category, to, toCount)
			.requires(from, fromCount)
			.group(BuiltInRegistries.ITEM.getKey(to).getPath())
			.unlockedBy("has_" + BuiltInRegistries.ITEM.getKey(from).getPath(), has(from))
			.save(recipeOutput);
	}

	/**
	 * Build a shapeless crafting recipe that crafts an amount of an item into another single item.
	 * @param category The tab the recipe should be placed into in the recipe book.
	 */
	void buildRecipesForItemToItem(Item from, int fromCount, Item to, RecipeCategory category) {
		buildRecipesForItemToItem(from, fromCount, to, 1, category);
	}

	/**
	 * Build a shapeless crafting recipe that crafts a single item into an amount of another item.
	 * @param category The tab the recipe should be placed into in the recipe book.
	 */
	void buildRecipesForItemToItem(Item from, Item to, int toCount, RecipeCategory category) {
		buildRecipesForItemToItem(from, 1, to, toCount, category);
	}

	/**
	 * Build a shapeless crafting recipe that crafts a single item into another single item.
	 * @param category The tab the recipe should be placed into in the recipe book.
	 */
	void buildRecipesForItemToItem(Item from, Item to, RecipeCategory category) {
		buildRecipesForItemToItem(from, 1, to, 1, category);
	}

	/**
	 * Build a recipes for crafting an 9 of an item into it's storage block and vice versa.
	 * @param storage_of The item we craft 9 of into the block and that the block can be crafted back into.
	 * @param storage_block The block we craft and can uncraft.
	 */
	void buildRecipesForItemBlock(Item storage_of, Item storage_block) {
		buildRecipesForItemToItem(storage_of, 9, storage_block, RecipeCategory.BUILDING_BLOCKS);
		buildRecipesForItemToItem(storage_block, storage_of, 9, RecipeCategory.MISC);
	}
}
