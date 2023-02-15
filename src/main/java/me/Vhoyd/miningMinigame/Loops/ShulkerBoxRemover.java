package me.Vhoyd.miningMinigame.Loops;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class ShulkerBoxRemover extends BukkitRunnable {

	public void run() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			for (ItemStack item : player.getInventory().getContents()) {
				if (item == null)
					continue;
				if (item.getType() == Material.BLACK_SHULKER_BOX || item.getType() == Material.WHITE_SHULKER_BOX) {
					BlockStateMeta meta = (BlockStateMeta) item.getItemMeta();
					ShulkerBox state = (ShulkerBox) meta.getBlockState();
					if (state.getInventory().isEmpty())
						player.getInventory().remove(item);
				}
			}
		}
	}

}
