package net.mavakcraft;

public record ColorHSA(int hsa) {
	public ColorHSA(int hue, int saturation, int alpha) {
		this(hue | (saturation << 11) | (alpha << 19));
		if (hue > 0xFF * 6 || saturation > 0xFF || alpha > 0xFF) throw new IllegalArgumentException();
	}

	public int hue() {
		return hsa & 0b11111111111;
	}

	public int saturation() {
		return (hsa >> 11) & 0xFF;
	}

	public int alpha() {
		return (hsa >> 19) & 0xFF;
	}

	int hueSection() {
		return hue() / 256;
	}

	int hueModSection() {
		return hue() % 256;
	}

	int hueModSectionInverse() {
		return 255 - hueModSection();
	}

	int desaturate(int in) {
		return 255 - (255 - in) * saturation() / 255;
	}

	public int red() {
		return desaturate(switch (hueSection()) {
			case 0, 5 -> 255;
			case 4 -> hueModSection();
			case 1 -> hueModSectionInverse();
			case 2, 3 -> 0;
			default -> 0;
		});
	}

	public int green() {
		return desaturate(switch (hueSection()) {
			case 1, 2 -> 255;
			case 0 -> hueModSection();
			case 3 -> hueModSectionInverse();
			case 4, 5 -> 0;
			default -> 0;
		});
	}

	public int blue() {
		return desaturate(switch (hueSection()) {
			case 3, 4 -> 255;
			case 2 -> hueModSection();
			case 5 -> hueModSectionInverse();
			case 0, 1 -> 0;
			default -> 0;
		});
	}
}
