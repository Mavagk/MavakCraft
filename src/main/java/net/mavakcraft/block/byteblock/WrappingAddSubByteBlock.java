package net.mavakcraft.block.byteblock;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class WrappingAddSubByteBlock extends AbstractStorageByteBlock {
	public WrappingAddSubByteBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}

	@Override
	protected void tick(@Nonnull BlockState state, @Nonnull ServerLevel level, @Nonnull BlockPos pos, @Nonnull RandomSource random) {
		super.tick(state, level, pos, random);
		int newValue = 0;
		for (Direction direction : Direction.values()) {
			@Nullable Integer connectingValue = getByteValueOfConnectingBlock(level, pos, direction, 0);
			@Nullable Integer connectingValueInputType = getByteValueInputNumberOfConnectingBlock(level, pos, direction);
			if (connectingValueInputType == null || connectingValue == null) continue;
			switch (connectingValueInputType) {
				case 0:
					newValue = (newValue + connectingValue) % 256;
					break;
				case 1:
					int result = (newValue - connectingValue);
					newValue = result < 0? result + 256: result;
					break;
			}
		}
		setByteValue(level, state, pos, newValue);
	}
}
