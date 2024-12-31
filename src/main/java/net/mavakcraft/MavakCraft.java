package net.mavakcraft;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
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
public class MavakCraft
{
	// Define mod id in a common place for everything to reference
	// The value here should match an entry in the META-INF/neoforge.mods.toml file
	public static final String MODID = "mavakcraft";

	// The logger to log debug messages to
	private static final Logger LOGGER = LogUtils.getLogger();

	// Deferred registers containing game elements that will be registered when the mod entrypoint is executed
	public static final MavakCraftBlocksDeferredRegister BLOCKS = new MavakCraftBlocksDeferredRegister(MODID);
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

	// Mod blocks
	public static final DeferredBlock<Block> GLOWING_OBSIDIAN = BLOCKS.registerSimpleBlock("glowing_obsidian", BlockBehaviour.Properties.of()
		.mapColor(MapColor.COLOR_BLACK)
		.instrument(NoteBlockInstrument.BASEDRUM)
		.requiresCorrectToolForDrops()
		.strength(35.0F, 1200.0F)
		.lightLevel(state -> 12)
	);
	public static final DeferredBlock<Block> CHARCOAL_BLOCK = BLOCKS.registerSimpleBlock("charcoal_block", BlockBehaviour.Properties.of()
		.mapColor(MapColor.COLOR_BLACK)
		.instrument(NoteBlockInstrument.BASEDRUM)
		.requiresCorrectToolForDrops()
		.strength(5.0F, 6.0F)
	);
	public static final DeferredBlock<FlowerBlock> ROSE = BLOCKS.registerSimpleFlower("rose", MobEffects.NIGHT_VISION, 5);
	public static final DeferredBlock<FlowerBlock> BLUE_ROSE = BLOCKS.registerSimpleFlower("blue_rose", MobEffects.NIGHT_VISION, 5);

	// Mod items
	public static final DeferredItem<Item> SALT = ITEMS.registerSimpleItem("salt");

	public static final DeferredItem<BlockItem> GLOWING_OBSIDIAN_ITEM = ITEMS.registerSimpleBlockItem(GLOWING_OBSIDIAN);
	public static final DeferredItem<BlockItem> CHARCOAL_BLOCK_ITEM = ITEMS.registerSimpleBlockItem(CHARCOAL_BLOCK);
	public static final DeferredItem<BlockItem> ROSE_ITEM = ITEMS.registerSimpleBlockItem(ROSE);
	public static final DeferredItem<BlockItem> BLUE_ROSE_ITEM = ITEMS.registerSimpleBlockItem(BLUE_ROSE);

	// Mod creative mode tabs
	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("mavakcraft",
		() -> CreativeModeTab.builder()
		.title(Component.translatable("itemGroup.mavakcraft"))
		.withTabsBefore(CreativeModeTabs.COMBAT)
		.icon(() -> GLOWING_OBSIDIAN_ITEM.get().getDefaultInstance())
		.displayItems((parameters, output) -> {
			output.accept(GLOWING_OBSIDIAN_ITEM.get());
			output.accept(CHARCOAL_BLOCK_ITEM.get());
			output.accept(SALT.get());
			output.accept(ROSE_ITEM.get());
			output.accept(BLUE_ROSE_ITEM.get());
		})
		.build()
	);

	// The constructor for the mod class is the first code that is run when your mod is loaded.
	// FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
	public MavakCraft(IEventBus modEventBus, ModContainer modContainer) {
		LOGGER.info("Loading MavakCraft");
		// Register our mod's ModConfigSpec so that FML can create and load the config file for us
		modContainer.registerConfig(ModConfig.Type.STARTUP, Config.SPEC);
		modContainer.registerExtensionPoint(IConfigScreenFactory.class, (mc, parent) -> new ConfigurationScreen(modContainer, parent));
		// Register the deferred registers
		BLOCKS.register(modEventBus);
		ITEMS.register(modEventBus);
		if (Config.MOD_ITEMS_IN_MOD_CREATIVE_TAB.get()) {
			CREATIVE_MODE_TABS.register(modEventBus);
		}
	}

	@SubscribeEvent
	public static void buildContents(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
			event.accept(ROSE_ITEM);
			event.accept(BLUE_ROSE_ITEM);
		}
		if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
			event.accept(GLOWING_OBSIDIAN_ITEM);
			event.accept(CHARCOAL_BLOCK_ITEM);
		}
		if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
			event.accept(SALT);
		}
	}
}