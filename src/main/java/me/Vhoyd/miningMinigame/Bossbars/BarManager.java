package me.Vhoyd.miningMinigame.Bossbars;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Villager;

public class BarManager {

	private static List<VivBar> vivbars = new ArrayList<VivBar>();

	public static void removeVivBar(VivBar bar) {
		vivbars.remove(bar);
	}

	public static void removeVivBar(int index) {
		vivbars.remove(index);
	}

	public static VivBar getVivBar(int index) {
		return vivbars.get(index);
	}

	public static void addVivBar(VivBar bar) {
		vivbars.add(bar);
	}

	public static VivBar queryVivBar(Villager viv) {
		for (VivBar bar : vivbars) {
			if (bar.getViv().equals(viv))
				return bar;
		}
		return null;
	}

	public static List<VivBar> getVivBars() {
		return vivbars;
	}

}
