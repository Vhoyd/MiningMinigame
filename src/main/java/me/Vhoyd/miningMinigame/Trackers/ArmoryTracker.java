package me.Vhoyd.miningMinigame.Trackers;

import org.bukkit.Location;

import me.Vhoyd.miningMinigame.station.AbstractStation;

public class ArmoryTracker extends AbstractStationTracker {
	
	@Override
	public void add(AbstractStation object) {
		trackedObjects.add(object);
	}

	@Override
	public void remove(AbstractStation object) {
		trackedObjects.remove(object);
	}

	@Override
	public void clear() {
		trackedObjects.clear();
		
	}

	@Override
	public void remove(Location location) {
		for (AbstractStation a : trackedObjects) {
			if (a.getLocation().equals(location)) {
				trackedObjects.remove(a);
			}
		}
	}
}
