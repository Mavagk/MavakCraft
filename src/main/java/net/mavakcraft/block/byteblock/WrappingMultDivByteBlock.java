package net.mavakcraft.block.byteblock;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class WrappingMultDivByteBlock extends AbstractStorageByteBlock {
	public WrappingMultDivByteBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}

	@Override
	protected void tick(@Nonnull BlockState state, @Nonnull ServerLevel level, @Nonnull BlockPos pos, @Nonnull RandomSource random) {
		super.tick(state, level, pos, random);
		long dividend = 1;
		long divisor = 1;
		for (Direction direction : Direction.values()) {
			@Nullable Integer connectingValue = getByteValueOfConnectingBlock(level, pos, direction, 0);
			@Nullable Integer connectingValueInputType = getByteValueInputNumberOfConnectingBlock(level, pos, direction);
			if (connectingValueInputType == null || connectingValue == null) continue;
			switch (connectingValueInputType) {
				case 0:
					dividend *= connectingValue;
					break;
				case 1:
					if (connectingValue == 0) return;
					divisor *= connectingValue;
					break;
			}
		}
		setByteValue(level, state, pos, (int)(dividend / divisor % 256));
	}
}
