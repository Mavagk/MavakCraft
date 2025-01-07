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
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
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
		shapelessRecipeForItemToItem(MavakCraft.ROSE.get().asItem(), Items.RED_DYE, RecipeCategory.MISC);
		shapelessRecipeForItemToItem(MavakCraft.BLUE_ROSE.get().asItem(), Items.BLUE_DYE, RecipeCategory.MISC);
		recipesForItemStorageBlock(Items.CHARCOAL, MavakCraft.CHARCOAL_BLOCK.get().asItem());
		recipesForItemStorageBlock(Items.FLINT, MavakCraft.FLINT_BLOCK.get().asItem());
		recipesForItemStorageBlock(Items.TURTLE_SCUTE, MavakCraft.TURTLE_SCUTE_BLOCK.get().asItem());
		recipesForItemStorageBlock(Items.NETHERITE_SCRAP, MavakCraft.NETHERITE_SCRAP_BLOCK.get().asItem());
		recipesForItemStorageBlock(Items.NETHER_STAR, MavakCraft.NETHER_STAR_BLOCK.get().asItem());
		recipesForItemStorageBlock(Items.LEATHER, MavakCraft.LEATHER_BLOCK.get().asItem());
		recipesForItemStorageBlock(Items.RABBIT_HIDE, MavakCraft.RABBIT_HIDE_BLOCK.get().asItem());
		recipesForItemStorageBlock(Items.FEATHER, MavakCraft.FEATHER_BLOCK.get().asItem());
		recipesForItemStorageBlock(Items.NETHER_WART, MavakCraft.NETHER_WART_BLOCK.get().asItem());
		recipesForItemStorageBlock(Items.GLOWSTONE_DUST, MavakCraft.GLOWSTONE_DUST_BLOCK.get().asItem());
		recipesForItemStorageBlock(Items.GUNPOWDER, MavakCraft.GUNPOWDER_BLOCK.get().asItem());
		recipesForItemStorageBlock(Items.BLAZE_POWDER, MavakCraft.BLAZE_POWDER_BLOCK.get().asItem());
		recipesForItemStorageBlock(Items.SUGAR, MavakCraft.SUGAR_BLOCK.get().asItem());
		recipesForItemStorageBlock(MavakCraft.SALT.get(), MavakCraft.SALT_BLOCK.get().asItem());
		for (int dyeColorId = 0; dyeColorId < 16; dyeColorId++) {
			recipesForItemStorageBlock(DyeItem.byColor(DyeColor.byId(dyeColorId)), MavakCraft.DYE_BLOCKS[dyeColorId].get().asItem());
		}
	}

	/**
	 * Build a shapeless crafting recipe that crafts an amount of an item into an amount of another item.
	 * @param category The tab the recipe should be placed into in the recipe book.
	 */
	void shapelessRecipeForItemToItem(Item from, int fromCount, Item to, int toCount, RecipeCategory category) {
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
	void shapelessRecipeForItemToItem(Item from, int fromCount, Item to, RecipeCategory category) {
		shapelessRecipeForItemToItem(from, fromCount, to, 1, category);
	}

	/**
	 * Build a shapeless crafting recipe that crafts a single item into an amount of another item.
	 * @param category The tab the recipe should be placed into in the recipe book.
	 */
	void shapelessRecipeForItemToItem(Item from, Item to, int toCount, RecipeCategory category) {
		shapelessRecipeForItemToItem(from, 1, to, toCount, category);
	}

	/**
	 * Build a shapeless crafting recipe that crafts a single item into another single item.
	 * @param category The tab the recipe should be placed into in the recipe book.
	 */
	void shapelessRecipeForItemToItem(Item from, Item to, RecipeCategory category) {
		shapelessRecipeForItemToItem(from, 1, to, 1, category);
	}

	/**
	 * Build a recipes for crafting an 9 of an item into it's storage block and vice versa.
	 * @param storage_of The item we craft 9 of into the block and that the block can be crafted back into.
	 * @param storage_block The block we craft and can uncraft.
	 */
	void recipesForItemStorageBlock(Item storage_of, Item storage_block) {
		shapelessRecipeForItemToItem(storage_of, 9, storage_block, RecipeCategory.BUILDING_BLOCKS);
		shapelessRecipeForItemToItem(storage_block, storage_of, 9, RecipeCategory.MISC);
	}
}
