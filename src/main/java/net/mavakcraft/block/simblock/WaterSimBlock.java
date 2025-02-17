package net.mavakcraft.block.simblock;

import javax.annotation.Nonnull;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class WaterSimBlock extends AbstractSimBlock {
	public WaterSimBlock(Properties properties) {
		super(properties);
	}

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
	protected void onPlace(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull BlockState oldState, boolean movedByPiston) {
		super.onPlace(state, level, pos, oldState, movedByPiston);
		level.scheduleTick(pos, state.getBlock(), 0);
	}
	
	@Override
	protected void neighborChanged(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull Block neighborBlock, @Nonnull BlockPos neighborPos, boolean movedByPiston) {
		super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);
		level.scheduleTick(pos, state.getBlock(), 0);
	}
}
