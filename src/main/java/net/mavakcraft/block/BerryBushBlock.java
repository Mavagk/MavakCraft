package net.mavakcraft.block;

import javax.annotation.Nonnull;

import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.neoforged.neoforge.registries.DeferredItem;

public class BerryBushBlock extends SweetBerryBushBlock {
	@Nonnull DeferredItem<ItemNameBlockItem> berryItem;

	public BerryBushBlock(Properties properties, DeferredItem<ItemNameBlockItem> berryItem) {
		super(properties);
		this.berryItem = berryItem;
	}
}
