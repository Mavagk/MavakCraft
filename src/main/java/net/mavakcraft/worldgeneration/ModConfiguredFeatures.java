package net.mavakcraft.worldgeneration;

import java.util.List;

import net.mavakcraft.Blocks;
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
	public static final ResourceKey<ConfiguredFeature<?, ?>> ROSES_PLACED = registerKey("roses_placed");
	public static final ResourceKey<ConfiguredFeature<?, ?>> RUBY_ORE_PLACED = registerKey("ruby_ore_placed");

	public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
		// Flowers
		register(context, ROSES_PLACED, Feature.FLOWER, FeatureUtils.simpleRandomPatchConfiguration(
		64, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(
			SimpleWeightedRandomList.<BlockState>builder()
				.add(Blocks.ROSE.get().defaultBlockState(), 2)
				.add(Blocks.BLUE_ROSE.get().defaultBlockState(), 2)
			)))
		));
		// Ores
		register(context, RUBY_ORE_PLACED, Feature.ORE, new OreConfiguration(List.of(
			OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), Materials.RUBY.ore.get().defaultBlockState()),
			OreConfiguration.target(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES), Materials.RUBY.deepslateOre.get().defaultBlockState())
		), 5));
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
