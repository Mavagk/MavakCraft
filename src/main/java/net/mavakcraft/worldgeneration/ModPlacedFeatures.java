package net.mavakcraft.worldgeneration;

import java.util.List;

import net.mavakcraft.Materials;
import net.mavakcraft.MavakCraft;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

/**
 * Sets where features should be placed in the biomes it can generate in.
 */
public class ModPlacedFeatures {
	static BootstrapContext<PlacedFeature> context;
	static HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures;

	public static final ResourceKey<PlacedFeature> ROSES_PLACED = registerKey("roses_placed");
	
	public static final ResourceKey<PlacedFeature> SAPPHIRE_ORE_PLACED = registerKey("sapphire_ore_placed");
	public static final ResourceKey<PlacedFeature> TOPAZ_ORE_PLACED = registerKey("topaz_ore_placed");

	public static final ResourceKey<PlacedFeature> TIN_ORE_PLACED = registerKey("tin_ore_placed");
	public static final ResourceKey<PlacedFeature> ALUMINUM_ORE_PLACED = registerKey("aluminum_ore_placed");

	public static void bootstrap(BootstrapContext<PlacedFeature> contextIn) {
		context = contextIn;
		configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

		register(
			ROSES_PLACED,
			ModConfiguredFeatures.ROSES_PLACED,
			List.of(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome())
		);
		register(
			SAPPHIRE_ORE_PLACED,
			ModConfiguredFeatures.SAPPHIRE_ORE_PLACED,
			List.of(
				CountPlacement.of(5), InSquarePlacement.spread(),
				HeightRangePlacement.uniform(VerticalAnchor.absolute(-63), VerticalAnchor.absolute(30)), BiomeFilter.biome()
			)
		);
		register(
			TOPAZ_ORE_PLACED,
			ModConfiguredFeatures.TOPAZ_ORE_PLACED,
			List.of(
				CountPlacement.of(30), InSquarePlacement.spread(),
				HeightRangePlacement.uniform(VerticalAnchor.absolute(-63), VerticalAnchor.absolute(20)), BiomeFilter.biome()
			)
		);

		register(
			TIN_ORE_PLACED,
			ModConfiguredFeatures.TIN_ORE_PLACED,
			List.of(
				CountPlacement.of(70), InSquarePlacement.spread(),
				HeightRangePlacement.uniform(VerticalAnchor.absolute(-63), VerticalAnchor.absolute(64)), BiomeFilter.biome()
			)
		);
		register(
			ALUMINUM_ORE_PLACED,
			ModConfiguredFeatures.ALUMINUM_ORE_PLACED,
			List.of(
				CountPlacement.of(50), InSquarePlacement.spread(),
				HeightRangePlacement.triangle(VerticalAnchor.absolute(30), VerticalAnchor.absolute(80)), BiomeFilter.biome()
			)
		);

		Materials.generatePlacedFeatures();
	}

	public static ResourceKey<PlacedFeature> registerKey(String name) {
		return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(MavakCraft.MODID, name));
	}

	public static void register(ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
		context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
	}

	public static void register(ResourceKey<PlacedFeature> key, ResourceKey<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
		register(key, configuredFeatures.getOrThrow(configuration), modifiers);
	}
}
