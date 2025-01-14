package net.mavakcraft.datagenerator;

import java.util.concurrent.CompletableFuture;

import net.mavakcraft.Materials;
import net.mavakcraft.MavakCraft;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.FurnaceFuel;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

public class ModDataMapProvider extends DataMapProvider {
	public ModDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(packOutput, lookupProvider);
	}

	@Override
	protected void gather() {
		this.builder(NeoForgeDataMaps.FURNACE_FUELS)
			.add(MavakCraft.ITEMS.getBlockItem(Materials.CHARCOAL_BLOCK.block), new FurnaceFuel(16000), false);
	}
}
