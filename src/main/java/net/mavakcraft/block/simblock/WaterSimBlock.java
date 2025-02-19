package net.mavakcraft.block.simblock;

import javax.annotation.Nullable;

public class WaterSimBlock extends AbstractLiquidSimBlock {
	public WaterSimBlock(Properties properties) {
		super(properties);
	}

	@Override
	@Nullable Integer getDensity() {
		return 2;
	}
}
