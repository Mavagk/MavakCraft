package net.mavakcraft.datagenerator;

import net.mavakcraft.MavakCraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

/**
 * Generates block states and models for blocks and item models for some block items.
 */
public class ModBlockStateProvider extends BlockStateProvider {
	public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
		super(output, MavakCraft.MODID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		simpleBlockWithItem(MavakCraft.GLOWING_OBSIDIAN.get());
		simpleBlockWithItem(MavakCraft.CHARCOAL_BLOCK.get());
		simpleBlockWithItem(MavakCraft.GLOWSTONE_DUST_BLOCK.get());
		simpleBlockWithItem(MavakCraft.GUNPOWDER_BLOCK.get());
		simpleBlockWithItem(MavakCraft.SUGAR_BLOCK.get());
		simpleBlockWithItem(MavakCraft.SALT_BLOCK.get());
		simpleBlockWithItem(MavakCraft.BYTE_BLOCK.get());
		simpleFacingWithItem(MavakCraft.PRIMARY_INPUT_BYTE_BLOCK.get());
		simplePlant(MavakCraft.ROSE.get());
		simplePlant(MavakCraft.BLUE_ROSE.get());
	}

	/**
	 * Generates a block state and model for a simple full cube block, also generates the model for the block's block item.
	 */
	void simpleBlockWithItem(Block block) {
		simpleBlockWithItem(block, cubeAll(block));
	}

	/**
	 * Generates a block state and model for a simple 1 block high plant such as a flower, this does NOT generate a item model.
	 */
	void simplePlant(Block block) {
		simpleBlock(block, models().cross(BuiltInRegistries.BLOCK.getKey(block).getPath(), blockTexture(block)).renderType("cutout"));
	}

	void simpleFacingWithItem(Block block) {
		ResourceLocation name = BuiltInRegistries.BLOCK.getKey(block);
		ResourceLocation side = ResourceLocation.fromNamespaceAndPath(name.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + name.getPath() + "_side");
		ResourceLocation top = ResourceLocation.fromNamespaceAndPath(name.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + name.getPath() + "_top");
		directionalBlock(block, models().orientable(BuiltInRegistries.BLOCK.getKey(block).getPath(), side, side, top));
		simpleBlockItem(block, models().orientable(BuiltInRegistries.BLOCK.getKey(block).getPath(), side, side, top));
	}
}
