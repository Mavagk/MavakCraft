package net.mavakcraft.block;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
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

	public @Nullable Integer getByteValue(BlockState state, Direction directionOfValueFlow) {
		return null;
	}

	/**
	 * The type of byte value input this block provides.
	 * @return 0 for a primary input, 1 fro a secondary input, {@link null} for a non-input.
	 */
	public @Nullable Integer getByteValueInputNumber() {
		return null;
	}

	///**
	// * Get the stored byte value of the current block or {@link null} if the block doesn't have a stored byte value.
	// */
	//public @Nullable Integer getStoredByteValue(BlockState state) {
	//	return null;
	//}
//
	///**
	// * Get the calculated byte value of the current block or {@link null} if the block doesn't have a calculated byte value.
	// */
	//public @Nullable Integer getCalculatedByteValue(BlockState state) {
	//	return getStoredByteValue(state);
	//}
//
	///**
	// * Get the stored byte value of a connecting block.
	// * @param pos The position of the current block (NOT the connecting block we want to get the value of).
	// * @param direction The direction from the current block to the connecting block.
	// * @return The byte value of the connecting block or {@link null} if it doesn't have a stored byte value or doesn't extend {@link AbstractByteBlock}.
	// */
	//public @Nullable Integer getStoredByteValueOfConnectingBlock(LevelReader level, BlockPos pos, Direction direction) {
	//	BlockState connectionBlockState = level.getBlockState(pos.offset(direction.getNormal()));
	//	Block connectionBlock = connectionBlockState.getBlock();
	//	if (!(connectionBlock instanceof AbstractByteBlock)) return null;
	//	return ((AbstractByteBlock)connectionBlock).getStoredByteValue(connectionBlockState);
	//}
//
	///**
	// * Get the calculated or stored byte value of a connecting block.
	// * @param pos The position of the current block (NOT the connecting block we want to get the value of).
	// * @param direction The direction from the current block to the connecting block.
	// * @return The byte value of the connecting block or {@link null} if it doesn't have a byte value or doesn't extend {@link AbstractByteBlock}.
	// */
	//public @Nullable Integer getCalculatedByteValueOfConnectingBlock(LevelReader level, BlockPos pos, Direction direction) {
	//	BlockState connectionBlockState = level.getBlockState(pos.offset(direction.getNormal()));
	//	Block connectionBlock = connectionBlockState.getBlock();
	//	if (!(connectionBlock instanceof AbstractByteBlock)) return null;
	//	return ((AbstractByteBlock)connectionBlock).getCalculatedByteValue(connectionBlockState);
	//}
//
	//public void directByteValue(ServerLevel level, BlockPos pos, int value, int pushes) {
//
	//}

	//@Override
	//protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
	//	level.scheduleTick(pos, null, UPDATE_ALL);
	//}
}
