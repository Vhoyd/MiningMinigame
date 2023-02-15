package me.Vhoyd.miningMinigame.station;

import org.bukkit.Location;

import me.Vhoyd.miningMinigame.Trackers.AbstractStationTracker;
import me.Vhoyd.pluginUtility.inventory.InteractInventory;


public abstract class AbstractStation {
	protected static AbstractStationTracker tracker;
	protected static InteractInventory staticShop = null;
	protected Location location = null;
	protected InteractInventory objectShop = null;
	
	public static AbstractStationTracker getTracker() {
		return tracker;
	}
	
	public static InteractInventory getStaticShop() {
		return staticShop;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public InteractInventory getShop() {
		return objectShop;
	}
		
}
