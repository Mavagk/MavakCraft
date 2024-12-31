package net.mavakcraft.datagenerator;

import java.util.concurrent.CompletableFuture;

import javax.annotation.Nonnull;

import net.mavakcraft.MavakCraft;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;

/**
 * Generates recipes such as crafting recipes.
 */
public class ModRecipeProvider extends RecipeProvider {
	public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	@Override
	protected void buildRecipes(@Nonnull RecipeOutput recipeOutput) {
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.RED_DYE)
			.requires(MavakCraft.ROSE_ITEM)
			.group("red_dye")
			.unlockedBy("has_rose", has(MavakCraft.ROSE_ITEM))
			.save(recipeOutput);
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.BLUE_DYE)
			.requires(MavakCraft.BLUE_ROSE_ITEM)
			.group("blue_dye")
			.unlockedBy("has_blue_rose", has(MavakCraft.BLUE_ROSE_ITEM))
			.save(recipeOutput);
		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, MavakCraft.CHARCOAL_BLOCK)
			.requires(Items.CHARCOAL, 9)
			.group("charcoal_block")
			.unlockedBy("has_charcoal", has(Items.CHARCOAL))
			.save(recipeOutput);
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.CHARCOAL, 9)
			.requires(MavakCraft.CHARCOAL_BLOCK)
			.group("charcoal")
			.unlockedBy("has_charcoal_block", has(MavakCraft.CHARCOAL_BLOCK))
			.save(recipeOutput);
	}
}
