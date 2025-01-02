package net.mavakcraft.block;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.Direction;

public abstract class AbstractByteBlock extends Block {
	public AbstractByteBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}

	/**
	 * Get the byte value this block provides.
	 * @return The value the block provides from a direction of {@link null} if it doesn't provide a value from the direction.
	 */
	public @Nullable Integer getByteValue(BlockState state, Direction directionFrom) {
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
	public @Nullable Integer getByteValueOfConnectingBlock(LevelReader level, BlockPos pos, Direction directionFrom) {
		BlockState connectionBlockState = level.getBlockState(pos.offset(directionFrom.getNormal()));
		Block connectionBlock = connectionBlockState.getBlock();
		if (!(connectionBlock instanceof AbstractByteBlock)) return null;
		return ((AbstractByteBlock)connectionBlock).getByteValue(connectionBlockState, directionFrom);
	}

	/**
	 * Returns the type of byte value from a connecting block.
	 * @param pos The pos of this block, NOT the connecting block.
	 * @param directionFrom The direction towards the block we want to get the input type of.
	 * @return 0 for a primary input, 1 from a secondary input, {@link null} for a non-input or one that does not extend {@link AbstractByteBlock}.
	 */
	public @Nullable Integer getByteValueInputNumberOfConnectingBlock(LevelReader level, BlockPos pos, Direction directionFrom) {
		BlockState connectionBlockState = level.getBlockState(pos.offset(directionFrom.getNormal()));
		Block connectionBlock = connectionBlockState.getBlock();
		if (!(connectionBlock instanceof AbstractByteBlock)) return null;
		return ((AbstractByteBlock)connectionBlock).getByteValueInputNumber();
	}

	//@Override
	//protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
	//	level.scheduleTick(pos, null, UPDATE_ALL);
	//}
}
