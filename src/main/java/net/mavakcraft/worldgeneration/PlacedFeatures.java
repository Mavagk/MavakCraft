package net.mavakcraft.worldgeneration;

import java.util.List;

import net.mavakcraft.MavakCraft;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

public class PlacedFeatures {
	public static final ResourceKey<PlacedFeature> ROSES_PLACED = registerKey("roses_placed");

	public static void bootstrap(BootstrapContext<PlacedFeature> context) {
		HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

		register(
			context,
			ROSES_PLACED,
			configuredFeatures.getOrThrow(ConfiguredFeatures.ROSES_PLACED),
			List.of(RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome())
		);
	}

	private static ResourceKey<PlacedFeature> registerKey(String name) {
		return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(MavakCraft.MODID, name));
	}

	private static void register(
		BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers
	) {
		context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
	}
}
