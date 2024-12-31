package net.mavakcraft;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister.Blocks;

/**
 * A DeferredRegister subclass with extra methods.
 */
public class MavakCraftBlocksDeferredRegister extends Blocks {
	protected MavakCraftBlocksDeferredRegister(String namespace) {
		super(namespace);
	}

	public DeferredBlock<FlowerBlock> registerSimpleFlower(String name, Holder<MobEffect> effect, float seconds) {
		return register(name, () -> new FlowerBlock(effect, seconds, BlockBehaviour.Properties.of()
			.mapColor(MapColor.PLANT)
			.noCollission()
			.instabreak()
			.sound(SoundType.GRASS)
			.offsetType(BlockBehaviour.OffsetType.XZ)
			.pushReaction(PushReaction.DESTROY)
		));
	}
}
