package net.mavakcraft.material;

import net.mavakcraft.datagenerator.ModBlockLootProvider;
import net.mavakcraft.registry.ModBlocksDeferredRegister;
import net.mavakcraft.registry.ModItemsDeferredRegister;

public abstract class Material {
	public abstract void registerBlocks(ModBlocksDeferredRegister blockRegister);
	public abstract void registerItems(ModItemsDeferredRegister itemRegister);
	public abstract void generateDrops(ModBlockLootProvider lootProvider);
}
