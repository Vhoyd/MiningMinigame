package me.Vhoyd.miningMinigame;

import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.boss.KeyedBossBar;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import me.Vhoyd.miningMinigame.Commands.Cmds;
import me.Vhoyd.miningMinigame.Commands.TabHandler;
import me.Vhoyd.miningMinigame.Dialogue.ShopNoResources;
import me.Vhoyd.miningMinigame.Dialogue.ShopTooFull;
import me.Vhoyd.miningMinigame.Loops.HungerTick;
import me.Vhoyd.miningMinigame.Loops.ShulkerBoxRemover;
import me.Vhoyd.miningMinigame.listeners.EventHandlers;
import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin {

	private static Plugin plugin;

	@Override
	public void onEnable() {
		Iterator<KeyedBossBar> it = Bukkit.getBossBars();
		while (it.hasNext()) {
			try {
				KeyedBossBar b = it.next();
				Entity e = getEntityByUniqueId(b.getKey().getKey());
				if (e == null) {
					b.removeAll();
					Bukkit.removeBossBar(b.getKey());
				}
			} catch (Exception e) {
				break;
			}
		}
		Cmds c = new Cmds();
		plugin = this;
		getServer().getPluginManager().registerEvents(new EventHandlers(), this);
		getCommand("trader").setExecutor(c);
		getCommand("lefthand").setExecutor(c);
		getCommand("deathsound").setExecutor(c);
		getCommand("item").setExecutor(c);
		getCommand("peek").setExecutor(c);
		getCommand("hide").setExecutor(c);
		getCommand("spin").setExecutor(c);
		getCommand("pteam").setExecutor(c);
		getCommand("show").setExecutor(c);
		getCommand("hand").setExecutor(c);
		getCommand("deathsound").setTabCompleter(new TabHandler());
		getCommand("item").setTabCompleter(new TabHandler());
		ShopTooFull.init();
		ShopNoResources.init();
		ShulkerBoxRemover sbr = new ShulkerBoxRemover();
		sbr.runTaskTimer(Main.getPlugin(), 0, 1L);
		HungerTick ht = new HungerTick();
		ht.runTaskTimer(Main.getPlugin(), 0, 1L);
		System.out.println(ChatColor.GREEN+"pvpPlugin enabled successfully.");
	}

	public static Plugin getPlugin() {
		return plugin;
	}

	@Override
	public void onDisable() {

	}

	public Entity getEntityByUniqueId(String uniqueId) {
		for (World world : Bukkit.getWorlds()) {
			for (Entity entity : world.getEntities()) {
				if (entity.getUniqueId().toString().equals(uniqueId))
					return entity;
			}
		}

		return null;
	}
}
