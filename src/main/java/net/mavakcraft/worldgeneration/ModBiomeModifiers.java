package net.mavakcraft.worldgeneration;

import net.mavakcraft.MavakCraft;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries.Keys;

/**
 * Sets the biome that features should be placed in.
 */
public class ModBiomeModifiers {
	static BootstrapContext<BiomeModifier> context;

	public static final ResourceKey<BiomeModifier> ADD_ROSES = registerKey("add_roses");
	public static final ResourceKey<BiomeModifier> ADD_RUBY_ORE = registerKey("add_ruby_ore");
	public static final ResourceKey<BiomeModifier> ADD_SAPPHIRE_ORE = registerKey("add_sapphire_ore");
	public static final ResourceKey<BiomeModifier> ADD_TOPAZ_ORE = registerKey("add_topaz_ore");

	public static void bootstrap(BootstrapContext<BiomeModifier> contextIn) {
		context = contextIn;
		HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
		HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);

		register(ADD_ROSES, new BiomeModifiers.AddFeaturesBiomeModifier(
			HolderSet.direct(biomes.getOrThrow(Biomes.PLAINS)),
			HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.ROSES_PLACED)),
			GenerationStep.Decoration.VEGETAL_DECORATION
		));
		register(ADD_RUBY_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
			biomes.getOrThrow(Tags.Biomes.IS_OVERWORLD),
			HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.RUBY_ORE_PLACED)),
			GenerationStep.Decoration.UNDERGROUND_ORES
		));
		register(ADD_SAPPHIRE_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
			biomes.getOrThrow(Tags.Biomes.IS_OVERWORLD),
			HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.SAPPHIRE_ORE_PLACED)),
			GenerationStep.Decoration.UNDERGROUND_ORES
		));
		register(ADD_TOPAZ_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
			biomes.getOrThrow(Tags.Biomes.IS_OVERWORLD),
			HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.TOPAZ_ORE_PLACED)),
			GenerationStep.Decoration.UNDERGROUND_ORES
		));
	}

	public static ResourceKey<BiomeModifier> registerKey(String name) {
		return ResourceKey.create(Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(MavakCraft.MODID, name));
	}

	public static Reference<BiomeModifier> register(ResourceKey<BiomeModifier> key, BiomeModifier value) {
		return context.register(key, value);
	}
}
