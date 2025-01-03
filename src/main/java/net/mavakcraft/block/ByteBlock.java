package net.mavakcraft.block;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class ByteBlock extends AbstractStorageByteBlock {
	public ByteBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}

	@Override
	protected void tick(@Nonnull BlockState state, @Nonnull ServerLevel level, @Nonnull BlockPos pos, @Nonnull RandomSource random) {
		super.tick(state, level, pos, random);
		@Nullable Integer newValue = null;
		for (Direction direction : Direction.values()) {
			@Nullable Integer connectingValue = getByteValueOfConnectingBlock(level, pos, direction, 0);
			@Nullable Integer connectingValueInputType = getByteValueInputNumberOfConnectingBlock(level, pos, direction);
			if (connectingValueInputType == null || connectingValue == null) continue;
			int connectingValueNonNull = connectingValue;
			if (newValue != null && connectingValueNonNull != newValue) return;
			newValue = connectingValueNonNull;
		}
		if (newValue == null) return;
		setByteValue(level, state, pos, newValue);
	}
}
