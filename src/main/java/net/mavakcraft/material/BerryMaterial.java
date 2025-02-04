package net.mavakcraft.material;

import javax.annotation.Nonnull;

import net.mavakcraft.registry.ModBlocksDeferredRegister;
import net.mavakcraft.registry.ModItemsDeferredRegister;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.mavakcraft.block.BerryBushBlock;
import net.mavakcraft.datagenerator.ModBlockLootProvider;
import net.mavakcraft.datagenerator.ModBlockStateProvider;
import net.mavakcraft.datagenerator.ModBlockTagProvider;
import net.mavakcraft.datagenerator.ModEnglishLanguageProvider;
import net.mavakcraft.datagenerator.ModItemModelProvider;
import net.mavakcraft.datagenerator.ModItemTagProvider;

public class BerryMaterial extends Material {
	@Nonnull String name;

	public DeferredBlock<BerryBushBlock> bush;

	DeferredItem<ItemNameBlockItem> berry;

	public BerryMaterial(@Nonnull String name) {
		this.name = name;
	}

	@Override
	public void onRegisterBlocks(ModBlocksDeferredRegister register) {
		bush = register.registerSimpleBerryBush(name);
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
		provider.dropBerryBushDrops(bush.get());
	}

	@Override
	protected void onGenerateBlockStates(ModBlockStateProvider provider) {
		provider.simpleBerryBush(bush.get(), name);
	}

	@Override
	protected void onGenerateItemModels(ModItemModelProvider provider) {
		provider.basicItem(berry.get());
	}

	@Override
	protected void onGenerateEnglishNames(ModEnglishLanguageProvider provider) {
		String englishName = provider.idToTitle(name);
		provider.add(bush.get(), englishName + "Bush");
		provider.add(berry.get(), englishName);
	}

	@Override
	protected void onGenerateBlockTags(ModBlockTagProvider provider) {
		provider.tag(BlockTags.FALL_DAMAGE_RESETTING)
			.add(bush.get());
		provider.tag(BlockTags.SWORD_EFFICIENT)
			.add(bush.get());
		provider.tag(BlockTags.BEE_GROWABLES)
			.add(bush.get());
		provider.tag(BlockTags.MINEABLE_WITH_AXE)
			.add(bush.get());
	}

	@Override
	protected void onGenerateItemTags(ModItemTagProvider provider) {
		provider.tag(Tags.Items.FOODS_BERRY)
			.add(berry.asItem());
	}
}
