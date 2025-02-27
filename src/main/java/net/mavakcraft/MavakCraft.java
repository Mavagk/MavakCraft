package net.mavakcraft;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.mavakcraft.block.byteblock.ByteBlock;
import net.mavakcraft.block.byteblock.PrimaryInputByteBlock;
import net.mavakcraft.block.byteblock.SecondaryInputByteBlock;
import net.mavakcraft.block.byteblock.WrappingAddSubByteBlock;
import net.mavakcraft.block.byteblock.WrappingMultDivByteBlock;
import net.mavakcraft.registry.ModBlocksDeferredRegister;
import net.mavakcraft.registry.ModItemsDeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(MavakCraft.MODID)
@EventBusSubscriber(modid = MavakCraft.MODID, bus = EventBusSubscriber.Bus.MOD)
public class MavakCraft
{
	// Define mod id in a common place for everything to reference
	// The value here should match an entry in the META-INF/neoforge.mods.toml file
	public static final String MODID = "mavakcraft";

	// The logger to log debug messages to
	public static final Logger LOGGER = LogUtils.getLogger();
	// Deferred registers containing game elements that will be registered when the mod entrypoint is executed
	public static final ModBlocksDeferredRegister BLOCKS = new ModBlocksDeferredRegister(MavakCraft.MODID);
	public static final ModItemsDeferredRegister ITEMS = new ModItemsDeferredRegister(MODID);
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

	// Mod blocks
	public static final DeferredBlock<Block> GLOWING_OBSIDIAN = BLOCKS.registerSimpleBlock("glowing_obsidian", (props) -> props
		.mapColor(MapColor.STONE)
		.instrument(NoteBlockInstrument.BASEDRUM)
		.requiresCorrectToolForDrops()
		.strength(35.0F, 1200.0F)
		.lightLevel(state -> 12),
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

	// Mod items
	public static final DeferredItem<Item> SALT = ITEMS.registerSimpleItem("salt", CreativeModeTabs.INGREDIENTS);
	// Materials
	static {
		Materials.registerItems(ITEMS);
	}
	// Register block items for all blocks that are set to have block items generated for them.
	static {
		BLOCKS.registerBlockItems(ITEMS);
	}

	// Mod creative mode tabs
	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("mavakcraft",
		() -> CreativeModeTab.builder()
		.title(Component.translatable("itemGroup.mavakcraft"))
		.withTabsBefore(CreativeModeTabs.COMBAT)
		.icon(() -> GLOWING_OBSIDIAN.get().asItem().getDefaultInstance())
		.displayItems((parameters, output) -> ITEMS.putItemsInModCreativeTab(output))
		.build()
	);

	// The constructor for the mod class is the first code that is run when your mod is loaded.
	// FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
	public MavakCraft(IEventBus modEventBus, ModContainer modContainer) {
		LOGGER.info("Loading MavakCraft");
		// Register our mod's ModConfigSpec so that FML can create and load the config file for us
		modContainer.registerConfig(ModConfig.Type.STARTUP, Config.SPEC);
		// The mod should have an auto generated config menu
		modContainer.registerExtensionPoint(IConfigScreenFactory.class, (mc, parent) -> new ConfigurationScreen(modContainer, parent));
		// Register the deferred registers
		BLOCKS.register(modEventBus);
		ITEMS.register(modEventBus);
		if (Config.MOD_ITEMS_IN_MOD_CREATIVE_TAB.get()) CREATIVE_MODE_TABS.register(modEventBus);
	}

	@SubscribeEvent
	// For adding items to vanilla creative mode tabs.
	public static void buildContents(BuildCreativeModeTabContentsEvent event) {
		if (!Config.MOD_ITEMS_IN_VANILLA_TABS.get()) return;
		ITEMS.putItemsInVanillaTab(event);
	}
}