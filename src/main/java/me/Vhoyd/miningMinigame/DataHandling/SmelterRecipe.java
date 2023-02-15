package me.Vhoyd.miningMinigame.DataHandling;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.Vhoyd.miningMinigame.ItemManager.ItemManager;
import me.Vhoyd.pluginUtility.item.QuickItem;

public enum SmelterRecipe {
	EMPTY(null, null, null),
	HYBRID(QuickItem.create(ItemManager.HYBRID_INGOT, 1), QuickItem.create(ItemManager.IRON, 1),
			QuickItem.create(ItemManager.COPPER, 1)),
	COPPER(QuickItem.create(ItemManager.COPPER, 1), QuickItem.create(ItemManager.COPPER_LUMP, 2),
			QuickItem.create(ItemManager.SAND_PILE, 1)),
	GLASS(QuickItem.create(Material.GLASS, 2, "Silicone Glass"), QuickItem.create(ItemManager.SAND_PILE, 3)),
	STEEL(QuickItem.create(ItemManager.STEEL_CUBE, 1), QuickItem.create(ItemManager.COAL_BLOCK, 1),
			QuickItem.create(ItemManager.IRON_BLOCK, 1)),;

	private ItemStack[] costs;
	private ItemStack result;

	private SmelterRecipe(ItemStack r, ItemStack... costs) {
		result = r;
		ItemStack[] addCoal = new ItemStack[costs.length + 1];
		for (int i = 0; i < costs.length; i++)
			addCoal[i] = costs[i];
		addCoal[costs.length] = QuickItem.create(ItemManager.COAL, 1);
		this.costs = addCoal;
	}

	public ItemStack[] getCosts() {
		return costs;
	}

	public ItemStack getResult() {
		return result;
	}

	public static SmelterRecipe queryRecipe(ItemStack result) {
		for (SmelterRecipe s : SmelterRecipe.values()) {
			if (s == SmelterRecipe.EMPTY)
				continue;
			if (result.equals(s.getResult()))
				return s;
		}
		return null;
	}
}
