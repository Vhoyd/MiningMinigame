package me.Vhoyd.miningMinigame.Loops;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class HungerTick extends BukkitRunnable {
	public void run() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.setFoodLevel(20);
		}
	}

}
