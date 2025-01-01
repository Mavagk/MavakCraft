package net.mavakcraft;

import java.util.HashMap;
import java.util.Vector;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
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
public class ModBlocksDeferredRegister extends Blocks {
	@Nonnull Vector<DeferredBlock<?>> registerBlockItemsFor;
	@Nonnull HashMap<DeferredBlock<?>, ResourceKey<CreativeModeTab>> vanillaCreativeTabsForEachBlock;

	protected ModBlocksDeferredRegister(String namespace) {
		super(namespace);
		registerBlockItemsFor = new Vector<>();
		vanillaCreativeTabsForEachBlock = new HashMap<>();
	}

	public DeferredBlock<Block> registerSimpleBlock(
		@Nonnull String name, @Nonnull BlockBehaviour.Properties props, boolean doRegisterItem, @Nullable ResourceKey<CreativeModeTab> vanillaCreativeTabToPutIn
	) {
		return this.registerBlock(name, Block::new, props, doRegisterItem, vanillaCreativeTabToPutIn);
	}

	public DeferredBlock<FlowerBlock> registerSimpleFlower(
		String name, Holder<MobEffect> effect, float seconds, boolean doRegisterItem, @Nullable ResourceKey<CreativeModeTab> vanillaCreativeTabToPutIn
	) {
		return register(name, () -> new FlowerBlock(effect, seconds, BlockBehaviour.Properties.of()
			.mapColor(MapColor.PLANT)
			.noCollission()
			.instabreak()
			.sound(SoundType.GRASS)
			.offsetType(BlockBehaviour.OffsetType.XZ)
			.pushReaction(PushReaction.DESTROY)
		), true, vanillaCreativeTabToPutIn);
	}

	public <B extends Block> DeferredBlock<B> register(
		@Nonnull String name, @Nonnull Supplier<? extends B> sup, boolean doRegisterItem, @Nullable ResourceKey<CreativeModeTab> vanillaCreativeTabToPutIn
	) {
		DeferredBlock<B> block = super.register(name, sup);
		if (doRegisterItem) registerBlockItemsFor.add(block);
		vanillaCreativeTabsForEachBlock.put(block, vanillaCreativeTabToPutIn);
		return block;
	}

	public <B extends Block> DeferredBlock<B> registerBlock(
		String name, Function<BlockBehaviour.Properties, ? extends B> func, BlockBehaviour.Properties props, boolean doRegisterItem,
		@Nullable ResourceKey<CreativeModeTab> vanillaCreativeTabToPutIn
	) {
		return this.register(name, () -> func.apply(props), doRegisterItem, vanillaCreativeTabToPutIn);
	}

	public void registerBlockItems(ModItemsDeferredRegister itemRegister) {
		registerBlockItemsFor.forEach(block -> itemRegister.registerSimpleBlockItem(block, vanillaCreativeTabsForEachBlock.get(block)));
	}
}
