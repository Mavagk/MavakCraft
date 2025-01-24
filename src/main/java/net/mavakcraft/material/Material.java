package net.mavakcraft.material;

import java.util.ArrayList;

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

public abstract class Material {
	private final @Nonnull ArrayList<Material> subMaterials = new ArrayList<>();

	public final void registerBlocks(ModBlocksDeferredRegister register) {
		onRegisterBlocks(register);
		subMaterials.forEach(material -> material.registerBlocks(register));
	}

	public final void registerItems(ModItemsDeferredRegister register) {
		onRegisterItems(register);
		subMaterials.forEach(material -> material.registerItems(register));
	}

	public final void generateLoot(ModBlockLootProvider provider) {
		onGenerateLoot(provider);
		subMaterials.forEach(material -> material.generateLoot(provider));
	}

	public final void generateBlockStates(ModBlockStateProvider provider) {
		onGenerateBlockStates(provider);
		subMaterials.forEach(material -> material.generateBlockStates(provider));
	}

	public final void generateItemModels(ModItemModelProvider provider) {
		onGenerateItemModels(provider);
		subMaterials.forEach(material -> material.generateItemModels(provider));
	}

	public final void generateRecipes(ModRecipeProvider provider) {
		onGenerateRecipes(provider);
		subMaterials.forEach(material -> material.generateRecipes(provider));
	}

	public final void generateBlockTags(ModBlockTagProvider provider) {
		onGenerateBlockTags(provider);
		subMaterials.forEach(material -> material.generateBlockTags(provider));
	}

	public final void generateItemTags(ModItemTagProvider provider) {
		onGenerateItemTags(provider);
		subMaterials.forEach(material -> material.generateItemTags(provider));
	}

	public final void generateEnglishNames(ModEnglishLanguageProvider provider) {
		onGenerateEnglishNames(provider);
		subMaterials.forEach(material -> material.generateEnglishNames(provider));
	}

	public final void generateConfiguredFeatures() {
		onGenerateConfiguredFeatures();
		subMaterials.forEach(material -> material.generateConfiguredFeatures());
	}

	public final void generatePlacedFeatures() {
		onGeneratePlacedFeatures();
		subMaterials.forEach(material -> material.generatePlacedFeatures());
	}

	public final void generateBiomeModifiers() {
		onGenerateBiomeModifiers();
		subMaterials.forEach(material -> material.generateBiomeModifiers());
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

	protected void onGenerateItemTags(ModItemTagProvider provider) {

	}

	protected void onGenerateEnglishNames(ModEnglishLanguageProvider provider) {
		
	}

	protected void onGenerateConfiguredFeatures() {
		
	}

	protected void onGeneratePlacedFeatures() {
		
	}

	protected void onGenerateBiomeModifiers() {
		
	}

	protected <T extends Material> T addSubMaterial(T material) {
		subMaterials.add(material);
		return material;
	}
}
