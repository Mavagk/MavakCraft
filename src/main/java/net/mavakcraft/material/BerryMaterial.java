package net.mavakcraft.material;

import javax.annotation.Nonnull;

import net.mavakcraft.registry.ModBlocksDeferredRegister;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.mavakcraft.block.BerryBushBlock;
import net.mavakcraft.datagenerator.ModBlockLootProvider;

public class BerryMaterial extends Material {
	@Nonnull String name;

	DeferredBlock<BerryBushBlock> bush;

	public BerryMaterial(@Nonnull String name) {
		this.name = name;
	}

	@Override
	public void onRegisterBlocks(ModBlocksDeferredRegister register) {
		bush = register.registerSimpleBerryBush(name, true, CreativeModeTabs.NATURAL_BLOCKS);
	}

	@Override
	protected void onGenerateLoot(ModBlockLootProvider provider) {
		provider.dropSelf(bush.get());
	}
}
