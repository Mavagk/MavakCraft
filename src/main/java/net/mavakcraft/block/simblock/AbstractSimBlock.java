package net.mavakcraft.block.simblock;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AbstractSimBlock extends Block {
	public AbstractSimBlock(Properties properties) {
		super(properties);
	}

	static final @Nullable Integer DENSITY = null;

	@Nullable Integer getDensity() {
		return DENSITY;
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

	static @Nullable AbstractSimBlock getSimBlockClass(@Nonnull BlockState state) {
		Block block = state.getBlock();
		return block instanceof AbstractSimBlock? (AbstractSimBlock)block: null;
	}

	static @Nullable AbstractSimBlock getSimBlockClass(@Nonnull Level level, @Nonnull BlockPos pos) {
		return getSimBlockClass(level.getBlockState(pos));
	}

	static @Nullable Integer getDensity(@Nonnull Level level, @Nonnull BlockPos pos) {
		BlockState state = level.getBlockState(pos);
		if (state.isAir()) return 0;
		AbstractSimBlock block = getSimBlockClass(state);
		return block == null? null: block.getDensity();
	}
}
