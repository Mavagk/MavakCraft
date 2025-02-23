package net.mavakcraft.block.simblock;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AbstractSimBlock extends Block {
	public AbstractSimBlock(Properties properties) {
		super(properties);
	}

	@Nullable Integer getDensity() {
		return null;
	}

	boolean isSimFlammable() {
		return false;
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

	/**
	 * Get the density of a block state.
	 * @return The density or null if the block cannot be displaced.
	 */
	static @Nullable Integer getDensity(@Nonnull BlockState state) {
		if (state.isAir()) return 0;
		AbstractSimBlock block = getSimBlockClass(state);
		return block == null? null: block.getDensity();
	}

	/**
	 * Will make a block try to displace (swap with) another block. The swap will succede if the displacee block is less dense than the displacer block.
	 * @param displacerPos The pos of the block that will displace the other block.
	 * @param direction The direction from the displacer block to the displacee block.
	 * @return true if the blocks where swapped or else false.
	 */
	static boolean tryDisplace(@Nonnull Level level, @Nonnull BlockPos displacerPos, @Nonnull Direction direction, boolean isUpwards) {
		// Get the block displacing the other block
		BlockState displacerBlockState = level.getBlockState(displacerPos);
		@Nullable Integer displacerDensityOptional = getDensity(displacerBlockState);
		if (displacerDensityOptional == null) return false;
		int displacerDensity = displacerDensityOptional;
		// Get the block being displaced
		BlockPos displaceePos = displacerPos.offset(direction.getNormal());
		BlockState displaceeBlockState = level.getBlockState(displaceePos);
		@Nullable Integer displaceeDensityOptional = getDensity(displaceeBlockState);
		if (displaceeDensityOptional == null) return false;
		int displaceeDensity = displaceeDensityOptional;
		// A block can only displace a less dense block
		if ((displaceeDensity >= displacerDensity && !isUpwards) || (displaceeDensity <= displacerDensity && isUpwards)) return false;
		// Swap the blocks
		level.setBlockAndUpdate(displaceePos, displacerBlockState);
		level.setBlockAndUpdate(displacerPos, displaceeBlockState);
		return true;
	}
}
