package me.Vhoyd.miningMinigame.Dialogue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ShopTooFull {
	private static List<String> options = new ArrayList<String>();

	public static void init() {
		options.add("Your inventory is too full.");
		options.add("Consider making space first.");
		options.add("You don't have the space for that.");
		options.add("Empty your pockets to do that.");
		options.add("There's no room for that in your inventory.");
	}

	public static String randomLine() {
		return options.get(Math.abs(new Random().nextInt() % options.size()));
	}

}
