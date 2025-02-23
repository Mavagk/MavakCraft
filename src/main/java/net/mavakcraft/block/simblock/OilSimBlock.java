package net.mavakcraft.block.simblock;

import javax.annotation.Nullable;

public class OilSimBlock extends AbstractLiquidSimBlock {
	public OilSimBlock(Properties properties) {
		super(properties);
	}

	@Override
	@Nullable Integer getDensity() {
		return 1;
	}

	@Override
	boolean isSimFlammable() {
		return true;
	}
}
