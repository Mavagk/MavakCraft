package net.mavakcraft.block;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class ByteBlock extends AbstractByteBlock {
	public static final IntegerProperty BYTE_VALUE = IntegerProperty.create("value", 0, 255);

	public ByteBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}

	@Override
	protected void createBlockStateDefinition(@Nonnull StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(BYTE_VALUE);
		this.registerDefaultState(this.stateDefinition.any().setValue(BYTE_VALUE, Integer.valueOf(0)));
	}

	@Override
	public @Nullable Integer getByteValue(BlockState state, Direction directionFrom) {
		return state.getValue(BYTE_VALUE);
	}
}
