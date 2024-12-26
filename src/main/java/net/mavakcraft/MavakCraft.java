package net.mavakcraft;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
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
	public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

	// Mod items
	public static final DeferredItem<Item> SALT = ITEMS.registerSimpleItem("salt");

	// The constructor for the mod class is the first code that is run when your mod is loaded.
	// FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
	public MavakCraft(IEventBus modEventBus, ModContainer modContainer) {
		LOGGER.info("Loading MavakCraft");
		// Register our mod's ModConfigSpec so that FML can create and load the config file for us
		modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
		// Register the deferred registers
		BLOCKS.register(modEventBus);
		ITEMS.register(modEventBus);
	}
}
