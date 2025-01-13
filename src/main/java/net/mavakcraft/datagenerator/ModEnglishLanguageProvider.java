package net.mavakcraft.datagenerator;

import javax.annotation.Nonnull;

import net.mavakcraft.Blocks;
import net.mavakcraft.Materials;
import net.mavakcraft.MavakCraft;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.DyeColor;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModEnglishLanguageProvider extends LanguageProvider {
	@Nonnull String locale;
	@Nonnull EnglishDialect dialect;

	public ModEnglishLanguageProvider(PackOutput output, String locale) {
		super(output, MavakCraft.MODID, locale);
		this.locale = locale;
		this.dialect = EnglishDialect.fromLocale(locale);
	}

	@Override
	protected void addTranslations() {
		addItem(MavakCraft.SALT, "Salt");

		addBlock(Blocks.GLOWING_OBSIDIAN, "Glowing Obsidian");
		addBlock(Blocks.ROSE, "Rose");
		addBlock(Blocks.BLUE_ROSE, "Blue Rose");
		addBlock(Blocks.CHARCOAL_BLOCK, "Block of Charcoal");
		addBlock(Blocks.FEATHER_BLOCK, "Feather Feathers");
		addBlock(Blocks.FLINT_BLOCK, "Block of Flint");
		addBlock(Blocks.LEATHER_BLOCK, "Block of Leather");
		addBlock(Blocks.NETHER_STAR_BLOCK, "Nether Star Block");
		addBlock(Blocks.NETHER_WART_BLOCK, "Block of Nether Wart");
		addBlock(Blocks.NETHERITE_SCRAP_BLOCK, "Block of Netherite Scrap");
		addBlock(Blocks.RABBIT_HIDE_BLOCK, "Block of Rabbit Hide");
		addBlock(Blocks.TURTLE_SCUTE_BLOCK, "Block of Turtle Scutes");
		addBlock(Blocks.BYTE_BLOCK, "Byte Block");
		addBlock(Blocks.PRIMARY_INPUT_BYTE_BLOCK, "Primary Input Byte Block");
		addBlock(Blocks.SECONDARY_INPUT_BYTE_BLOCK, "Secondary Input Byte Block");
		addBlock(Blocks.WRAPPING_ADD_SUB_BYTE_BLOCK, "Wrapping Add/Subtract Byte Block");
		addBlock(Blocks.WRAPPING_MULT_DIV_BYTE_BLOCK, "Wrapping Multiply/Divide Byte Block");
		for (int dyeColorId = 0; dyeColorId < 16; dyeColorId++) {
			addBlock(Blocks.DYE_BLOCKS[dyeColorId], getDyeColorName(DyeColor.byId(dyeColorId), dialect) + " Dye Block");
		}
		Materials.generateEnglishName(this);

		add("itemGroup.mavakcraft", "MavakCraft");

		add("mavakcraft.configuration.modItemsInVanillaTabs", "Mod Items in Vanilla Tabs");
		add("mavakcraft.configuration.modItemsInCreativeModeTab", "Mod Items in Creative Mode Tab");
	}

	protected enum EnglishDialect {
		American,
		Commonwealth;

		public static EnglishDialect fromLocale(String locale) {
			switch (locale) {
				case "en_us":
					return American;
				default:
					return Commonwealth;
			}
		}
	}

	String[] AMERICAN_DYE_COLOR_NAMES = {
		"White", "Orange", "Magenta", "Light Blue", "Yellow", "Lime", "Pink", "Gray",
		"Light Gray", "Cyan", "Purple", "Blue", "Brown", "Green", "Red", "Black"
	};
	String[] COMMONWEALTH_DYE_COLOR_NAMES = {
		"White", "Orange", "Magenta", "Light Blue", "Yellow", "Lime", "Pink", "Grey",
		"Light Grey", "Cyan", "Purple", "Blue", "Brown", "Green", "Red", "Black"
	};

	String getDyeColorName(DyeColor dyeColor, EnglishDialect dialect) {
		int colorId = dyeColor.getId();
		switch (dialect) {
			case American:
				return AMERICAN_DYE_COLOR_NAMES[colorId];
			case Commonwealth:
				return COMMONWEALTH_DYE_COLOR_NAMES[colorId];
			default:
				return null;
		}
	}
}
