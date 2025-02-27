package net.mavakcraft.worldgeneration;

import java.util.List;

import net.mavakcraft.Materials;
import net.mavakcraft.MavakCraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

/**
 * Sets how features are shaped and what blocks they should consist of once they have been placed.
 */
public class ModConfiguredFeatures {
	static BootstrapContext<ConfiguredFeature<?, ?>> context;

	public static final ResourceKey<ConfiguredFeature<?, ?>> ROSES_PLACED = registerKey("roses_placed");

	public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> contextIn) {
		context = contextIn;
		// Flowers
		register(ROSES_PLACED, Feature.FLOWER, FeatureUtils.simpleRandomPatchConfiguration(
		64, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(
			SimpleWeightedRandomList.<BlockState>builder()
				.add(Materials.ROSE.flower.get().defaultBlockState(), 2)
				.add(Materials.BLUE_ROSE.flower.get().defaultBlockState(), 2)
			)))
		));

		Materials.generateConfiguredFeatures();
	}

	public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
		return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(MavakCraft.MODID, name));
	}

	public static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
		context.register(key, new ConfiguredFeature<>(feature, configuration));
	}

	public static void registerSimpleOverworldOre(ResourceKey<ConfiguredFeature<?, ?>> key, Block stoneOre, Block deepslateOre, int size) {
		register(key, Feature.ORE, new OreConfiguration(List.of(
			OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), stoneOre.defaultBlockState()),
			OreConfiguration.target(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES), deepslateOre.defaultBlockState())
		), size));
	}
}
