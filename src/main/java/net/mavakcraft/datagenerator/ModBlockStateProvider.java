package net.mavakcraft.datagenerator;

import net.mavakcraft.Blocks;
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
		simpleBlockWithItem(Blocks.GLOWING_OBSIDIAN.get());
		simpleBlockWithItem(Blocks.CHARCOAL_BLOCK.get());
		simpleBlockWithItem(Blocks.FLINT_BLOCK.get());
		simpleBlockWithItem(Blocks.TURTLE_SCUTE_BLOCK.get());
		simpleBlockWithItem(Blocks.NETHERITE_SCRAP_BLOCK.get());
		simpleBlockWithItem(Blocks.NETHER_STAR_BLOCK.get());
		simpleBlockWithItem(Blocks.LEATHER_BLOCK.get());
		simpleBlockWithItem(Blocks.RABBIT_HIDE_BLOCK.get());
		simpleBlockWithItem(Blocks.NETHER_WART_BLOCK.get());
		simpleBlockWithItem(Blocks.FEATHER_BLOCK.get());
		simpleBlockWithItem(Blocks.GLOWSTONE_DUST_BLOCK.get());
		simpleBlockWithItem(Blocks.GUNPOWDER_BLOCK.get());
		simpleBlockWithItem(Blocks.BLAZE_POWDER_BLOCK.get());
		simpleBlockWithItem(Blocks.SUGAR_BLOCK.get());
		simpleBlockWithItem(Blocks.SALT_BLOCK.get());
		simpleBlockWithItem(Blocks.BYTE_BLOCK.get());
		simpleFacingWithItem(Blocks.PRIMARY_INPUT_BYTE_BLOCK.get());
		simpleFacingWithItem(Blocks.SECONDARY_INPUT_BYTE_BLOCK.get());
		simpleBlockWithItem(Blocks.WRAPPING_ADD_SUB_BYTE_BLOCK.get());
		simpleBlockWithItem(Blocks.WRAPPING_MULT_DIV_BYTE_BLOCK.get());
		simplePlant(Blocks.ROSE.get());
		simplePlant(Blocks.BLUE_ROSE.get());
		for (int dyeColorId = 0; dyeColorId < 16; dyeColorId++) {
			simpleBlockWithItem(Blocks.DYE_BLOCKS[dyeColorId].get());
		}
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
