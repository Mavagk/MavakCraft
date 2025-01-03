package net.mavakcraft.block.byteblock;

import javax.annotation.Nullable;

import net.minecraft.world.level.block.state.BlockBehaviour;

public class PrimaryInputByteBlock extends AbstractInputByteBlock {
	public PrimaryInputByteBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}

	@Override
	public @Nullable Integer getByteValueInputNumber() {
		return 0;
	}
}
