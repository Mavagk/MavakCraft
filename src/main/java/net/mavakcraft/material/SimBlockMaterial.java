package net.mavakcraft.material;

import java.util.function.Function;
import javax.annotation.Nonnull;

import net.mavakcraft.block.simblock.AbstractSimBlock;
import net.mavakcraft.datagenerator.ModBlockLootProvider;
import net.mavakcraft.datagenerator.ModBlockStateProvider;
import net.mavakcraft.datagenerator.ModEnglishLanguageProvider;
import net.mavakcraft.registry.ModBlocksDeferredRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;

public class SimBlockMaterial<B extends AbstractSimBlock> extends Material {
	public DeferredBlock<B> block;

	@Nonnull String name;
	MapColor mapColor;
	int lightLevel;
	@Nonnull Function<Properties, B> sup;
	boolean isTransparent;

	public SimBlockMaterial(@Nonnull String name, @Nonnull Function<Properties, B> sup, @Nonnull MapColor mapColor, int lightLevel, boolean isTransparent) {
		this.name = name;
		this.mapColor = mapColor;
		this.lightLevel = lightLevel;
		this.sup = sup;
		this.isTransparent = isTransparent;
	}

	private static boolean never(BlockState state, BlockGetter blockGetter, BlockPos pos) {
		return false;
	}

	@Override
	public void onRegisterBlocks(ModBlocksDeferredRegister register) {
		block = register.register(name + "_sim_block", () -> sup.apply(BlockBehaviour.Properties.of()
			.mapColor(mapColor)
			.requiresCorrectToolForDrops()
			.strength(5.0F, 6.0F)
			.isViewBlocking(SimBlockMaterial::never)
			.noOcclusion()
			.lightLevel(blockState -> lightLevel)),
		true, CreativeModeTabs.BUILDING_BLOCKS);
	}

	@Override
	public void onGenerateLoot(ModBlockLootProvider provider) {
		provider.dropSelf(block.get());
	}

	@Override
	public void onGenerateBlockStates(ModBlockStateProvider provider) {
		if (isTransparent) provider.simpleTranslucentBlockWithItem(block.get());
		else provider.simpleBlockWithItem(block.get());
	}

	@Override
	public void onGenerateEnglishNames(ModEnglishLanguageProvider provider) {
		String englishName = provider.idToTitle(name);
		provider.add(block.get(), englishName + " Sim Block");
	}
}
