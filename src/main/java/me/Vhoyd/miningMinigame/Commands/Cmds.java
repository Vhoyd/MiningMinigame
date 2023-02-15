package me.Vhoyd.miningMinigame.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import me.Vhoyd.miningMinigame.Main;
import me.Vhoyd.miningMinigame.Bossbars.BarManager;
import me.Vhoyd.miningMinigame.Bossbars.VivBar;
import me.Vhoyd.miningMinigame.Inventories.PeekInventory;
import me.Vhoyd.miningMinigame.ItemManager.ItemManager;
import me.Vhoyd.miningMinigame.Loops.ParticleOrbit;
import me.Vhoyd.pluginUtility.enchantment.EnchantmentName;
import me.Vhoyd.pluginUtility.inventory.InteractInventory;
import me.Vhoyd.pluginUtility.item.QuickItem;
import me.Vhoyd.pluginUtility.string.CaseHandler;
import me.Vhoyd.pluginUtility.string.NumeralHandler;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

public class Cmds implements CommandExecutor {

	private static Scoreboard currentScoreboard;

	private static InteractInventory traderMenu;
	private Scoreboard scoreboard;

	static {
		InteractInventory drills = new InteractInventory("Drills", null, null, Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
		drills.addPurchase(QuickItem.create(ItemManager.DRILL_BASIC, 1), QuickItem.create(ItemManager.BORE, 2),
				QuickItem.create(ItemManager.PLANK, 3));
		drills.addPurchase(QuickItem.create(ItemManager.DRILL_CRUDE, 1), QuickItem.create(ItemManager.BORE, 2),
				QuickItem.create(ItemManager.GEARBOX, 3));
		drills.addPurchase(QuickItem.create(ItemManager.DRILL_MECHANICAL, 1), QuickItem.create(ItemManager.BORE_IRON, 2),
				QuickItem.create(ItemManager.GEARBOX, 3));
		drills.addPurchase(QuickItem.create(ItemManager.DRILL_HYBRID, 1), QuickItem.create(ItemManager.GEARBOX_STURDY, 3),
				QuickItem.create(ItemManager.BORE_IRON, 3));

		InteractInventory utility = new InteractInventory("Utility", null, null, Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
		utility.addPurchase(QuickItem.create(ItemManager.BREAKER, 1), null, null);
		utility.addPurchase(QuickItem.create(ItemManager.CHISEL, 1), QuickItem.create(ItemManager.HYBRID_INGOT, 3),
				QuickItem.create(ItemManager.BORE_IRON, 2));
//		utility.addPurchase(QuickItem.create(ItemManager.mobileMenu, 1), QuickItem.create(ItemManager.stick, 1));

		InteractInventory materials = new InteractInventory("Raw Materials", null, null,
				Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
		materials.addPurchase(QuickItem.create(ItemManager.PLANK, 1), QuickItem.create(ItemManager.STICK, 2));
		materials.addPurchase(QuickItem.create(ItemManager.STONE, 1), QuickItem.create(ItemManager.SCRAP, 4));
		materials.addPurchase(QuickItem.create(ItemManager.COAL, 2), QuickItem.create(ItemManager.COAL_DUST, 5),
				QuickItem.create(ItemManager.STICK, 2));
		materials.addPurchase(QuickItem.create(ItemManager.IRON, 1), QuickItem.create(ItemManager.SHARD, 5),
				QuickItem.create(ItemManager.SCRAP, 3));
		materials.addPurchase(QuickItem.create(ItemManager.COAL_BLOCK, 1), QuickItem.create(ItemManager.COAL, 8),
				QuickItem.create(ItemManager.COAL_DUST, 4));
		materials.addPurchase(QuickItem.create(ItemManager.IRON_BLOCK, 1), QuickItem.create(ItemManager.IRON, 8),
				QuickItem.create(ItemManager.SHARD, 4));

		InteractInventory components = new InteractInventory("Components", null, null,
				Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
		components.addPurchase(QuickItem.create(ItemManager.BORE, 1), QuickItem.create(ItemManager.STICK, 4),
				QuickItem.create(ItemManager.PLANK, 2));
		components.addPurchase(QuickItem.create(ItemManager.GEARBOX, 1), QuickItem.create(ItemManager.STICK, 5),
				QuickItem.create(ItemManager.STONE, 2));
		components.addPurchase(QuickItem.create(ItemManager.BORE_IRON, 1), QuickItem.create(ItemManager.BORE, 1),
				QuickItem.create(ItemManager.IRON, 3));
		components.addPurchase(QuickItem.create(ItemManager.GEARBOX_STURDY, 1),
				QuickItem.create(ItemManager.HYBRID_INGOT, 5), QuickItem.create(ItemManager.IRON, 2));

		InteractInventory manufacturing = new InteractInventory("Manufacturing", null, null,
				Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
		manufacturing.addPurchase(QuickItem.create(ItemManager.SMELTER, 1), QuickItem.create(ItemManager.STONE, 10),
				QuickItem.create(ItemManager.IRON, 5));
		manufacturing.addPurchase(QuickItem.create(ItemManager.ARMORY, 1), QuickItem.create(ItemManager.STEEL_CUBE, 1),
				QuickItem.create(ItemManager.BIG_DIAMOND, 10), QuickItem.create(ItemManager.HYBRID_INGOT, 2),
				QuickItem.create(ItemManager.IRON, 4));
		manufacturing.addPurchase(QuickItem.create(ItemManager.FACTORY, 1), QuickItem.create(ItemManager.STEEL_PLATE, 3),
				QuickItem.create(ItemManager.STONE, 8), QuickItem.create(ItemManager.BLOOD, 4));

		InteractInventory consumables = new InteractInventory("Consumables", null, null, Sound.ENTITY_GENERIC_EAT);
		consumables.addPurchase(QuickItem.create(ItemManager.BLOOD_APPLE, 1), QuickItem.create(ItemManager.BLOOD, 30));
		consumables.addPurchase(QuickItem.create(ItemManager.NODE, 1), null, null);
		consumables.addPurchase(QuickItem.create(ItemManager.MOBILE_MENU, 1), QuickItem.create(ItemManager.STICK, 1));
//		consumables.addPurchase(QuickItem.create(Material.ENDER_PEARL, 1, "Quiet pearl"), null, null);

		InteractInventory menu = new InteractInventory("Material shop", null, null, Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
		menu.addCategory(QuickItem.create(Material.STONE, 1, "Raw Materials"), materials);
		menu.addCategory(QuickItem.create(Material.LOOM, 1, "Components"), components);
		menu.addCategory(QuickItem.create(Material.NETHERITE_PICKAXE, 1, "Utility"), utility);
		menu.addCategory(QuickItem.create(Material.GRINDSTONE, 1, "Drills"), drills);
		menu.addCategory(QuickItem.create(Material.ANVIL, 1, "Manufacturing"), manufacturing);
		menu.addCategory(QuickItem.create(Material.APPLE, 1, "Consumables"), consumables);
		traderMenu = menu;

	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player))
			return false;
		final Player player = (Player) sender;
		if (label.equalsIgnoreCase("trader")) {
			Villager deploy = sender.getServer().getWorld("world").spawn(player.getLocation(), Villager.class);
			deploy.setProfession(Villager.Profession.NITWIT);
			deploy.setSilent(true);
			deploy.setCustomName(ChatColor.DARK_PURPLE + "V.I.V");
			deploy.setCustomNameVisible(true);
			((LivingEntity) deploy).setAI(false);
			deploy.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(2000);
			deploy.setHealth(2000);
			VivBar v = new VivBar(deploy, BarColor.PURPLE);
			for (Player p : Bukkit.getOnlinePlayers())
				v.addPlayer(p);
			BarManager.addVivBar(v);

		} else if (label.equalsIgnoreCase("lefthand")) {
			BukkitRunnable test = new BukkitRunnable() {
				private int ticks = 0;

				public void run() {
					if (ticks == 20)
						this.cancel();
					if (ticks % 2 == 0) {
						((LivingEntity) player).swingOffHand();
					} else {
						((LivingEntity) player).swingMainHand();
					}
					ticks++;
				}
			};
			test.runTaskTimer(Main.getPlugin(), 0, 5L);
		} else if (label.equalsIgnoreCase("deathsound")) {
			if (args.length > 0) {
				String assemble = "";
				for (String arg : args) {
					assemble += arg.toUpperCase() + "_";
				}
				assemble = assemble.substring(0, assemble.length() - 1);
				Sound set;
				try {
					set = Sound.valueOf(assemble);
				} catch (Exception e) {
					sender.sendMessage(ChatColor.RED + "Unrecognized sound name.");
					return false;
				}
				player.getPersistentDataContainer().set(new NamespacedKey(Main.getPlugin(), "deathsound"),
						PersistentDataType.STRING, set.toString());
				player.sendMessage(
						"Successfully set custom deathsound to " + set.toString().toLowerCase().replace('_', '.'));
			} else {
				player.sendMessage(ChatColor.RED + "Please input a sound name.");
			}
		} else if (label.equalsIgnoreCase("item")) {
			if (args.length > 0) {
				ItemStack item = null;
				String str = args[0].replace('_', ' ');
				for (ItemStack i : ItemManager.getItems()) {
					if (str.equalsIgnoreCase(i.getItemMeta().getDisplayName()))
						item = i.clone();
				}
				if (item == null) {
					player.sendMessage(ChatColor.RED + "Unrecognized item name.");
					return false;
				}
				int amount = 1;
				try {
					amount = Integer.valueOf(args[1]);
				} catch (Exception e) {
					;
				}
				item.setAmount(amount);
				player.getInventory().addItem(item);
				player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
			} else {
				player.sendMessage(ChatColor.RED + "Please input an item name.");
			}
		} else if (label.equalsIgnoreCase("peek")) {
			if (args.length == 0) {
				sender.sendMessage(ChatColor.RED + "Please input a player name.");
				return false;
			}
			Player target = Bukkit.getPlayer(args[0]);
			if (target == null) {
				sender.sendMessage(ChatColor.RED + "Unknown player name.");
				return false;
			}
			player.openInventory(new PeekInventory(target).getInventory());
		} else if (label.equalsIgnoreCase("hide")) {
			for (Player target : Bukkit.getOnlinePlayers()) {
				target.hidePlayer(Main.getPlugin(), player);
			}
			sender.sendMessage("You are now hidden.");
		} else if (label.equalsIgnoreCase("show")) {
			for (Player target : Bukkit.getOnlinePlayers()) {
				target.showPlayer(Main.getPlugin(), player);
			}
			sender.sendMessage("You are now visible.");
		} else if (label.equalsIgnoreCase("spin")) {
			ParticleOrbit orbit1 = new ParticleOrbit(0, Particle.FLAME, player);
			orbit1.runTaskTimer(Main.getPlugin(), 0, 1L);
			ParticleOrbit orbit2 = new ParticleOrbit(Math.PI, Particle.FLAME, player);
			orbit2.runTaskTimer(Main.getPlugin(), 0, 1L);
		} else if (label.equalsIgnoreCase("pteam")) {
			if (args.length > 0) {
				Team team = scoreboard.getTeam(args[0]);
				if (team == null) {
					team = scoreboard.registerNewTeam(args[0]);
					team.setAllowFriendlyFire(false);
					team.setCanSeeFriendlyInvisibles(true);
					team.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.FOR_OTHER_TEAMS);
					team.setOption(Team.Option.DEATH_MESSAGE_VISIBILITY, Team.OptionStatus.ALWAYS);
					team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.FOR_OWN_TEAM);
					team.setPrefix(ChatColor.valueOf(args[0].toUpperCase()) + "[" + args[0].toUpperCase() + "] ");
					team.setColor(ChatColor.valueOf(args[0].toUpperCase()));
				}
				team.addEntry(player.getName());
				player.setScoreboard(scoreboard);
			}
		} else if (label.equalsIgnoreCase("hand")) {
			ItemStack hand = player.getInventory().getItemInMainHand();
			if (hand == null || hand.getItemMeta() == null) {
				player.sendMessage(ChatColor.RED + "You have an empty hand!");
				return false;
			}
			ItemMeta meta = hand.getItemMeta();
			String type = CaseHandler.capitalize(hand.getType().toString().toLowerCase().replace('_', ' '));
			StringBuilder ItemData = new StringBuilder(meta.hasDisplayName() ? meta.getDisplayName() : type);
			List<Enchantment> enchants = new ArrayList<Enchantment>();
			for (Enchantment add : meta.getEnchants().keySet())
				enchants.add(add);
			ItemData.append("\n§r§7");
			for (int i = 0; i < meta.getEnchants().size(); i++) {
				Enchantment e = enchants.get(i);
				String[] split = e.toString().split(",");
				String eName = split[1].substring(1, split[1].length() - 1);
				String name = "";
				for (EnchantmentName n : EnchantmentName.values()) {
					if (n.toString().equals(eName)) {
						name = n.getName();
					}
				}
				int value = meta.getEnchants().get(e);
				ItemData.append(name + " " + NumeralHandler.getNumeral(value) + "\n");
			}
			ItemData.append("§r");
			if (meta.hasLore()) {
				for (String line : meta.getLore()) {
					ItemData.append(line);
					if (!line.equals(meta.getLore().get(meta.getLore().size() - 1)))
						ItemData.append("\n");
				}
			}
			Text toDisplay = new Text(ItemData.toString());
			TextComponent mainGroup = new TextComponent("<" + player.getDisplayName() + "> ");
			boolean enchantmentColor = !meta.getEnchants().isEmpty();
			String itemName = meta.getDisplayName().equals("")
					? CaseHandler.capitalize(hand.getType().toString().toLowerCase().replace('_', ' '))
					: meta.getDisplayName();
			TextComponent displayItem = new TextComponent(
					(enchantmentColor ? "§b" : "") + "[" + itemName + (enchantmentColor ? "§b" : "§f") + "]");
			displayItem.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, toDisplay));
			mainGroup.addExtra(displayItem);
			for (Player p : player.getServer().getOnlinePlayers()) {
				p.spigot().sendMessage(mainGroup);
			}
		}
		return false;
	}

	public static InteractInventory getMenu() {
		return traderMenu;
	}

	public Cmds() {
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		scoreboard = manager.getNewScoreboard();
		Objective objective = scoreboard.registerNewObjective("Teams", "dummy", "Teams");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		Score score = objective.getScore("red");
		score.setScore(1);
		currentScoreboard = scoreboard;
	}

	public static Scoreboard getCurrentScoreboard() {
		return currentScoreboard;
	}
}
