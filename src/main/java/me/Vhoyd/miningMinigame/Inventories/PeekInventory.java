package me.Vhoyd.miningMinigame.Inventories;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import me.Vhoyd.miningMinigame.ItemManager.ItemManager;

public class PeekInventory implements InventoryHolder {

	private Inventory inv;

	public Inventory getInventory() {
		return inv;
	}

	public PeekInventory(Player player) {
		inv = Bukkit.createInventory(this, 45, player.getName() + "'s inventory");
		for (int i = 0; i < 36; i++) {
			inv.setItem(i, player.getInventory().getItem(i));
		}
		inv.setItem(36, ItemManager.BLANK_PANE);
		inv.setItem(37, player.getEquipment().getHelmet());
		inv.setItem(38, player.getEquipment().getChestplate());
		inv.setItem(39, player.getEquipment().getLeggings());
		inv.setItem(40, player.getEquipment().getBoots());
		inv.setItem(41, ItemManager.BLANK_PANE);
		inv.setItem(42, player.getEquipment().getItemInMainHand());
		inv.setItem(43, player.getEquipment().getItemInOffHand());
		inv.setItem(44, ItemManager.BLANK_PANE);
	}

}
