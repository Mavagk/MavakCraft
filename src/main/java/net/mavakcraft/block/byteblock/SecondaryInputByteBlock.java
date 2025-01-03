package net.mavakcraft.block.byteblock;

import javax.annotation.Nullable;

import net.minecraft.world.level.block.state.BlockBehaviour;

public class SecondaryInputByteBlock extends AbstractInputByteBlock {
	public SecondaryInputByteBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}

	@Override
	public @Nullable Integer getByteValueInputNumber() {
		return 1;
	}
}
