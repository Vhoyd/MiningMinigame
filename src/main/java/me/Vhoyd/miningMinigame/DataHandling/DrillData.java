package me.Vhoyd.miningMinigame.DataHandling;

import org.bukkit.inventory.ItemStack;

import me.Vhoyd.miningMinigame.ItemManager.ItemManager;

public enum DrillData {
	BASIC(ItemManager.DRILL_BASIC, 40L, 1, 1), CRUDE(ItemManager.DRILL_CRUDE, 35L, 1, 2),
	MECHANICAL(ItemManager.DRILL_MECHANICAL, 60L, 2, 3), HYBRID(ItemManager.DRILL_HYBRID, 45L, 2, 4),;

	private long rate;
	private ItemStack data;
	private int power;
	private int tier;

	private DrillData(ItemStack d, long r, int p, int t) {
		data = d;
		rate = r;
		power = p;
		tier = t;
	}

	public long getRate() {
		return rate;
	}

	public ItemStack getData() {
		return data;
	}

	public int getPower() {
		return power;
	}

	public int getTier() {
		return tier;
	}

	public static DrillData queryType(ItemStack item) {
		if (item == null)
			return null;
		for (DrillData compare : values()) {
			if (item.isSimilar(compare.getData()))
				return compare;
		}
		return null;
	}

}
