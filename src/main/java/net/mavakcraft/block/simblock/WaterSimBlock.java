package net.mavakcraft.block.simblock;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class WaterSimBlock extends AbstractSimBlock {
	public WaterSimBlock(Properties properties) {
		super(properties);
	}

	static final @Nullable Integer DENSITY = 2;

	@Override
	protected void tick(@Nonnull BlockState state, @Nonnull ServerLevel level, @Nonnull BlockPos pos, @Nonnull RandomSource random) {
		super.tick(state, level, pos, random);
		BlockPos posBelow = pos.below();
		if (level.getBlockState(posBelow).isAir()) {
			level.setBlockAndUpdate(posBelow, state);
			level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
			return;
		}
		int flowDirection = random.nextInt(4);
		for (int x = 0; x < 4; x++) {
			flowDirection = (flowDirection + 1) % 4;
			BlockPos flowToPos = pos.offset(Direction.BY_ID.apply(flowDirection + 2).getNormal());
			if (level.getBlockState(flowToPos).isAir()) {
				level.setBlockAndUpdate(flowToPos, state);
				level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
				return;
			}
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
