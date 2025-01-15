package net.mavakcraft.material;

import java.util.List;
import java.util.Vector;

import javax.annotation.Nonnull;

import net.mavakcraft.worldgeneration.ModBiomeModifiers;
import net.mavakcraft.worldgeneration.ModConfiguredFeatures;
import net.mavakcraft.worldgeneration.ModPlacedFeatures;
import net.minecraft.core.HolderSet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.world.BiomeModifier;

public class OverworldOreGenMaterial extends Material {
	@Nonnull String name;
	//@Nonnull DeferredBlock<?> oreToGen;
	//@Nonnull DeferredBlock<?> deepslateOreToGen;
	@Nonnull OverworldOresMaterial ores;
	@Nonnull List<PlacementModifier> placementModifiers;
	int size;

	ResourceKey<ConfiguredFeature<?, ?>> orePlacedConfiguredFeature;
	ResourceKey<PlacedFeature> orePlacedPlacedFeature;
	ResourceKey<BiomeModifier> addOre;

	public OverworldOreGenMaterial(
		String name, OverworldOresMaterial ores, int size, int count, List<PlacementModifier> placementModifiers
	) {
		this.name = name;
		//this.oreToGen = oreToGen;
		//this.deepslateOreToGen = deepslateOreToGen;
		this.size = size;
		//Vector<PlacementModifier> placementModifiersVec = new Vector<>();
		//placementModifiers.forEach(modifier -> placementModifiersVec.add(modifier));
		//placementModifiersVec.addAll(List.of(InSquarePlacement.spread(), CountPlacement.of(count), BiomeFilter.biome()));
		//placementModifiersVec.add(InSquarePlacement.spread());
		//placementModifiersVec.add(CountPlacement.of(count));
		//placementModifiersVec.add(BiomeFilter.biome());
		this.placementModifiers = List.of(CountPlacement.of(100), InSquarePlacement.spread(),
		HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(480)), BiomeFilter.biome());//placementModifiersVec.stream().toList();
		this.ores = ores;
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
		HolderSet<Biome> biomes = ModBiomeModifiers.biomes.getOrThrow(Tags.Biomes.IS_OVERWORLD);
		ModBiomeModifiers.register(addOre, biomes, orePlacedPlacedFeature, GenerationStep.Decoration.UNDERGROUND_ORES);
	}
}
