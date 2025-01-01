package net.mavakcraft.registry;

import java.util.HashMap;
import java.util.Vector;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister.Items;

public class ModItemsDeferredRegister extends Items {
	/**
	 * A list of items that should be put in the mod creative mode tab.
	 */
	@Nonnull Vector<DeferredItem<?>> itemsToPutInModCreativeTab;
	/**
	 * Maps vanilla creative mode tabs to list of mod items that should be added to them.
	 */
	@Nonnull HashMap<ResourceKey<CreativeModeTab>, Vector<DeferredItem<?>>> itemsToPutInVanillaCreativeTabs;
	@Nonnull HashMap<Holder<Block>, DeferredItem<BlockItem>> blockItems;

	public ModItemsDeferredRegister(String namespace) {
		super(namespace);
		itemsToPutInModCreativeTab = new Vector<>();
		itemsToPutInVanillaCreativeTabs = new HashMap<>();
		blockItems = new HashMap<>();
	}

	/**
	 * Register a block item for a block.
	 * @param vanillaCreativeTabToPutIn If non-null, is the vanilla tab to put the item into, the item is also put into the mod creative mode tab.
	 * If null, the item is not put into any creative mode tabs.
	 */
	public DeferredItem<BlockItem> registerSimpleBlockItem(Holder<Block> block, @Nullable ResourceKey<CreativeModeTab> vanillaCreativeTabToPutIn) {
		DeferredItem<BlockItem> item = super.registerSimpleBlockItem(block);
		setCreativeTab(item, vanillaCreativeTabToPutIn);
		blockItems.put(block, item);
		return item;
	}
	
	/**
	 * Register a simple item with default properties.
	 * @param vanillaCreativeTabToPutIn If non-null, is the vanilla tab to put the item into, the item is also put into the mod creative mode tab.
	 * If null, the item is not put into any creative mode tabs.
	 */
	public DeferredItem<Item> registerSimpleItem(String name, @Nullable ResourceKey<CreativeModeTab> vanillaCreativeTabToPutIn) {
		DeferredItem<Item> item = super.registerSimpleItem(name);
		setCreativeTab(item, vanillaCreativeTabToPutIn);
		return item;
	}

	/**
	 * Set the vanilla creative tab an item should be in.
	 * @param vanillaCreativeTabToPutIn If non-null, is the vanilla tab to put the item into, the item is also put into the mod creative mode tab.
	 * If null, the item is not put into any creative mode tabs.
	 */
	<I extends Item> void setCreativeTab(DeferredItem<I> item, @Nullable ResourceKey<CreativeModeTab> vanillaCreativeTabToPutIn) {
		// Return if the item shouldn't be put in any tab
		if (vanillaCreativeTabToPutIn == null) return;
		// Set that the item should be put in the mod creative tab
		itemsToPutInModCreativeTab.add(item);
		// Set that the item should be put in the set vanilla tab.
		if (!itemsToPutInVanillaCreativeTabs.containsKey(vanillaCreativeTabToPutIn))
			itemsToPutInVanillaCreativeTabs.put(vanillaCreativeTabToPutIn, new Vector<>());
		itemsToPutInVanillaCreativeTabs.get(vanillaCreativeTabToPutIn).add(item);
	}

	/**
	 * Put all items registered to be put in a creative tab in a creative mode tab.
	 */
	public void putItemsInModCreativeTab(CreativeModeTab.Output output) {
		itemsToPutInModCreativeTab.forEach(item -> output.accept(item));
	}

	/**
	 * Put all items registered for a creative mode tab in the tab.
	 */
	public void putItemsInVanillaTab(BuildCreativeModeTabContentsEvent event) {
		ResourceKey<CreativeModeTab> tab = event.getTabKey();
		Vector<DeferredItem<?>> itemsForTab = itemsToPutInVanillaCreativeTabs.get(tab);
		if (itemsForTab == null) return;
		itemsForTab.forEach(item -> event.accept(item));
	}

	public DeferredItem<BlockItem> getBlockItem(Holder<Block> block) {
		return blockItems.get(block);
	}
}
