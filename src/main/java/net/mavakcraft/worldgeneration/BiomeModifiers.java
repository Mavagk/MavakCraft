package net.mavakcraft.worldgeneration;

import net.mavakcraft.MavakCraft;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class BiomeModifiers {
	public static final ResourceKey<BiomeModifier> ADD_ROSES = registerKey("add_roses");

	public static void bootstrap(BootstrapContext<BiomeModifier> context) {
		HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
		HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);

		context.register(ADD_ROSES, new net.neoforged.neoforge.common.world.BiomeModifiers.AddFeaturesBiomeModifier(
			HolderSet.direct(biomes.getOrThrow(Biomes.PLAINS)),
			HolderSet.direct(placedFeatures.getOrThrow(PlacedFeatures.ROSES_PLACED)),
			GenerationStep.Decoration.VEGETAL_DECORATION
		));
	}

	private static ResourceKey<BiomeModifier> registerKey(String name) {
		return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(MavakCraft.MODID, name));
	}
}
