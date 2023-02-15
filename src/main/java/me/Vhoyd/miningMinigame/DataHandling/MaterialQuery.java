package me.Vhoyd.miningMinigame.DataHandling;

import org.bukkit.Material;

public class MaterialQuery {

	public static MaterialYield query(Material mat) {
		for (MaterialYield compare : MaterialYield.values()) {
			if (compare.getType().equals(mat))
				return compare;
		}
		return null;
	}

}
