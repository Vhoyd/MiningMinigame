package me.Vhoyd.miningMinigame.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.inventory.ItemStack;

import me.Vhoyd.miningMinigame.ItemManager.ItemManager;

public class TabHandler implements TabCompleter {

	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if (command.getLabel().equalsIgnoreCase("deathsound")) {
			if (args.length > 0) {
				return handleArguments(sender, command, Sound.values(), args);
			}
		} else if (command.getLabel().equalsIgnoreCase("item")) {
			if (args.length == 1) {
				List<String> names = new ArrayList<String>();
				for (ItemStack item : ItemManager.getItems())
					names.add(item.getItemMeta().getDisplayName().toLowerCase().replace(' ', '_'));
				String compare = args[args.length - 1];
				List<String> arguments = new ArrayList<String>();
				for (String name : names) {
					try {
						if (compare.length() > 0 && !compare.equals(name.substring(0, compare.length())))
							continue;
					} catch (Exception e) {
						continue;
					}
					try {
						if (!arguments.contains(name))
							arguments.add(name);
					} catch (Exception e) {
						return null;
					}
				}
				return arguments;
			}
		}
		return null;
	}

	public List<String> handleArguments(CommandSender sender, Command command, Object[] values, String[] args) {
		String compare = args[args.length - 1];
		List<String> arguments = new ArrayList<String>();
		for (Object value : values) {
			String[] check = value.toString().toLowerCase().split("_");
			if (check.length < args.length - 1) {
				continue;
			}
			try {
				if (compare.length() > 0 && !compare.equals(check[args.length - 1].substring(0, compare.length())))
					continue;
			} catch (Exception e) {
				continue;
			}
			boolean cont = false;
			for (int i = 0; i < args.length - 1; i++) {
				if (!args[i].equals(check[i])) {
					cont = true;
					break;
				}
			}
			if (cont)
				continue;
			try {
				if (!arguments.contains(check[args.length - 1]))
					arguments.add(check[args.length - 1]);
			} catch (Exception e) {
				return null;
			}
		}
		return arguments;
	}

	public List<String> handleArguments(CommandSender sender, Command command, List<String> values, String[] args) {
		String compare = args[args.length - 1];
		List<String> arguments = new ArrayList<String>();
		for (Object value : values) {
			String[] check = value.toString().toLowerCase().split("_");
			if (check.length < args.length - 1) {
				continue;
			}
			try {
				if (compare.length() > 0 && !compare.equals(check[args.length - 1].substring(0, compare.length())))
					continue;
			} catch (Exception e) {
				continue;
			}
			boolean cont = false;
			for (int i = 0; i < args.length - 1; i++) {
				if (!args[i].equals(check[i])) {
					cont = true;
					break;
				}
			}
			if (cont)
				continue;
			try {
				if (!arguments.contains(check[args.length - 1]))
					arguments.add(check[args.length - 1]);
			} catch (Exception e) {
				return null;
			}
		}
		return arguments;
	}

}
