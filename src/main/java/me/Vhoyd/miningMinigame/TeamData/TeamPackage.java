package me.Vhoyd.miningMinigame.TeamData;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.scoreboard.Team;

public class TeamPackage implements InventoryHolder {

	private static List<TeamPackage> packages = new ArrayList<TeamPackage>();

	private Team team;
	private String name;
	private Villager viv;
	private Inventory sharedInventory;

	public TeamPackage(Team team, Villager viv) {
		this.team = team;
		name = team.getName();
		this.viv = viv;
		packages.add(this);
		sharedInventory = Bukkit.createInventory(this, 54, team.getName() + " unified inventory");
	}

	public Team getTeam() {
		return team;
	}

	public Inventory getInventory() {
		return sharedInventory;
	}

	public String getName() {
		return name;
	}

	public Villager getVillager() {
		return viv;
	}

	public static TeamPackage query(Team team) {
		for (TeamPackage p : packages) {
			if (p.getTeam().equals(team))
				return p;
		}
		return null;
	}

	public static TeamPackage query(String name) {
		for (TeamPackage p : packages) {
			if (p.getName().equals(name))
				return p;
		}
		return null;
	}

	public static TeamPackage query(Villager viv) {
		for (TeamPackage p : packages) {
			if (p.getVillager().equals(viv))
				return p;
		}
		return null;
	}

	public static List<TeamPackage> getPackages() {
		return packages;
	}
}
