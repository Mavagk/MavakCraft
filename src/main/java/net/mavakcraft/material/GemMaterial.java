package net.mavakcraft.material;

import javax.annotation.Nonnull;

import net.mavakcraft.datagenerator.ModBlockLootProvider;
import net.mavakcraft.registry.ModBlocksDeferredRegister;
import net.mavakcraft.registry.ModItemsDeferredRegister;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

public class GemMaterial extends Material {
	public DeferredBlock<DropExperienceBlock> ore;
	public DeferredBlock<DropExperienceBlock> deepslateOre;
	public DeferredBlock<Block> materialBlock;

	public DeferredItem<Item> gem;

	@Nonnull String name;
	int xpMin;
	int xpMax;
	MapColor mapColor;

	public GemMaterial(@Nonnull String name, int xpMin, int xpMax, MapColor mapColor) {
		this.name = name;
		this.xpMin = xpMin;
		this.xpMax = xpMax;
		this.mapColor = mapColor;
	}

	@Override
	public void registerBlocks(ModBlocksDeferredRegister blockRegister) {
		ore = blockRegister.registerSimpleOre(name, xpMin, xpMax);
		deepslateOre = blockRegister.registerSimpleDeepslateOre(name, xpMin, xpMax);
		materialBlock = blockRegister.registerSimpleMaterialBlock(name, mapColor);
	}

	@Override
	public void registerItems(ModItemsDeferredRegister itemRegister) {
		gem = itemRegister.registerSimpleItem(name, CreativeModeTabs.INGREDIENTS);
	}

	@Override
	public void generateDrops(ModBlockLootProvider lootProvider) {
		lootProvider.dropSelf(materialBlock.get());
		lootProvider.dropOreDrops(ore.get(), gem.get());
		lootProvider.dropOreDrops(deepslateOre.get(), gem.get());
	}
}
