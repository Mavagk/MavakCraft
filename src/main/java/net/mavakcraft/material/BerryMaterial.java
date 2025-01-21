package net.mavakcraft.material;

import javax.annotation.Nonnull;

import net.mavakcraft.registry.ModBlocksDeferredRegister;
import net.mavakcraft.registry.ModItemsDeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.neoforged.neoforge.client.model.generators.ModelProvider;
import net.neoforged.neoforge.client.model.generators.VariantBlockStateBuilder;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.mavakcraft.block.BerryBushBlock;
import net.mavakcraft.datagenerator.ModBlockLootProvider;
import net.mavakcraft.datagenerator.ModBlockStateProvider;
import net.mavakcraft.datagenerator.ModEnglishLanguageProvider;
import net.mavakcraft.datagenerator.ModItemModelProvider;

public class BerryMaterial extends Material {
	@Nonnull String name;

	DeferredBlock<BerryBushBlock> bush;

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
		provider.dropSelf(bush.get());
	}

	@Override
	protected void onGenerateBlockStates(ModBlockStateProvider provider) {
		ResourceLocation name = BuiltInRegistries.BLOCK.getKey(bush.get());
		String path = ModelProvider.BLOCK_FOLDER + "/" + this.name + "/";
		VariantBlockStateBuilder builder = provider.getVariantBuilder(bush.get());
		for (int age = 0; age < 4; age++) {
			ResourceLocation resource = ResourceLocation.fromNamespaceAndPath(name.getNamespace(), path + String.valueOf(age));
			builder.partialState().with(BerryBushBlock.AGE, age)
			.modelForState().modelFile(provider.models().cross(name + "_bush_stage" + String.valueOf(age), resource).renderType("cutout")).addModel();
		}
	}

	@Override
	protected void onGenerateItemModels(ModItemModelProvider provider) {
		provider.basicItem(berry.get());
		// TODO
	}

	@Override
	protected void onGenerateEnglishNames(ModEnglishLanguageProvider provider) {
		String englishName = provider.idToTitle(name);
		provider.add(bush.get(), englishName + "Bush");
		provider.add(berry.get(), englishName);
	}
}
