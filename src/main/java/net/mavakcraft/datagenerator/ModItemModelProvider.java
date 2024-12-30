package net.mavakcraft.datagenerator;

import net.mavakcraft.MavakCraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
	public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, MavakCraft.MODID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		basicItem(MavakCraft.SALT.get());
		basicFlatBlockItem(MavakCraft.ROSE.get());
		basicFlatBlockItem(MavakCraft.BLUE_ROSE.get());
	}

	void basicFlatBlockItem(Block block) {
		getBuilder(BuiltInRegistries.BLOCK.getKey(block).getPath())
			.parent(new ModelFile.UncheckedModelFile("item/generated"))
			.texture("layer0", ResourceLocation.fromNamespaceAndPath(MavakCraft.MODID, "block/" + BuiltInRegistries.BLOCK.getKey(block).getPath()));
	}
}
