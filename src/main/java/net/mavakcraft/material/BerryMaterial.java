package net.mavakcraft.material;

import javax.annotation.Nonnull;

//import net.mavakcraft.registry.ModBlocksDeferredRegister;
//import net.minecraft.world.item.CreativeModeTabs;

import net.mavakcraft.block.BerryBushBlock;

public class BerryMaterial extends Material {
	@Nonnull String name;

	BerryBushBlock bush;

	public BerryMaterial(@Nonnull String name) {
		this.name = name;
	}

	//@Override
	//public void onRegisterBlocks(ModBlocksDeferredRegister register) {
	//	bush = register.;
	//}
}
