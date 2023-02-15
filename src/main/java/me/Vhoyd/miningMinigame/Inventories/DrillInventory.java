package me.Vhoyd.miningMinigame.Inventories;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class DrillInventory implements InventoryHolder {

	private Inventory inv;

	public DrillInventory(ItemStack display) {
		inv = Bukkit.createInventory(this, 9, "Drill Information");
		inv.setItem(4, display);
	}

	public Inventory getInventory() {
		return inv;
	}

}
