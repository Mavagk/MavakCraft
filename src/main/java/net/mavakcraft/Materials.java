package net.mavakcraft;

import java.util.List;
import java.util.Vector;

import net.mavakcraft.datagenerator.ModBlockLootProvider;
import net.mavakcraft.datagenerator.ModBlockStateProvider;
import net.mavakcraft.datagenerator.ModBlockTagProvider;
import net.mavakcraft.datagenerator.ModEnglishLanguageProvider;
import net.mavakcraft.datagenerator.ModItemModelProvider;
import net.mavakcraft.datagenerator.ModRecipeProvider;
import net.mavakcraft.material.GemMaterial;
import net.mavakcraft.material.Material;
import net.mavakcraft.material.OverworldOreGenMaterial;
import net.mavakcraft.material.PureMetalMaterial;
import net.mavakcraft.material.PowderBlockMaterial;
import net.mavakcraft.material.RockItemBlockMaterial;
import net.mavakcraft.material.SimpleFlowerMaterial;
import net.mavakcraft.material.WoodLikeItemBlockMaterial;
import net.mavakcraft.registry.ModBlocksDeferredRegister;
import net.mavakcraft.registry.ModItemsDeferredRegister;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.material.MapColor;

public class Materials {
	public static Vector<Material> MATERIALS = new Vector<>();

	public static <T extends Material> T register(T material) {
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
		MATERIALS.forEach(material -> material.generateEnglishNames(provider));
	}

	public static void generateConfiguredFeatures() {
		MATERIALS.forEach(material -> material.generateConfiguredFeatures());
	}

	public static void generatePlacedFeatures() {
		MATERIALS.forEach(material -> material.generatePlacedFeatures());
	}

	public static void generateBiomeModifiers() {
		MATERIALS.forEach(material -> material.generateBiomeModifiers());
	}

	// Gems
	public static GemMaterial RUBY = register(new GemMaterial("ruby", 3, 7, MapColor.COLOR_RED, BlockTags.NEEDS_IRON_TOOL));
	public static GemMaterial SAPPHIRE = register(new GemMaterial("sapphire", 3, 7, MapColor.COLOR_BLUE, BlockTags.NEEDS_IRON_TOOL));
	public static GemMaterial TOPAZ = register(new GemMaterial("topaz", 3, 7, MapColor.COLOR_BROWN, BlockTags.NEEDS_IRON_TOOL));

	// Pure metals
	public static PureMetalMaterial TIN = register(new PureMetalMaterial("tin", MapColor.METAL, MapColor.METAL, BlockTags.NEEDS_STONE_TOOL));
	public static PureMetalMaterial ALUMINUM = register(new PureMetalMaterial("aluminum", MapColor.METAL, MapColor.METAL, BlockTags.NEEDS_STONE_TOOL));

	// Ore gens
	public static OverworldOreGenMaterial RUBY_GEN = register(new OverworldOreGenMaterial(
		"ruby", RUBY.ores, 5, 100,
		List.of(
			CountPlacement.of(100), InSquarePlacement.spread(),
			HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(480)), BiomeFilter.biome()
		)
	));

	// Simple flowers
	public static SimpleFlowerMaterial ROSE = register(new SimpleFlowerMaterial("rose", MobEffects.NIGHT_VISION, 5));
	public static SimpleFlowerMaterial BLUE_ROSE = register(new SimpleFlowerMaterial("blue_rose", MobEffects.NIGHT_VISION, 5));

	// Powder item blocks
	public static PowderBlockMaterial GLOWSTONE_DUST_BLOCK = register(new PowderBlockMaterial("glowstone_dust", 0xB7966E, MapColor.SAND, 15));
	public static PowderBlockMaterial BLAZE_POWDER_BLOCK = register(new PowderBlockMaterial("blaze_powder", 0xFFA300, MapColor.COLOR_ORANGE, 0));
	public static PowderBlockMaterial GUNPOWDER_BLOCK = register(new PowderBlockMaterial("gunpowder", 0x545454, MapColor.STONE, 0));
	public static PowderBlockMaterial SUGAR_BLOCK = register(new PowderBlockMaterial("sugar", 0xEAEAEA, MapColor.SAND, 0));
	public static PowderBlockMaterial SALT_BLOCK = register(new PowderBlockMaterial("salt", 0xEAEAEA, MapColor.SAND, 0));

	// Item blocks
	public static RockItemBlockMaterial CHARCOAL_BLOCK = register(new RockItemBlockMaterial("charcoal", MapColor.COLOR_BLACK, 0));
	public static RockItemBlockMaterial FLINT_BLOCK = register(new RockItemBlockMaterial("flint", MapColor.COLOR_BLACK, 0));
	public static RockItemBlockMaterial TURTLE_SCUTE_BLOCK = register(new RockItemBlockMaterial("turtle_scute", MapColor.GRASS, 0));
	public static RockItemBlockMaterial NETHERITE_SCRAP_BLOCK = register(new RockItemBlockMaterial("netherite_scrap", MapColor.COLOR_BLACK, 0));
	public static RockItemBlockMaterial NETHER_STAR_BLOCK = register(new RockItemBlockMaterial("nether_star", MapColor.COLOR_YELLOW, 0));
	public static WoodLikeItemBlockMaterial LEATHER_BLOCK = register(new WoodLikeItemBlockMaterial("leather", MapColor.DIRT, 0));
	public static WoodLikeItemBlockMaterial RABBIT_HIDE_BLOCK = register(new WoodLikeItemBlockMaterial("rabbit_hide", MapColor.DIRT, 0));
}
