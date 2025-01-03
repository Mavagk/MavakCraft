package net.mavakcraft.block;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

//import net.mavakcraft.MavakCraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

public abstract class AbstractInputByteBlock extends AbstractByteBlock {
	protected static final DirectionProperty DIRECTION_POINTING = BlockStateProperties.FACING;

	public AbstractInputByteBlock(BlockBehaviour.Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(DIRECTION_POINTING, Direction.NORTH));
	}

	@Override
	protected void createBlockStateDefinition(@Nonnull StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(DIRECTION_POINTING);
	}

	@Override
	public BlockState getStateForPlacement(@Nonnull BlockPlaceContext context) {
		return this.defaultBlockState().setValue(DIRECTION_POINTING, context.getNearestLookingDirection());
	}

	public Direction getDirectionPointing(@Nonnull BlockState state) {
		return state.getValue(DIRECTION_POINTING);
	}

	@Override
	protected void byteValueChanged(ServerLevel level, BlockPos pos, Direction direction, int recursiveCount) {
		if (direction != getDirectionPointing(level.getBlockState(pos))) return;
		connectingByteValueChanged(level, pos, direction, recursiveCount);
	}

	@Override
	public @Nullable Integer getByteValue(LevelReader level, BlockState state, BlockPos pos, Direction directionFrom, int recursiveCount) {
		if (!directionFrom.equals(getDirectionPointing(state).getOpposite())) return null;
		@Nullable Integer result = getByteValueOfConnectingBlock(level, pos, directionFrom, recursiveCount);
		//MavakCraft.LOGGER.info("Value of " + result.toString() + " at " + pos.toString());
		return result;
	}

	@Override
	public @Nullable Integer getByteValueInputNumber() {
		return 0;
	}

	@Override
	protected void tick(@Nonnull BlockState state, @Nonnull ServerLevel level, @Nonnull BlockPos pos, @Nonnull RandomSource random) {
		connectingByteValueChanged(level, pos, getDirectionPointing(state), 0);
	}

	@Override
	protected void onRemove(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean movedByPiston) {
		Direction directionPointing = getDirectionPointing(state);
		super.onRemove(state, level, pos, newState, movedByPiston);
		BlockState connectingBlockState = getConnectingBlockState(level, pos, directionPointing);
		level.scheduleTick(pos.offset(directionPointing.getNormal()), connectingBlockState.getBlock(), 1);
	}
}
