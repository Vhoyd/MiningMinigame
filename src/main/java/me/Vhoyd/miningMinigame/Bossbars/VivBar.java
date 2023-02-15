package me.Vhoyd.miningMinigame.Bossbars;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import me.Vhoyd.miningMinigame.Main;

public class VivBar {

	private BossBar bar;
	private Villager viv;

	public void addPlayer(Player player) {
		bar.addPlayer(player);
	}

	public BossBar getBar() {
		return bar;
	}

	public VivBar(Villager viv, BarColor color) {
		this.viv = viv;
		String title = viv.getCustomName() + " - " + String.valueOf(Math.ceil(viv.getHealth() * 10) / 10) + "/"
				+ String.valueOf(Math.round(viv.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() * 10) / 10);
		bar = Bukkit.createBossBar(new NamespacedKey(Main.getPlugin(), viv.getUniqueId().toString()), title, color,
				BarStyle.SOLID);
		bar.setVisible(true);
		update();
	}

	public VivBar(Villager viv, BossBar bar) {
		this.bar = bar;
		this.viv = viv;
		update();
	}

	public void update() {
		bar.setProgress(viv.getHealth() / viv.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
		String title = viv.getCustomName() + " - " + String.valueOf(Math.ceil((viv.getHealth()) * 10) / 10) + "/"
				+ String.valueOf(Math.round(viv.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() * 10) / 10);
		bar.setTitle(title);
	}

	public Villager getViv() {
		return viv;
	}

	public void delete() {
		bar.removeAll();
	}

}
