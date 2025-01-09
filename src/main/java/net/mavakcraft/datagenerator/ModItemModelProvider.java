package net.mavakcraft.datagenerator;

import net.mavakcraft.Blocks;
import net.mavakcraft.Materials;
import net.mavakcraft.MavakCraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

/**
 * Generates models for items and block items that arn't generated by ModBlockStateProvider.
 */
public class ModItemModelProvider extends ItemModelProvider {
	public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, MavakCraft.MODID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		basicItem(MavakCraft.SALT.get());
		basicFlatBlockItem(Blocks.ROSE.get());
		basicFlatBlockItem(Blocks.BLUE_ROSE.get());
		Materials.generateItemModels(this);
	}

	/**
	 * Generate a model for a block item that uses the block texture as if it where a normal item texture, a flower is an example of a use for this.
	 * @param block A block that should have a model generated for it's block item.
	 */
	public void basicFlatBlockItem(Block block) {
		getBuilder(BuiltInRegistries.BLOCK.getKey(block).getPath())
			.parent(new ModelFile.UncheckedModelFile("item/generated"))
			.texture("layer0", ResourceLocation.fromNamespaceAndPath(MavakCraft.MODID, "block/" + BuiltInRegistries.BLOCK.getKey(block).getPath()));
	}
}
