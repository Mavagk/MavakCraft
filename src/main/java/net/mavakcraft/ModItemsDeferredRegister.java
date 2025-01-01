package net.mavakcraft;

import java.util.HashMap;
import java.util.Vector;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister.Items;

public class ModItemsDeferredRegister extends Items {
	@Nonnull Vector<DeferredItem<?>> itemsToPutInModCreativeTab;
	@Nonnull HashMap<ResourceKey<CreativeModeTab>, Vector<DeferredItem<?>>> itemsToPutInVanillaCreativeTabs;

	protected ModItemsDeferredRegister(String namespace) {
		super(namespace);
		itemsToPutInModCreativeTab = new Vector<>();
		itemsToPutInVanillaCreativeTabs = new HashMap<>();
	}

	public <I extends Item> DeferredItem<I> register(
		@Nonnull String name, @Nonnull Function<ResourceLocation, ? extends I> func, @Nullable ResourceKey<CreativeModeTab> vanillaCreativeTabToPutIn
	) {
		DeferredItem<I> item = super.register(name, func);
		if (vanillaCreativeTabToPutIn != null) {
			itemsToPutInModCreativeTab.add(item);
			if (!itemsToPutInVanillaCreativeTabs.containsKey(vanillaCreativeTabToPutIn))
				itemsToPutInVanillaCreativeTabs.put(vanillaCreativeTabToPutIn, new Vector<>());
			itemsToPutInVanillaCreativeTabs.get(vanillaCreativeTabToPutIn).add(item);
		}
		return item;
	}

	public DeferredItem<BlockItem> registerSimpleBlockItem(Holder<Block> block, @Nullable ResourceKey<CreativeModeTab> vanillaCreativeTabToPutIn) {
		return this.registerSimpleBlockItem(block, new Item.Properties(), vanillaCreativeTabToPutIn);
	}

	public DeferredItem<BlockItem> registerSimpleBlockItem(
		Holder<Block> block, Item.Properties properties, @Nullable ResourceKey<CreativeModeTab> vanillaCreativeTabToPutIn
	) {
		return this.registerSimpleBlockItem(block.unwrapKey().orElseThrow().location().getPath(), block::value, properties, vanillaCreativeTabToPutIn);
	}

	public DeferredItem<BlockItem> registerSimpleBlockItem(
		String name, Supplier<? extends Block> block, Item.Properties properties, @Nullable ResourceKey<CreativeModeTab> vanillaCreativeTabToPutIn
	) {
		return this.register(name, key -> new BlockItem(block.get(), properties), vanillaCreativeTabToPutIn);
	}
	
	public DeferredItem<Item> registerSimpleItem(String name, @Nullable ResourceKey<CreativeModeTab> vanillaCreativeTabToPutIn) {
		return this.registerItem(name, Item::new, new Item.Properties(), vanillaCreativeTabToPutIn);
	}

	public <I extends Item> DeferredItem<I> registerItem(
		String name, Function<Item.Properties, ? extends I> func, Item.Properties props, @Nullable ResourceKey<CreativeModeTab> vanillaCreativeTabToPutIn
	) {
		return this.register(name, () -> func.apply(props), vanillaCreativeTabToPutIn);
	}

	public <I extends Item> DeferredItem<I> register(
		String name, Supplier<? extends I> sup, @Nullable ResourceKey<CreativeModeTab> vanillaCreativeTabToPutIn
	) {
		return this.register(name, key -> sup.get(), vanillaCreativeTabToPutIn);
	}

	public void putItemsInModCreativeTab(CreativeModeTab.Output output) {
		itemsToPutInModCreativeTab.forEach(item -> output.accept(item));
	}

	public void putItemsInVanillaTab(BuildCreativeModeTabContentsEvent event) {
		ResourceKey<CreativeModeTab> tab = event.getTabKey();
		Vector<DeferredItem<?>> itemsForTab = itemsToPutInVanillaCreativeTabs.get(tab);
		if (itemsForTab == null) return;
		itemsForTab.forEach(item -> event.accept(item));
	}
}
