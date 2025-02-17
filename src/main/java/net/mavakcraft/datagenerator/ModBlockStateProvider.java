package net.mavakcraft.datagenerator;

import net.mavakcraft.Materials;
import net.mavakcraft.MavakCraft;
import net.mavakcraft.block.BerryBushBlock;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelProvider;
import net.neoforged.neoforge.client.model.generators.VariantBlockStateBuilder;
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
		simpleBlockWithItem(MavakCraft.NETHER_WART_BLOCK.get());
		simpleBlockWithItem(MavakCraft.FEATHER_BLOCK.get());
		simpleBlockWithItem(MavakCraft.BYTE_BLOCK.get());
		simpleFacingWithItem(MavakCraft.PRIMARY_INPUT_BYTE_BLOCK.get());
		simpleFacingWithItem(MavakCraft.SECONDARY_INPUT_BYTE_BLOCK.get());
		simpleBlockWithItem(MavakCraft.WRAPPING_ADD_SUB_BYTE_BLOCK.get());
		simpleBlockWithItem(MavakCraft.WRAPPING_MULT_DIV_BYTE_BLOCK.get());
		for (int dyeColorId = 0; dyeColorId < 16; dyeColorId++) {
			simpleBlockWithItem(MavakCraft.DYE_BLOCKS[dyeColorId].get());
		}
		Materials.generateBlockStates(this);
	}

	/**
	 * Generates a block state and model for a simple full cube block, also generates the model for the block's block item.
	 */
	public void simpleBlockWithItem(Block block) {
		simpleBlockWithItem(block, cubeAll(block));
	}

	/**
	 * Generates a block state and model for a simple full cube block, also generates the model for the block's block item.
	 */
	public void simpleTranslucentBlockWithItem(Block block) {
		simpleBlockWithItem(block, models().cubeAll(BuiltInRegistries.BLOCK.getKey(block).getPath(), blockTexture(block)).renderType("translucent"));
	}

	/**
	 * Generates a block state and model for a simple 1 block high plant such as a flower, this does NOT generate a item model.
	 */
	public void simplePlant(Block block) {
		simpleBlock(block, models().cross(BuiltInRegistries.BLOCK.getKey(block).getPath(), blockTexture(block)).renderType("cutout"));
	}

	public void blockWithTextureWithItem(Block block, String pathInBlockFolder) {
		ResourceLocation name = BuiltInRegistries.BLOCK.getKey(block);
		ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(name.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + pathInBlockFolder);
		simpleBlockWithItem(block, models().cubeAll(BuiltInRegistries.BLOCK.getKey(block).getPath(), texture));
	}

	public void simpleFacingWithItem(Block block) {
		ResourceLocation name = BuiltInRegistries.BLOCK.getKey(block);
		ResourceLocation side = ResourceLocation.fromNamespaceAndPath(name.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + name.getPath() + "_side");
		ResourceLocation top = ResourceLocation.fromNamespaceAndPath(name.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + name.getPath() + "_top");
		directionalBlock(block, models().orientable(BuiltInRegistries.BLOCK.getKey(block).getPath(), side, side, top));
		simpleBlockItem(block, models().orientable(BuiltInRegistries.BLOCK.getKey(block).getPath(), side, side, top));
	}

	public void simpleBerryBush(Block bushBlock, String name) {
		ResourceLocation resourceName = BuiltInRegistries.BLOCK.getKey(bushBlock);
		String path = ModelProvider.BLOCK_FOLDER + "/" + name + "/";
		VariantBlockStateBuilder variantBuilder = getVariantBuilder(bushBlock);
		for (int age = 0; age < 4; age++) {
			ResourceLocation resource = ResourceLocation.fromNamespaceAndPath(resourceName.getNamespace(), path + String.valueOf(age));
			variantBuilder.partialState().with(BerryBushBlock.AGE, age)
			.modelForState().modelFile(models().cross(resourceName + "_bush_stage" + String.valueOf(age), resource).renderType("cutout")).addModel();
		}
	}
}
