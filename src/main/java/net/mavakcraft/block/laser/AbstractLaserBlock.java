package net.mavakcraft.block.laser;

import javax.annotation.Nullable;

import net.mavakcraft.ColorHSV;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;

public abstract class AbstractLaserBlock extends Block {
	public AbstractLaserBlock(Properties properties) {
		super(properties);
	}

	@Nullable AbstractLaserBlock getLaserBlockAt(LevelReader level, BlockPos pos) {
		Block block = level.getBlockState(pos).getBlock();
		if (!(block instanceof AbstractLaserBlock)) return null;
		return (AbstractLaserBlock)block;
	}

	@Nullable AbstractLaserBlock getNeighborLaserBlock(LevelReader level, BlockPos pos, Direction direction) {
		return getLaserBlockAt(level, pos.relative(direction));
	}

	public abstract ColorHSV colorSentInDirection(LevelReader level, BlockPos pos, Direction direction);
	public abstract void recalculateColors(LevelReader level, BlockPos pos);
}
