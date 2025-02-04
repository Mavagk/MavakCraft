package net.mavakcraft.material;

import java.util.List;

import javax.annotation.Nonnull;

import net.mavakcraft.worldgeneration.ModBiomeModifiers;
import net.mavakcraft.worldgeneration.ModConfiguredFeatures;
import net.mavakcraft.worldgeneration.ModPlacedFeatures;
import net.minecraft.core.HolderSet;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.minecraft.world.level.levelgen.GenerationStep;

public class SimpleBerryBushGenMaterial extends Material {
	@Nonnull String name;
	int size;
	int rarity;
	int tries;
	@Nonnull TagKey<Biome> biomes;
	@Nonnull BerryMaterial berryMaterial;

	ResourceKey<ConfiguredFeature<?, ?>> placedConfiguredFeature;
	ResourceKey<PlacedFeature> placedPlacedFeature;
	ResourceKey<BiomeModifier> addPlant;

	public SimpleBerryBushGenMaterial(
		String name, BerryMaterial berryMaterial, int size, int rarity, int tries, TagKey<Biome> biomes
	) {
		this.name = name;
		this.size = size;
		this.biomes = biomes;
		this.berryMaterial = berryMaterial;
		this.rarity = rarity;
		this.tries = tries;
	}

	@Override
	protected void onGenerateConfiguredFeatures() {
		placedConfiguredFeature = ModConfiguredFeatures.registerKey(name + "_placed");
		ModConfiguredFeatures.register(placedConfiguredFeature, Feature.FLOWER, FeatureUtils.simpleRandomPatchConfiguration(
		tries, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(
			SimpleWeightedRandomList.<BlockState>builder()
				.add(berryMaterial.bush.get().defaultBlockState(), 2)
			)))
		));
	}

	@Override
	protected void onGeneratePlacedFeatures() {
		placedPlacedFeature = ModPlacedFeatures.registerKey(name + "_placed");
		ModPlacedFeatures.register(
			placedPlacedFeature,
			placedConfiguredFeature,
			List.of(RarityFilter.onAverageOnceEvery(rarity), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome())
		);
	}

	@Override
	protected void onGenerateBiomeModifiers() {
		addPlant = ModBiomeModifiers.registerKey("add_" + name);
		HolderSet<Biome> biomeModifiers = ModBiomeModifiers.biomes.getOrThrow(this.biomes);
		ModBiomeModifiers.register(addPlant, biomeModifiers, placedPlacedFeature, GenerationStep.Decoration.VEGETAL_DECORATION);
	}
}
