package net.mavakcraft.datagenerator;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

import net.mavakcraft.MavakCraft;
import net.mavakcraft.worldgeneration.BiomeModifiers;
import net.mavakcraft.worldgeneration.ConfiguredFeatures;
import net.mavakcraft.worldgeneration.PlacedFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModDatapackProvider extends DatapackBuiltinEntriesProvider {
	public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
		.add(Registries.CONFIGURED_FEATURE, ConfiguredFeatures::bootstrap)
		.add(Registries.PLACED_FEATURE, PlacedFeatures::bootstrap)
		.add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, BiomeModifiers::bootstrap);

	public ModDatapackProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries, BUILDER, Set.of(MavakCraft.MODID));
	}
}
