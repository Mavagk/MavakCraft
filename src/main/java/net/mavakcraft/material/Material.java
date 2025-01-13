package net.mavakcraft.material;

import java.util.Vector;

import javax.annotation.Nonnull;

import net.mavakcraft.datagenerator.ModBlockLootProvider;
import net.mavakcraft.datagenerator.ModBlockStateProvider;
import net.mavakcraft.datagenerator.ModBlockTagProvider;
import net.mavakcraft.datagenerator.ModEnglishLanguageProvider;
import net.mavakcraft.datagenerator.ModItemModelProvider;
import net.mavakcraft.datagenerator.ModRecipeProvider;
import net.mavakcraft.registry.ModBlocksDeferredRegister;
import net.mavakcraft.registry.ModItemsDeferredRegister;

public abstract class Material {
	private @Nonnull Vector<Material> subMaterials = new Vector<>();

	public final void registerBlocks(ModBlocksDeferredRegister register) {
		onRegisterBlocks(register);
		subMaterials.forEach(material -> material.onRegisterBlocks(register));
	}

	public final void registerItems(ModItemsDeferredRegister register) {
		onRegisterItems(register);
		subMaterials.forEach(material -> material.onRegisterItems(register));
	}

	public final void generateLoot(ModBlockLootProvider provider) {
		onGenerateLoot(provider);
		subMaterials.forEach(material -> material.onGenerateLoot(provider));
	}

	public final void generateBlockStates(ModBlockStateProvider provider) {
		onGenerateBlockStates(provider);
		subMaterials.forEach(material -> material.onGenerateBlockStates(provider));
	}

	public final void generateItemModels(ModItemModelProvider provider) {
		onGenerateItemModels(provider);
		subMaterials.forEach(material -> material.onGenerateItemModels(provider));
	}

	public final void generateRecipes(ModRecipeProvider provider) {
		onGenerateRecipes(provider);
		subMaterials.forEach(material -> material.onGenerateRecipes(provider));
	}

	public final void generateBlockTags(ModBlockTagProvider provider) {
		onGenerateBlockTags(provider);
		subMaterials.forEach(material -> material.onGenerateBlockTags(provider));
	}

	public final void generateEnglishNames(ModEnglishLanguageProvider provider) {
		onGenerateEnglishNames(provider);
		subMaterials.forEach(material -> material.onGenerateEnglishNames(provider));
	}

	protected void onRegisterBlocks(ModBlocksDeferredRegister register) {

	}

	protected void onRegisterItems(ModItemsDeferredRegister register) {

	}

	protected void onGenerateLoot(ModBlockLootProvider provider) {

	}

	protected void onGenerateBlockStates(ModBlockStateProvider provider) {

	}

	protected void onGenerateItemModels(ModItemModelProvider provider) {

	}

	protected void onGenerateRecipes(ModRecipeProvider provider) {

	}

	protected void onGenerateBlockTags(ModBlockTagProvider provider) {

	}

	protected void onGenerateEnglishNames(ModEnglishLanguageProvider provider) {
		
	}

	protected <T extends Material> T addSubMaterial(T material) {
		subMaterials.add(material);
		return material;
	}
}
