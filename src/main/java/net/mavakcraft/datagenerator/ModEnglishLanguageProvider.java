package net.mavakcraft.datagenerator;

import java.util.HashMap;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.StringUtils;

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

		addBlock(MavakCraft.GLOWING_OBSIDIAN, "Glowing Obsidian");
		addBlock(MavakCraft.FEATHER_BLOCK, "Feather Feathers");
		addBlock(MavakCraft.NETHER_WART_BLOCK, "Block of Nether Wart");
		addBlock(MavakCraft.BYTE_BLOCK, "Byte Block");
		addBlock(MavakCraft.PRIMARY_INPUT_BYTE_BLOCK, "Primary Input Byte Block");
		addBlock(MavakCraft.SECONDARY_INPUT_BYTE_BLOCK, "Secondary Input Byte Block");
		addBlock(MavakCraft.WRAPPING_ADD_SUB_BYTE_BLOCK, "Wrapping Add/Subtract Byte Block");
		addBlock(MavakCraft.WRAPPING_MULT_DIV_BYTE_BLOCK, "Wrapping Multiply/Divide Byte Block");
		for (int dyeColorId = 0; dyeColorId < 16; dyeColorId++) {
			addBlock(MavakCraft.DYE_BLOCKS[dyeColorId], getDyeColorName(DyeColor.byId(dyeColorId), dialect) + " Dye Block");
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

	static String[] AMERICAN_DYE_COLOR_NAMES = {
		"White", "Orange", "Magenta", "Light Blue", "Yellow", "Lime", "Pink", "Gray",
		"Light Gray", "Cyan", "Purple", "Blue", "Brown", "Green", "Red", "Black"
	};
	static String[] COMMONWEALTH_DYE_COLOR_NAMES = {
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

	static HashMap<String, String> AMERICAN_TO_COMMONWEALTH_WORDS = new HashMap<>();//"aluminum": "aluminium"
	static {
		AMERICAN_TO_COMMONWEALTH_WORDS.put("aluminum", "aluminium");
	}

	/*
	 * Converts a id string such as "my_block" to title case such as "My Block"
	 */
	public String idToTitle(String string) {
		String[] words = string.split("_");
		for (int x = 0; x < words.length; x++) {
			String word = words[x];
			switch (dialect) {
				case American:
					break;
				case Commonwealth:
					String commonwealthName = AMERICAN_TO_COMMONWEALTH_WORDS.get(word);
					if (commonwealthName == null) break;
					word = commonwealthName;
					break;
				default:
					break;
			}
			word = StringUtils.capitalize(word);
			if (x != words.length - 1) word = word + " ";
			words[x] = word;
		}
		return StringUtils.join(words);
	}
}
