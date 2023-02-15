package me.Vhoyd.miningMinigame.Trackers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

import me.Vhoyd.miningMinigame.station.AbstractStation;

public abstract class AbstractStationTracker {
	protected List<AbstractStation> trackedObjects = new ArrayList<>();
	
	public abstract void remove(AbstractStation object);
	
	public abstract void remove(Location location);
	
	public abstract void add(AbstractStation object);
	
	public abstract void clear();
	
	public AbstractStation query(Location location) {
		for (AbstractStation object : trackedObjects) {
			if (object.getLocation().equals(location)) {
				return object;
			}
		}
		return null;
	}
		
	public List<AbstractStation> getObjects() {
		return trackedObjects;
	}
}
