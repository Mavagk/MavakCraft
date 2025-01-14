package net.mavakcraft.material;

import javax.annotation.Nonnull;

import net.mavakcraft.datagenerator.ModBlockLootProvider;
import net.mavakcraft.datagenerator.ModBlockStateProvider;
import net.mavakcraft.datagenerator.ModBlockTagProvider;
import net.mavakcraft.datagenerator.ModEnglishLanguageProvider;
import net.mavakcraft.datagenerator.ModItemModelProvider;
import net.mavakcraft.registry.ModBlocksDeferredRegister;
import net.minecraft.core.Holder;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.FlowerBlock;
import net.neoforged.neoforge.registries.DeferredBlock;

public class SimpleFlowerMaterial extends Material {
	public DeferredBlock<FlowerBlock> flower;

	@Nonnull String name;
	@Nonnull Holder<MobEffect> effect;
	float seconds;

	public SimpleFlowerMaterial(@Nonnull String name, @Nonnull Holder<MobEffect> effect, float seconds) {
		this.name = name;
		this.effect = effect;
		this.seconds = seconds;
	}

	@Override
	public void onRegisterBlocks(ModBlocksDeferredRegister register) {
		flower = register.registerSimpleFlower(name, effect, seconds, true, CreativeModeTabs.NATURAL_BLOCKS);
	}

	@Override
	public void onGenerateLoot(ModBlockLootProvider provider) {
		provider.dropSelf(flower.get());
	}

	@Override
	public void onGenerateBlockStates(ModBlockStateProvider provider) {
		provider.simplePlant(flower.get());
	}

	@Override
	protected void onGenerateItemModels(ModItemModelProvider provider) {
		provider.basicFlatBlockItem(flower.get());
	}

	@Override
	public void onGenerateEnglishNames(ModEnglishLanguageProvider provider) {
		String englishName = provider.idToTitle(name);
		provider.add(flower.get(), englishName);
	}

	@Override
	protected void onGenerateBlockTags(ModBlockTagProvider provider) {
		provider.tag(BlockTags.SMALL_FLOWERS)
			.add(flower.get());
	}
}
