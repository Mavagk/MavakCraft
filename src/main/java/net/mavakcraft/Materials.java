package net.mavakcraft;

import java.util.Vector;

import net.mavakcraft.datagenerator.ModBlockLootProvider;
import net.mavakcraft.datagenerator.ModBlockStateProvider;
import net.mavakcraft.datagenerator.ModBlockTagProvider;
import net.mavakcraft.datagenerator.ModEnglishLanguageProvider;
import net.mavakcraft.datagenerator.ModItemModelProvider;
import net.mavakcraft.datagenerator.ModRecipeProvider;
import net.mavakcraft.material.GemMaterial;
import net.mavakcraft.material.Material;
import net.mavakcraft.registry.ModBlocksDeferredRegister;
import net.mavakcraft.registry.ModItemsDeferredRegister;
import net.minecraft.world.level.material.MapColor;

public class Materials {
	public static Vector<Material> MATERIALS = new Vector<>();

	public static Material register(Material material) {
		MATERIALS.add(material);
		return material;
	}

	public static void registerBlocks(ModBlocksDeferredRegister register) {
		MATERIALS.forEach(material -> material.registerBlocks(register));
	}

	public static void registerItems(ModItemsDeferredRegister register) {
		MATERIALS.forEach(material -> material.registerItems(register));
	}

	public static void generateLoot(ModBlockLootProvider provider) {
		MATERIALS.forEach(material -> material.generateLoot(provider));
	}

	public static void generateBlockStates(ModBlockStateProvider provider) {
		MATERIALS.forEach(material -> material.generateBlockStates(provider));
	}

	public static void generateItemModels(ModItemModelProvider provider) {
		MATERIALS.forEach(material -> material.generateItemModels(provider));
	}

	public static void generateRecipes(ModRecipeProvider provider) {
		MATERIALS.forEach(material -> material.generateRecipes(provider));
	}

	public static void generateBlockTags(ModBlockTagProvider provider) {
		MATERIALS.forEach(material -> material.generateBlockTags(provider));
	}

	public static void generateEnglishName(ModEnglishLanguageProvider provider) {
		MATERIALS.forEach(material -> material.generateEnglishName(provider));
	}

	public static Material RUBY = register(new GemMaterial("ruby", 3, 7, MapColor.COLOR_RED));
}
