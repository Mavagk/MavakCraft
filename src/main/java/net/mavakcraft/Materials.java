package net.mavakcraft;

import java.util.Vector;

import net.mavakcraft.datagenerator.ModBlockLootProvider;
import net.mavakcraft.material.GemMaterial;
import net.mavakcraft.material.Material;
import net.mavakcraft.registry.ModBlocksDeferredRegister;
import net.mavakcraft.registry.ModItemsDeferredRegister;
import net.minecraft.world.level.material.MapColor;

public class Materials {
	public static Vector<Material> MATERIALS = new Vector<>();

	public static Material register(Material material) {
		MATERIALS.add(material);
		return material;
	}

	public static void registerBlocks(ModBlocksDeferredRegister blockRegister) {
		MATERIALS.forEach(material -> material.registerBlocks(blockRegister));
	}

	public static void registerItems(ModItemsDeferredRegister itemRegister) {
		MATERIALS.forEach(material -> material.registerItems(itemRegister));
	}

	public static void generateDrops(ModBlockLootProvider lootProvider) {
		MATERIALS.forEach(material -> material.generateDrops(lootProvider));
	}

	public static Material RUBY = register(new GemMaterial("ruby", 3, 7, MapColor.COLOR_RED));
}
