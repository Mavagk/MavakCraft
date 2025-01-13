package net.mavakcraft;

import net.mavakcraft.block.byteblock.ByteBlock;
import net.mavakcraft.block.byteblock.PrimaryInputByteBlock;
import net.mavakcraft.block.byteblock.SecondaryInputByteBlock;
import net.mavakcraft.block.byteblock.WrappingAddSubByteBlock;
import net.mavakcraft.block.byteblock.WrappingMultDivByteBlock;
import net.mavakcraft.registry.ModBlocksDeferredRegister;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;

public class Blocks {
	public static final ModBlocksDeferredRegister BLOCKS = new ModBlocksDeferredRegister(MavakCraft.MODID);

	// Removed features
	public static final DeferredBlock<Block> GLOWING_OBSIDIAN = BLOCKS.registerSimpleBlock("glowing_obsidian", (props) -> props
		.mapColor(MapColor.STONE)
		.instrument(NoteBlockInstrument.BASEDRUM)
		.requiresCorrectToolForDrops()
		.strength(35.0F, 1200.0F)
		.lightLevel(state -> 12),
	true, CreativeModeTabs.BUILDING_BLOCKS);

	// Item blocks
	public static final DeferredBlock<Block> CHARCOAL_BLOCK = BLOCKS.registerSimpleBlock("charcoal_block", (props) -> props
		.mapColor(MapColor.COLOR_BLACK)
		.instrument(NoteBlockInstrument.BASEDRUM)
		.requiresCorrectToolForDrops()
		.strength(5.0F, 6.0F),
		true, CreativeModeTabs.BUILDING_BLOCKS);
	public static final DeferredBlock<Block> FLINT_BLOCK = BLOCKS.registerSimpleBlock("flint_block", (props) -> props
		.mapColor(MapColor.COLOR_BLACK)
		.instrument(NoteBlockInstrument.BASEDRUM)
		.requiresCorrectToolForDrops()
		.strength(5.0F, 6.0F),
		true, CreativeModeTabs.BUILDING_BLOCKS);
	public static final DeferredBlock<Block> TURTLE_SCUTE_BLOCK = BLOCKS.registerSimpleBlock("turtle_scute_block", (props) -> props
		.mapColor(MapColor.COLOR_BLACK)
		.instrument(NoteBlockInstrument.BASEDRUM)
		.requiresCorrectToolForDrops()
		.strength(5.0F, 6.0F),
		true, CreativeModeTabs.BUILDING_BLOCKS);
	public static final DeferredBlock<Block> NETHERITE_SCRAP_BLOCK = BLOCKS.registerSimpleBlock("netherite_scrap_block", (props) -> props
		.mapColor(MapColor.COLOR_BROWN)
		.instrument(NoteBlockInstrument.BASEDRUM)
		.requiresCorrectToolForDrops()
		.strength(5.0F, 6.0F),
		true, CreativeModeTabs.BUILDING_BLOCKS);
	public static final DeferredBlock<Block> NETHER_STAR_BLOCK = BLOCKS.registerSimpleBlock("nether_star_block", (props) -> props
		.mapColor(MapColor.COLOR_BLACK)
		.instrument(NoteBlockInstrument.BASEDRUM)
		.requiresCorrectToolForDrops()
		.strength(5.0F, 6.0F),
		true, CreativeModeTabs.BUILDING_BLOCKS);
	public static final DeferredBlock<Block> LEATHER_BLOCK = BLOCKS.registerSimpleBlock("leather_block", (props) -> props
		.mapColor(MapColor.DIRT)
		.instrument(NoteBlockInstrument.BASS)
		.strength(2.0F, 3.0F)
		.ignitedByLava(),
		true, CreativeModeTabs.BUILDING_BLOCKS);
	public static final DeferredBlock<Block> RABBIT_HIDE_BLOCK = BLOCKS.registerSimpleBlock("rabbit_hide_block", (props) -> props
		.mapColor(MapColor.DIRT)
		.instrument(NoteBlockInstrument.BASS)
		.strength(2.0F, 3.0F)
		.ignitedByLava(),
		true, CreativeModeTabs.BUILDING_BLOCKS);
	public static final DeferredBlock<Block> NETHER_WART_BLOCK = BLOCKS.registerSimpleBlock("nether_wart_block", (props) -> props
		.mapColor(MapColor.COLOR_RED)
		.instrument(NoteBlockInstrument.BASS)
		.strength(2.0F, 3.0F),
		true, CreativeModeTabs.BUILDING_BLOCKS);
	public static final DeferredBlock<Block> FEATHER_BLOCK = BLOCKS.registerSimpleBlock("feather_block", (props) -> props
		.mapColor(MapColor.SNOW)
		.instrument(NoteBlockInstrument.GUITAR)
		.strength(0.8F)
		.sound(SoundType.WOOL)
		.ignitedByLava(),
		true, CreativeModeTabs.BUILDING_BLOCKS);
	@SuppressWarnings("unchecked")
	public static DeferredBlock<Block>[] DYE_BLOCKS = new DeferredBlock[16];
	static {
		for (int dyeColorId = 0; dyeColorId < 16; dyeColorId++) {
			DyeColor color = DyeColor.byId(dyeColorId);
			DYE_BLOCKS[dyeColorId] = BLOCKS.registerSimpleBlock(color.getName() + "_dye_block", (props) -> props
				.mapColor(color.getMapColor())
				.instrument(NoteBlockInstrument.BASS)
				.strength(2.0F, 3.0F),
				true, CreativeModeTabs.BUILDING_BLOCKS
			);
		}
	}

	// Byte blocks
	public static final DeferredBlock<ByteBlock> BYTE_BLOCK = BLOCKS.registerSimpleByteBlock(
		"byte_block", ByteBlock::new, true, CreativeModeTabs.FUNCTIONAL_BLOCKS
	);
	public static final DeferredBlock<WrappingAddSubByteBlock> WRAPPING_ADD_SUB_BYTE_BLOCK = BLOCKS.registerSimpleByteBlock(
		"wrapping_add_sub_byte_block", WrappingAddSubByteBlock::new, true, CreativeModeTabs.FUNCTIONAL_BLOCKS
	);
	public static final DeferredBlock<WrappingMultDivByteBlock> WRAPPING_MULT_DIV_BYTE_BLOCK = BLOCKS.registerSimpleByteBlock(
		"wrapping_mult_div_byte_block", WrappingMultDivByteBlock::new, true, CreativeModeTabs.FUNCTIONAL_BLOCKS
	);
	public static final DeferredBlock<PrimaryInputByteBlock> PRIMARY_INPUT_BYTE_BLOCK = BLOCKS.registerSimpleByteBlock(
		"primary_input_byte_block", PrimaryInputByteBlock::new, true, CreativeModeTabs.FUNCTIONAL_BLOCKS
	);
	public static final DeferredBlock<SecondaryInputByteBlock> SECONDARY_INPUT_BYTE_BLOCK = BLOCKS.registerSimpleByteBlock(
		"secondary_input_byte_block", SecondaryInputByteBlock::new, true, CreativeModeTabs.FUNCTIONAL_BLOCKS
	);

	// Materials
	static {
		Materials.registerBlocks(BLOCKS);
	}
}
