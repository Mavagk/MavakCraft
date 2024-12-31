package net.mavakcraft;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
@EventBusSubscriber(modid = MavakCraft.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Config {
	private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

	public static final ModConfigSpec.BooleanValue MOD_ITEMS_IN_MOD_CREATIVE_TAB = BUILDER
		.comment("Should the mod add a creative mode tab where MavakCraft items can be found.")
		.gameRestart()
		.define("modItemsInCreativeModeTab", true);

	public static final ModConfigSpec.BooleanValue MOD_ITEMS_IN_VANILLA_TABS = BUILDER
		.comment("Should the mod items be findable in vanilla creative mode tabs.")
		.gameRestart()
		.define("modItemsInVanillaTabs", false);

	static final ModConfigSpec SPEC = BUILDER.build();

	@SubscribeEvent
	static void onLoad(final ModConfigEvent event) {
	}
}
