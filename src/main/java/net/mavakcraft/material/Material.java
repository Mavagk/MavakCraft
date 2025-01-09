package net.mavakcraft.material;

import net.mavakcraft.datagenerator.ModBlockLootProvider;
import net.mavakcraft.datagenerator.ModBlockStateProvider;
import net.mavakcraft.datagenerator.ModBlockTagProvider;
import net.mavakcraft.datagenerator.ModEnglishLanguageProvider;
import net.mavakcraft.datagenerator.ModItemModelProvider;
import net.mavakcraft.datagenerator.ModRecipeProvider;
import net.mavakcraft.registry.ModBlocksDeferredRegister;
import net.mavakcraft.registry.ModItemsDeferredRegister;

public abstract class Material {
	public abstract void registerBlocks(ModBlocksDeferredRegister register);
	public abstract void registerItems(ModItemsDeferredRegister register);
	public abstract void generateLoot(ModBlockLootProvider provider);
	public abstract void generateBlockStates(ModBlockStateProvider provider);
	public abstract void generateItemModels(ModItemModelProvider provider);
	public abstract void generateRecipes(ModRecipeProvider provider);
	public abstract void generateBlockTags(ModBlockTagProvider provider);
	public abstract void generateEnglishName(ModEnglishLanguageProvider provider);
}
