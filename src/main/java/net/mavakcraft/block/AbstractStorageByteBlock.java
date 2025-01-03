package net.mavakcraft.block;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public abstract class AbstractStorageByteBlock extends AbstractByteBlock {
	protected static final IntegerProperty BYTE_VALUE = IntegerProperty.create("byte_value", 0, 255);

	public AbstractStorageByteBlock(BlockBehaviour.Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(BYTE_VALUE, Integer.valueOf(0)));
	}

	@Override
	protected void createBlockStateDefinition(@Nonnull StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(BYTE_VALUE);
	}

	/**
	 * Set the byte value of this block.
	 */
	void setByteValue(ServerLevel level, BlockState state, BlockPos pos, int byteValue) {
		level.setBlock(pos, state.setValue(BYTE_VALUE, Integer.valueOf(byteValue)), 1 | 2);
		for (Direction direction : Direction.values()) {
			connectingByteValueChanged(level, pos, direction, 0);
		}
	}

	@Override
	public @Nullable Integer getByteValue(LevelReader level, BlockState state, BlockPos pos, Direction directionFrom, int recursiveCount) {
		return state.getValue(BYTE_VALUE);
	}

	@Override
	protected void byteValueChanged(ServerLevel level, BlockPos pos, Direction direction, int recursiveCount) {
		level.scheduleTick(pos, this, 1);
	}

	@Override
	protected void onRemove(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean movedByPiston) {
		super.onRemove(state, level, pos, newState, movedByPiston);
		for (Direction direction : Direction.values()) {
			BlockState connectingBlockState = getConnectingBlockState(level, pos, direction);
			level.scheduleTick(pos.offset(direction.getNormal()), connectingBlockState.getBlock(), 1);
		}
	}
}
