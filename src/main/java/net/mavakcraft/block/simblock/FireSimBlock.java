package net.mavakcraft.block.simblock;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class FireSimBlock extends AbstractSimBlock {
	public FireSimBlock(Properties properties) {
		super(properties);
	}

	@Override
	@Nullable Integer getDensity() {
		return -1;
	}

	@Override
	protected void tick(@Nonnull BlockState state, @Nonnull ServerLevel level, @Nonnull BlockPos pos, @Nonnull RandomSource random) {
		super.tick(state, level, pos, random);
		// Spread to neighbouring blocks that are sim flammable
		for (Direction direction : Direction.values()) {
			BlockPos neighborPos = pos.offset(direction.getNormal());
			@Nullable AbstractSimBlock neighbor = getSimBlockClass(level, neighborPos);
			if (neighbor == null || !neighbor.isSimFlammable()) continue;
			level.setBlockAndUpdate(neighborPos, state);
		}
		// Have a chance to despawn
		if (random.nextInt(20) == 0) {
			level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
			return;
		}
		// Have a change to float upwards
		if (random.nextInt(2) == 0) tryDisplace(level, pos, Direction.UP, true);
		BlockPos posAbove = pos.above();
		if (level.getBlockState(posAbove).isAir()) {
			level.scheduleTick(pos, state.getBlock(), UPDATE_ALL);
			return;
		}
		// Giggle arround if under a block
		int flowDirection = random.nextInt(4);
		for (int x = 0; x < 4; x++) {
			flowDirection = (flowDirection + 1) % 4;
			Direction direction = Direction.BY_ID.apply(flowDirection + 2);
			if (!level.getBlockState(pos.offset(direction.getNormal()).below()).isAir()) continue;
			if (tryDisplace((Level)level, pos, direction, true)) return;
		}
	}
}
