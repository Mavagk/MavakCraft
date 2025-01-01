package net.mavakcraft.registry;

import java.util.HashMap;
import java.util.Vector;
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
	/*
	 * A list of blocks that block items should be registered for.
	 */
	@Nonnull Vector<DeferredBlock<?>> registerBlockItemsFor;
	/*
	 * Maps each block to the vanilla creative mode tab it should be put in.
	 * Blocks can be mapped it null, in which case they won't be put into any vanilla tab or the mod tab.
	 */
	@Nonnull HashMap<DeferredBlock<?>, ResourceKey<CreativeModeTab>> vanillaCreativeTabsForEachBlock;

	public ModBlocksDeferredRegister(String namespace) {
		super(namespace);
		registerBlockItemsFor = new Vector<>();
		vanillaCreativeTabsForEachBlock = new HashMap<>();
	}

	/**
	 * Register a simple block.
	 * @param doRegisterItem should a block item be registered for this block.
	 * @param vanillaCreativeTabToPutIn If non-null, is the vanilla tab to put the block item into, the block item is also put into the mod creative mode tab.
	 * If null, the block item is not put into any creative mode tabs.
	 */
	public DeferredBlock<Block> registerSimpleBlock(
		@Nonnull String name, @Nonnull BlockBehaviour.Properties props, boolean doRegisterItem, @Nullable ResourceKey<CreativeModeTab> vanillaCreativeTabToPutIn
	) {
		DeferredBlock<Block> block = super.registerSimpleBlock(name, props);
		setItemAndCreativeTab(block, doRegisterItem, vanillaCreativeTabToPutIn);
		return block;
	}

	/**
	 * Register a simple flower with default properties.
	 * @param doRegisterItem should a block item be registered for this block.
	 * @param vanillaCreativeTabToPutIn If non-null, is the vanilla tab to put the block item into, the block item is also put into the mod creative mode tab.
	 * If null, the block item is not put into any creative mode tabs.
	 */
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

	/**
	 * Register a block.
	 * @param doRegisterItem should a block item be registered for this block.
	 * @param vanillaCreativeTabToPutIn If non-null, is the vanilla tab to put the block item into, the block item is also put into the mod creative mode tab.
	 * If null, the block item is not put into any creative mode tabs.
	 */
	public <B extends Block> DeferredBlock<B> register(
		@Nonnull String name, @Nonnull Supplier<? extends B> sup, boolean doRegisterItem, @Nullable ResourceKey<CreativeModeTab> vanillaCreativeTabToPutIn
	) {
		DeferredBlock<B> block = super.register(name, sup);
		setItemAndCreativeTab(block, doRegisterItem, vanillaCreativeTabToPutIn);
		return block;
	}

	/**
	 * Set if a block item should be created for the block and which creative mode tab it should be put in.
	 * @param doRegisterItem should a block item be registered for this block.
	 * @param vanillaCreativeTabToPutIn If non-null, is the vanilla tab to put the block item into, the block item is also put into the mod creative mode tab.
	 * If null, the block item is not put into any creative mode tabs.
	 */
	<B extends Block> void setItemAndCreativeTab(
		DeferredBlock<B> block, boolean doRegisterItem, @Nullable ResourceKey<CreativeModeTab> vanillaCreativeTabToPutIn
	) {
		if (doRegisterItem) registerBlockItemsFor.add(block);
		vanillaCreativeTabsForEachBlock.put(block, vanillaCreativeTabToPutIn);
	}

	/**
	 * Register block items for all blocks that block items should be created for.
	 */
	public void registerBlockItems(ModItemsDeferredRegister itemRegister) {
		registerBlockItemsFor.forEach(block -> itemRegister.registerSimpleBlockItem(block, vanillaCreativeTabsForEachBlock.get(block)));
	}
}
