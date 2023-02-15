package me.Vhoyd.miningMinigame.station;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;

import me.Vhoyd.miningMinigame.ItemManager.ItemManager;
import me.Vhoyd.miningMinigame.Trackers.ArmoryTracker;
import me.Vhoyd.pluginUtility.inventory.InteractInventory;
import me.Vhoyd.pluginUtility.item.QuickItem;

public class Armory extends AbstractStation {
	
	static {
		tracker = new ArmoryTracker();
		InteractInventory inv = new InteractInventory("Armory shop", null, null, Sound.BLOCK_SMITHING_TABLE_USE);
		inv.addPurchase(QuickItem.create(ItemManager.STEEL_PLATE, 6), QuickItem.create(ItemManager.STEEL_CUBE, 1),
				QuickItem.create(ItemManager.BIG_DIAMOND, 12));
		inv.addPurchase(QuickItem.create(Material.IRON_SWORD, 1, "Steel Blade"),
				QuickItem.create(ItemManager.STEEL_PLATE, 2), QuickItem.create(ItemManager.STICK, 2));
		inv.addPurchase(QuickItem.create(Material.SHIELD, 1, "Steel Shield"),
				QuickItem.create(ItemManager.STEEL_PLATE, 3), QuickItem.create(ItemManager.PLANK, 6));
		inv.addPurchase(QuickItem.create(Material.IRON_HELMET, 1, "Steel Helm"),
				QuickItem.create(ItemManager.STEEL_PLATE, 5));
		inv.addPurchase(QuickItem.create(Material.IRON_CHESTPLATE, 1, "Steel Breastplate"),
				QuickItem.create(ItemManager.STEEL_CUBE, 2), QuickItem.create(ItemManager.STEEL_PLATE, 4));
		inv.addPurchase(QuickItem.create(Material.IRON_LEGGINGS, 1, "Steel Platelegs"),
				QuickItem.create(ItemManager.STEEL_CUBE, 1), QuickItem.create(ItemManager.STEEL_PLATE, 5));
		inv.addPurchase(QuickItem.create(Material.IRON_BOOTS, 1, "Steel boots"),
				QuickItem.create(ItemManager.STEEL_PLATE, 4));
		staticShop = inv;
	}
	
	private Armory(Location location) {
		this.location = location;
		objectShop = staticShop;
	}
	
	public static void createNew(Location location) {
		Armory create = new Armory(location);
		tracker.add(create);
	}
	
	public static void init() {}
}
