package net.mavakcraft.material;

import java.util.List;
import javax.annotation.Nonnull;

import net.mavakcraft.worldgeneration.ModBiomeModifiers;
import net.mavakcraft.worldgeneration.ModConfiguredFeatures;
import net.mavakcraft.worldgeneration.ModPlacedFeatures;
import net.minecraft.core.HolderSet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.neoforged.neoforge.common.world.BiomeModifier;

public class OverworldOreGenMaterial extends Material {
	@Nonnull String name;
	@Nonnull OverworldOresMaterial ores;
	@Nonnull List<PlacementModifier> placementModifiers;
	int size;
	@Nonnull TagKey<Biome> biomes;

	ResourceKey<ConfiguredFeature<?, ?>> orePlacedConfiguredFeature;
	ResourceKey<PlacedFeature> orePlacedPlacedFeature;
	ResourceKey<BiomeModifier> addOre;

	public OverworldOreGenMaterial(
		String name, OverworldOresMaterial ores, int size, List<PlacementModifier> placementModifiers, TagKey<Biome> biomes
	) {
		this.name = name;
		this.size = size;
		this.placementModifiers = placementModifiers;
		this.ores = ores;
		this.biomes = biomes;
	}

	@Override
	protected void onGenerateConfiguredFeatures() {
		orePlacedConfiguredFeature = ModConfiguredFeatures.registerKey(name + "_ore_placed");
		ModConfiguredFeatures.registerSimpleOverworldOre(orePlacedConfiguredFeature, ores.ore.get(), ores.deepslateOre.get(), size);
	}

	@Override
	protected void onGeneratePlacedFeatures() {
		orePlacedPlacedFeature = ModPlacedFeatures.registerKey(name + "_ore_placed");
		ModPlacedFeatures.register(orePlacedPlacedFeature, orePlacedConfiguredFeature, placementModifiers);
	}

	@Override
	protected void onGenerateBiomeModifiers() {
		addOre = ModBiomeModifiers.registerKey("add_" + name + "_ore");
		HolderSet<Biome> biomes = ModBiomeModifiers.biomes.getOrThrow(this.biomes);
		ModBiomeModifiers.register(addOre, biomes, orePlacedPlacedFeature, GenerationStep.Decoration.UNDERGROUND_ORES);
	}
}
