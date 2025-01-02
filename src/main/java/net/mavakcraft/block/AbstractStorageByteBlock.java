package net.mavakcraft.block;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public abstract class AbstractStorageByteBlock extends AbstractByteBlock {
	protected static final IntegerProperty BYTE_VALUE = IntegerProperty.create("value", 0, 255);

	public AbstractStorageByteBlock(BlockBehaviour.Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(BYTE_VALUE, Integer.valueOf(0)));
	}

	@Override
	protected void createBlockStateDefinition(@Nonnull StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(BYTE_VALUE);
	}

	void setByteValue(ServerLevel level, BlockState state, BlockPos pos, int byte_value) {
		level.setBlock(pos, state.setValue(BYTE_VALUE, Integer.valueOf(byte_value)), 1 | 2);
	}

	@Override
	public @Nullable Integer getByteValue(BlockState state, Direction directionFrom) {
		return state.getValue(BYTE_VALUE);
	}
}
