package com.dre.brewery;

import org.bukkit.Color;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

public class PotionColor {
	public static final PotionColor PINK = new PotionColor(1, PotionType.REGEN, Color.FUCHSIA);
	public static final PotionColor CYAN = new PotionColor(2, PotionType.SPEED, Color.AQUA);
	public static final PotionColor ORANGE = new PotionColor(3, PotionType.FIRE_RESISTANCE, Color.ORANGE);
	public static final PotionColor GREEN = new PotionColor(4, PotionType.POISON, Color.GREEN);
	public static final PotionColor BRIGHT_RED = new PotionColor(5, PotionType.INSTANT_HEAL, Color.fromRGB(255,0,0));
	public static final PotionColor BLUE = new PotionColor(6, PotionType.NIGHT_VISION, Color.NAVY);
	public static final PotionColor BLACK = new PotionColor(8, PotionType.WEAKNESS, Color.BLACK);
	public static final PotionColor RED = new PotionColor(9, PotionType.STRENGTH, Color.fromRGB(196,0,0));
	public static final PotionColor GREY = new PotionColor(10, PotionType.SLOWNESS, Color.GRAY);
	public static final PotionColor WATER = new PotionColor(11, P.use1_9 ? PotionType.WATER_BREATHING : null, Color.BLUE);
	public static final PotionColor DARK_RED = new PotionColor(12, PotionType.INSTANT_DAMAGE, Color.fromRGB(128,0,0));
	public static final PotionColor BRIGHT_GREY = new PotionColor(14, PotionType.INVISIBILITY, Color.SILVER);

	private final int colorId;
	private final PotionType type;
	private final Color color;

	PotionColor(int colorId, PotionType type, Color color) {
		this.colorId = colorId;
		this.type = type;
		this.color = color;
	}

	public PotionColor(Color color) {
		colorId = -1;
		type = WATER.getType();
		this.color = color;
	}

	// gets the Damage Value, that sets a color on the potion
	// offset +32 is not accepted by brewer, so not further destillable
	public short getColorId(boolean destillable) {
		if (destillable) {
			return (short) (colorId + 64);
		}
		return (short) (colorId + 32);
	}

	public PotionType getType() {
		return type;
	}

	public Color getColor() {
		return color;
	}

	@SuppressWarnings("deprecation")
	public void colorBrew(PotionMeta meta, ItemStack potion, boolean destillable) {
		if (P.use1_9) {
			meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
			if (P.use1_11) {
				// BasePotionData was only used for the Color, so starting with 1.12 we can use setColor instead
				meta.setColor(getColor());
			} else {
				meta.setBasePotionData(new PotionData(getType()));
			}
		} else {
			potion.setDurability(getColorId(destillable));
		}
	}

	public static PotionColor fromString(String string) {
		switch (string) {
			case "PINK": return PINK;
			case "CYAN": return CYAN;
			case "ORANGE": return ORANGE;
			case "GREEN": return GREEN;
			case "BRIGHT_RED": return BRIGHT_RED;
			case "BLUE": return BLUE;
			case "BLACK": return BLACK;
			case "RED": return RED;
			case "GREY": return GREY;
			case "WATER": return WATER;
			case "DARK_RED": return DARK_RED;
			case "BRIGHT_GREY": return BRIGHT_GREY;
			default:
				try{
					if (string.length() >= 7) {
						string = string.substring(1);
					}
					return new PotionColor(Color.fromRGB(
						Integer.parseInt(string.substring( 0, 2 ), 16 ),
						Integer.parseInt(string.substring( 2, 4 ), 16 ),
						Integer.parseInt(string.substring( 4, 6 ), 16 )
					));
				} catch (Exception e) {
					return WATER;
				}
		}
	}

}
