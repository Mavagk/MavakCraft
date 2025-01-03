package net.mavakcraft.block;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;

public abstract class AbstractByteBlock extends Block {
	public AbstractByteBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}

	/**
	 * Get the byte value this block provides.
	 * @return The value the block provides from a direction of {@link null} if it doesn't provide a value from the direction.
	 */
	public @Nullable Integer getByteValue(LevelReader level, BlockState state, BlockPos pos, Direction directionFrom, int recursiveCount) {
		return null;
	}

	/**
	 * The type of byte value input this block provides.
	 * @return 0 for a primary input, 1 from a secondary input, {@link null} for a non-input.
	 */
	public @Nullable Integer getByteValueInputNumber() {
		return null;
	}

	/**
	 * Returns the byte value being provided from a connecting block.
	 * @param pos The pos of this block, NOT the connecting block.
	 * @param directionFrom The direction towards the block we want to get the provided byte value from.
	 * @return The byte value or {@link null} if the block doesn't provide a byte value or does not extend {@link AbstractByteBlock}.
	 */
	protected @Nullable Integer getByteValueOfConnectingBlock(LevelReader level, BlockPos pos, Direction directionFrom, int recursiveCount) {
		if (recursiveCount >= 50) return null;
		BlockState connectionBlockState = getConnectingBlockState(level, pos, directionFrom);
		Block connectionBlock = connectionBlockState.getBlock();
		if (!(connectionBlock instanceof AbstractByteBlock)) return null;
		return ((AbstractByteBlock)connectionBlock).getByteValue(level, connectionBlockState, pos.offset(directionFrom.getNormal()), directionFrom, recursiveCount + 1);
	}

	/**
	 * Returns the type of byte value from a connecting block.
	 * @param pos The pos of this block, NOT the connecting block.
	 * @param directionFrom The direction towards the block we want to get the input type of.
	 * @return 0 for a primary input, 1 from a secondary input, {@link null} for a non-input or one that does not extend {@link AbstractByteBlock}.
	 */
	protected @Nullable Integer getByteValueInputNumberOfConnectingBlock(LevelReader level, BlockPos pos, Direction directionFrom) {
		Block connectionBlock = getConnectingBlockState(level, pos, directionFrom).getBlock();
		if (!(connectionBlock instanceof AbstractByteBlock)) return null;
		return ((AbstractByteBlock)connectionBlock).getByteValueInputNumber();
	}

	protected BlockState getConnectingBlockState(LevelReader level, BlockPos pos, Direction direction) {
		return level.getBlockState(pos.offset(direction.getNormal()));
	}

	public void connectingByteValueChanged(ServerLevel level, BlockPos pos, Direction direction, int recursiveCount) {
		if (recursiveCount >= 60) return;
		BlockPos connecting_pos = pos.offset(direction.getNormal());
		Block connecting_block = level.getBlockState(connecting_pos).getBlock();
		if (!(connecting_block instanceof AbstractByteBlock)) return;
		((AbstractByteBlock)connecting_block).byteValueChanged(level, connecting_pos, direction, recursiveCount + 1);
	}

	protected abstract void byteValueChanged(ServerLevel level, BlockPos pos, Direction direction, int recursive_count);

	@Override
	protected void onPlace(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull BlockState oldState, boolean movedByPiston) {
		super.onPlace(state, level, pos, oldState, movedByPiston);
		level.scheduleTick(pos, state.getBlock(), 1);
	}
}
