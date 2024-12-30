package net.mavakcraft.worldgeneration;

import net.mavakcraft.MavakCraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;

public class ConfiguredFeatures {
	public static final ResourceKey<ConfiguredFeature<?, ?>> ROSES_PLACED = registerKey("roses_placed");

	public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
		register(context, ROSES_PLACED, Feature.FLOWER, FeatureUtils.simpleRandomPatchConfiguration(
		64, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(
			SimpleWeightedRandomList.<BlockState>builder()
				.add(MavakCraft.ROSE.get().defaultBlockState(), 2)
				.add(MavakCraft.BLUE_ROSE.get().defaultBlockState(), 2)
			)))
		));
	}

	public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
		return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(MavakCraft.MODID, name));
	}

	private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(
		BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration
	) {
		context.register(key, new ConfiguredFeature<>(feature, configuration));
	}
}
