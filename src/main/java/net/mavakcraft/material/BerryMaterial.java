package net.mavakcraft.material;

import javax.annotation.Nonnull;

import net.mavakcraft.registry.ModBlocksDeferredRegister;
import net.mavakcraft.registry.ModItemsDeferredRegister;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.mavakcraft.block.BerryBushBlock;
import net.mavakcraft.datagenerator.ModBlockLootProvider;

public class BerryMaterial extends Material {
	@Nonnull String name;

	DeferredBlock<BerryBushBlock> bush;

	DeferredItem<ItemNameBlockItem> berry;

	public BerryMaterial(@Nonnull String name) {
		this.name = name;
	}

	@Override
	public void onRegisterBlocks(ModBlocksDeferredRegister register) {
		bush = register.registerSimpleBerryBush(name, berry);
	}

	@Override
	protected void onRegisterItems(ModItemsDeferredRegister register) {
		berry = register.registerItem(
			name, props -> new ItemNameBlockItem(bush.get(), props),
			new Item.Properties().food(Foods.SWEET_BERRIES), CreativeModeTabs.FOOD_AND_DRINKS
		);
	}

	@Override
	protected void onGenerateLoot(ModBlockLootProvider provider) {
		provider.dropSelf(bush.get());
	}
}
