package net.mavakcraft.block;

import javax.annotation.Nonnull;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

public class BerryBushBlock extends SweetBerryBushBlock {
	public BerryBushBlock(Properties properties) {
		super(properties);
	}

	@Override
	public ItemStack getCloneItemStack(@Nonnull LevelReader level, @Nonnull BlockPos pos, @Nonnull BlockState state) {
		return new ItemStack(BlockItem.BY_BLOCK.get(this));
	}

	@Override
	protected InteractionResult useWithoutItem(
		@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull Player player, @Nonnull BlockHitResult hitResult
	) {
		int age = state.getValue(AGE);
		boolean isFullyGrown = age == 3;
		if (age <= 1) return super.useWithoutItem(state, level, pos, player, hitResult);
		popResource(level, pos, new ItemStack(BlockItem.BY_BLOCK.get(this), 1 + level.random.nextInt(2) + (isFullyGrown ? 1 : 0)));
		level.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
		BlockState blockstate = state.setValue(AGE, Integer.valueOf(1));
		level.setBlock(pos, blockstate, 2);
		level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, blockstate));
		return InteractionResult.sidedSuccess(level.isClientSide);
	}
}
