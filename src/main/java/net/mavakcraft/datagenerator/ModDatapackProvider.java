package net.mavakcraft.datagenerator;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

import net.mavakcraft.MavakCraft;
import net.mavakcraft.worldgeneration.ModBiomeModifiers;
import net.mavakcraft.worldgeneration.ModConfiguredFeatures;
import net.mavakcraft.worldgeneration.ModPlacedFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModDatapackProvider extends DatapackBuiltinEntriesProvider {
	public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
		.add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap)
		.add(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap)
		//.add(NeoForgeRegistries.Keys., ModBiomeModifiers::bootstrap);
		.add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifiers::bootstrap);

	public ModDatapackProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries, BUILDER, Set.of(MavakCraft.MODID));
	}
}
