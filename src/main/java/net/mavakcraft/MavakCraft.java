package net.mavakcraft;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.mavakcraft.registry.ModItemsDeferredRegister;

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
	public static final ModItemsDeferredRegister ITEMS = new ModItemsDeferredRegister(MODID);
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

	// Mod items
	public static final DeferredItem<Item> SALT = ITEMS.registerSimpleItem("salt", CreativeModeTabs.INGREDIENTS);
	// Materials
	static {
		Materials.registerItems(ITEMS);
	}
	// Register block items for all blocks that are set to have block items generated for them.
	{
		Blocks.BLOCKS.registerBlockItems(ITEMS);
	}

	// Mod creative mode tabs
	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("mavakcraft",
		() -> CreativeModeTab.builder()
		.title(Component.translatable("itemGroup.mavakcraft"))
		.withTabsBefore(CreativeModeTabs.COMBAT)
		.icon(() -> Blocks.GLOWING_OBSIDIAN.get().asItem().getDefaultInstance())
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
		Blocks.BLOCKS.register(modEventBus);
		ITEMS.register(modEventBus);
		if (Config.MOD_ITEMS_IN_MOD_CREATIVE_TAB.get()) CREATIVE_MODE_TABS.register(modEventBus);
	}

	@SubscribeEvent
	// For adding items to vanilla creative mode tabs.
	public static void buildContents(BuildCreativeModeTabContentsEvent event) {
		if (!Config.MOD_ITEMS_IN_VANILLA_TABS.get()) return;
		ITEMS.putItemsInVanillaTab(event);
	}

	/*
	 * Converts a id string such as "my_block" to title case such as "My Block"
	 */
	public static String idToTitle(String string) {
		string = StringUtils.capitalize(string.replace('_', ' '));
		boolean doCapitalize = true;
		for (int x = 0; x < string.length(); x++) {
			char chr = string.charAt(x);
			if (doCapitalize) {
				char upper = Character.toTitleCase(chr);
				string = string.replace(" " + chr, " " + upper);
			}
			doCapitalize = false;
			if (chr == ' ') doCapitalize = true;
		}
		return string;
	}
}