package me.Vhoyd.miningMinigame.Trackers;

import org.bukkit.Location;

import me.Vhoyd.miningMinigame.station.AbstractStation;
import me.Vhoyd.miningMinigame.station.Drill;

public class DrillTracker extends AbstractStationTracker {

	@Override
	public void remove(AbstractStation object) {
		Drill drill = (Drill)object;
		drill.getTimer().cancel();
		trackedObjects.remove(drill);
	}

	@Override
	public void add(AbstractStation object) {
		trackedObjects.add(object);
	}

	@Override
	public void clear() {
		for (AbstractStation a : trackedObjects) {
			Drill d = (Drill) a;
			d.getTimer().cancel();
			
		}
		trackedObjects.clear();
		
	}

	@Override
	public void remove(Location location) {
		for (AbstractStation a : trackedObjects) {
			if (a.getLocation().equals(location)) {
				Drill d = (Drill) a;
				d.getTimer().cancel();
				trackedObjects.remove(a);
			}
		}
		
	}

}
