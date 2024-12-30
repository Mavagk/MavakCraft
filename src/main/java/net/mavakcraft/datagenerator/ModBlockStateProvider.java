package net.mavakcraft.datagenerator;

import net.mavakcraft.MavakCraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {
	public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
		super(output, MavakCraft.MODID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		simpleBlockWithItem(MavakCraft.GLOWING_OBSIDIAN.get());
		simplePlant(MavakCraft.ROSE.get());
		simplePlant(MavakCraft.BLUE_ROSE.get());
	}

	void simpleBlockWithItem(Block block) {
		simpleBlockWithItem(block, cubeAll(block));
	}

	void simplePlant(Block block) {
		simpleBlock(block, models().cross(BuiltInRegistries.BLOCK.getKey(block).getPath(), blockTexture(block)).renderType("cutout"));
	}
}
