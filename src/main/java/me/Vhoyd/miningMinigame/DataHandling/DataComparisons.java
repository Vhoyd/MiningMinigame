package me.Vhoyd.miningMinigame.DataHandling;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class DataComparisons {

	public static MaterialYield validSurface(Block surface) {
		Material type = surface.getType();
		for (MaterialYield compare : MaterialYield.values()) {
			if (compare.getType().equals(type))
				return compare;
		}
		return null;
	}

}
