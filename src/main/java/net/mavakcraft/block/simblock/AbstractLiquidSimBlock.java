package net.mavakcraft.block.simblock;

import javax.annotation.Nonnull;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AbstractLiquidSimBlock extends AbstractSimBlock {
	public AbstractLiquidSimBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected void tick(@Nonnull BlockState state, @Nonnull ServerLevel level, @Nonnull BlockPos pos, @Nonnull RandomSource random) {
		super.tick(state, level, pos, random);
		if (tryDisplace(level, pos, Direction.DOWN)) return;
		int flowDirection = random.nextInt(4);
		for (int x = 0; x < 4; x++) {
			flowDirection = (flowDirection + 1) % 4;
			Direction direction = Direction.BY_ID.apply(flowDirection + 2);
			if (tryDisplace((Level)level, pos, direction)) return;
		}
	}

	@Override
	protected boolean propagatesSkylightDown(@Nonnull BlockState p_309084_, @Nonnull BlockGetter p_309133_, @Nonnull BlockPos p_309097_) {
		return true;
	}

	@Override
	protected boolean skipRendering(@Nonnull BlockState state, @Nonnull BlockState adjacentBlockState, @Nonnull Direction side) {
		return adjacentBlockState.is(this) ? true : super.skipRendering(state, adjacentBlockState, side);
	}
}
