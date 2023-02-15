package me.Vhoyd.miningMinigame.DataHandling;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.Vhoyd.miningMinigame.ItemManager.ItemManager;
import me.Vhoyd.pluginUtility.item.QuickItem;

public enum MaterialYield {
	GRASS(Material.GRASS_BLOCK, QuickItem.create(Material.GRASS_BLOCK, 0), 0),
	WOOD(Material.OAK_LOG, ItemManager.STICK, 0), 
	SAND(Material.SAND, ItemManager.SAND_PILE, 0),
	STONE(Material.STONE, ItemManager.SCRAP, 1), 
	COAL(Material.COAL_BLOCK, ItemManager.COAL_DUST, 2),
	IRON(Material.IRON_BLOCK, ItemManager.SHARD, 2), 
	GOLD(Material.GOLD_BLOCK, ItemManager.GOLD_FLAKE, 3),
	COPPER(Material.WAXED_COPPER_BLOCK, ItemManager.COPPER_LUMP, 3),
	DIAMOND(Material.DIAMOND_BLOCK, ItemManager.BIG_DIAMOND, 4),
	BLOODBRICK(Material.CRIMSON_HYPHAE, ItemManager.BLOOD, 4);
//	MAGMA(Material.MAGMA_BLOCK,4),
//	OBSIDIAN(Material.OBSIDIAN,5),
//	COBSIDIAN(Material.CRYING_OBSIDIAN, 5),
//	NETHERITE(Material.NETHERITE_BLOCK,6),
	;

	private Material type;
	private ItemStack yield;
	private int tier;

	private MaterialYield(Material m, ItemStack y, int t) {
		type = m;
		yield = y;
		tier = t;
	}

	public Material getType() {
		return type;
	}

	public ItemStack getYield() {
		return yield;
	}

	public int getTier() {
		return tier;
	}
}
