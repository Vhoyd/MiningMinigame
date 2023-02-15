package me.Vhoyd.miningMinigame.Dialogue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ShopNoResources {
	private static List<String> options = new ArrayList<String>();

	public static void init() {
		options.add("You don't have the resources for that.");
		options.add("Get more resources first.");
		options.add("You need more resources to get that.");
		options.add("Not enough materials.");
		options.add("Material count is too low.");
	}

	public static String randomLine() {
		return options.get(Math.abs(new Random().nextInt() % options.size()));
	}
}
