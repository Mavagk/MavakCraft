package net.mavakcraft.block.simblock;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class SandSimBlock extends AbstractSimBlock {
	public SandSimBlock(Properties properties) {
		super(properties);
	}

	@Override
	@Nullable Integer getDensity() {
		return 3;
	}

	@Override
	protected void tick(@Nonnull BlockState state, @Nonnull ServerLevel level, @Nonnull BlockPos pos, @Nonnull RandomSource random) {
		super.tick(state, level, pos, random);
		if (tryDisplace(level, pos, Direction.DOWN, false)) return;
		int flowDirection = random.nextInt(4);
		for (int x = 0; x < 4; x++) {
			flowDirection = (flowDirection + 1) % 4;
			Direction direction = Direction.BY_ID.apply(flowDirection + 2);
			if (!level.getBlockState(pos.offset(direction.getNormal()).below()).isAir()) continue;
			if (tryDisplace((Level)level, pos, direction, false)) return;
		}
	}
}
