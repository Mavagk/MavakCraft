package net.mavakcraft;

public record ColorHSV(int hsv) {
	public ColorHSV(int hue, int saturation, int value) {
		this(hue | (saturation << 8) | (value << 16));
		if (hue > 0xFF || saturation > 0xFF || value > 0xFF) throw new IllegalArgumentException();
	}

	public int getHSV() {
		return hsv;
	}

	public int getHue() {
		return hsv & 0xFF;
	}

	public int getSaturation() {
		return (hsv >> 8) & 0xFF;
	}

	public int getValue() {
		return (hsv >> 16) & 0xFF;
	}
}
