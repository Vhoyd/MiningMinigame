package me.Vhoyd.miningMinigame.DataHandling;

import org.bukkit.inventory.ItemStack;

import me.Vhoyd.miningMinigame.ItemManager.ItemManager;

public enum BreakerQuery {
	FIST(null, 0), BORE(ItemManager.BORE_IRON, 1), CHISEL(ItemManager.CHISEL, 2),;

	private ItemStack item;
	private int tier;

	private BreakerQuery(ItemStack i, int t) {
		item = i;
		tier = t;
	}

	public static BreakerQuery query(ItemStack compare) {
		if (compare == null)
			return FIST;
		for (BreakerQuery q : BreakerQuery.values()) {
			if (compare.equals(q.getItem()))
				return q;
		}
		return FIST;
	}

	public ItemStack getItem() {
		return item;
	}

	public int getTier() {
		return tier;
	}
}
