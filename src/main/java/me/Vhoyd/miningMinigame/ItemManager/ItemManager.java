package me.Vhoyd.miningMinigame.ItemManager;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemManager {

	public static ItemStack BLOOD_APPLE;

	public static ItemStack BORE;
	public static ItemStack GEARBOX;
	public static ItemStack BORE_IRON;
	public static ItemStack GEARBOX_STURDY;

	public static ItemStack SCRAP;
	public static ItemStack STONE;
	public static ItemStack STICK;
	public static ItemStack PLANK;
	public static ItemStack SHARD;
	public static ItemStack IRON;
	public static ItemStack IRON_BLOCK;
	public static ItemStack COAL_DUST;
	public static ItemStack COAL;
	public static ItemStack COAL_BLOCK;
	public static ItemStack GOLD_FLAKE;
	public static ItemStack GOLD;
	public static ItemStack SAND_PILE;
	public static ItemStack COPPER_LUMP;
	public static ItemStack COPPER;
	public static ItemStack BIG_DIAMOND;
	public static ItemStack STEEL_CUBE;
	public static ItemStack STEEL_PLATE;
	public static ItemStack BLOOD;

	public static ItemStack HYBRID_INGOT;

	public static ItemStack SMELTER;
	public static ItemStack ARMORY;
	public static ItemStack FACTORY;
	public static ItemStack DRILL_BASIC;
	public static ItemStack DRILL_CRUDE;
	public static ItemStack DRILL_MECHANICAL;
	public static ItemStack DRILL_HYBRID;
	public static ItemStack NODE;

	public static ItemStack BLANK_PANE;
	public static ItemStack BREAKER;
	public static ItemStack CHISEL;
	public static ItemStack MOBILE_MENU;

	private static List<ItemStack> notPlaceable = new ArrayList<ItemStack>();
	private static List<ItemStack> items = new ArrayList<ItemStack>();

	private static ItemStack quickCreate(Material material, String name, List<String> lore, int amount) {
		ItemStack temp = new ItemStack(material);
		ItemMeta meta = temp.getItemMeta();
		meta.setDisplayName("§r" + name);
		meta.setLore(lore);
		temp.setItemMeta(meta);
		temp.setAmount(amount);
		items.add(temp);
		return temp;
	}

	private static void createBreaker() {
		List<String> lore = new ArrayList<String>();
		lore.add("§8Useful for breaking drills,");
		lore.add("§8walls, smelters, etc.");
		lore.add("§3§o\"For when fists just");
		lore.add("§3§odon't cut it. Literally.\"");
		BREAKER = quickCreate(Material.NETHERITE_PICKAXE, "§dBuilding Unbuilder", lore, 1);
		ItemMeta meta = BREAKER.getItemMeta();
		meta.setUnbreakable(true);
		meta.addEnchant(Enchantment.DIG_SPEED, 5, true);
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		AttributeModifier modifier = new AttributeModifier("breaker weakness", 0,
				AttributeModifier.Operation.ADD_NUMBER);
		meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		BREAKER.setItemMeta(meta);
	}

	private static void createChisel() {
		List<String> lore = new ArrayList<String>();
		lore.add("§8Allows for manual harvesting");
		lore.add("§8of stone, iron, and coal.");
		lore.add("§3§o\"You do realize drill bores");
		lore.add("§3§owere meant for drills, right?\"");
		CHISEL = quickCreate(Material.PRISMARINE_SHARD, "§aHandheld Chisel", lore, 1);
	}

	private static void createMobileMenu() {
		List<String> lore = new ArrayList<String>();
		lore.add("§3§o\"Pffff, who needs a physical");
		lore.add("§3§ospot to do their work? Lame.\"");
		lore.add("§a§lSoulbound");
		MOBILE_MENU = quickCreate(Material.ENCHANTED_BOOK, "§5Singularity Menu", lore, 1);
	}

	private static void createBlood() {
		List<String> lore = new ArrayList<String>();
		lore.add("§3§o\"I now declare you victim ");
		lore.add("§3§oand vampire.\"");
		BLOOD = quickCreate(Material.REDSTONE, "§4Blood", lore, 1);
	}

	private static void createBlankPane() {
		BLANK_PANE = quickCreate(Material.BLACK_STAINED_GLASS_PANE, "", null, 1);
	}

	private static void createBore() {
		BORE = quickCreate(Material.END_ROD, "Basic Drill Bore", null, 1);
	}

	private static void createGearBox() {
		GEARBOX = quickCreate(Material.LOOM, "Basic Gear Box", null, 1);
	}

	private static void createButton() {
		SCRAP = quickCreate(Material.STONE_BUTTON, "Stone Scrap", null, 1);
	}

	private static void createStone() {
		STONE = quickCreate(Material.STONE, "Unbroken Stone", null, 1);
	}

	private static void createStick() {
		STICK = quickCreate(Material.STICK, "Wooden Stick", null, 1);
	}

	private static void createPlanks() {
		PLANK = quickCreate(Material.OAK_PLANKS, "Wooden Planks", null, 1);
	}

	private static void createShard() {
		SHARD = quickCreate(Material.IRON_NUGGET, "Iron Shard", null, 1);
	}

	private static void createIron() {
		IRON = quickCreate(Material.IRON_INGOT, "Iron Bar", null, 1);
	}

	private static void createIronBlock() {
		IRON_BLOCK = quickCreate(Material.RAW_IRON_BLOCK, "Unrefined Iron Clump", null, 1);
	}

	private static void createCoalDust() {
		COAL_DUST = quickCreate(Material.GUNPOWDER, "Coal Dust", null, 1);
	}

	private static void createCoal() {
		COAL = quickCreate(Material.COAL, "Fuel", null, 1);
	}

	private static void createCoalBlock() {
		COAL_BLOCK = quickCreate(Material.COAL_BLOCK, "Fuel Pile", null, 1);
	}

	private static void createGoldFlake() {
		GOLD_FLAKE = quickCreate(Material.GLOWSTONE_DUST, "Golden Flake", null, 1);
	}

	private static void createGold() {
		GOLD = quickCreate(Material.GOLD_INGOT, "Golden Bar", null, 1);
	}

	private static void createSandPile() {
		SAND_PILE = quickCreate(Material.SAND, "Sand Pile", null, 1);
	}

	private static void createCopperLump() {
		COPPER_LUMP = quickCreate(Material.RAW_COPPER, "Copper Lump", null, 1);
	}

	private static void createCopper() {
		COPPER = quickCreate(Material.COPPER_INGOT, "Copper Bar", null, 1);
	}

	private static void createBigDiamond() {
		BIG_DIAMOND = quickCreate(Material.DIAMOND, "Big Diamond", null, 1);
	}

	private static void createHybridIngot() {
		HYBRID_INGOT = quickCreate(Material.NETHER_BRICK, "Hybrid Ingot", null, 1);
	}

	private static void createSteelCube() {
		STEEL_CUBE = quickCreate(Material.IRON_BLOCK, "Steel Cube", null, 1);
	}

	private static void createSteelPlate() {
		STEEL_PLATE = quickCreate(Material.PAPER, "Steel Plate", null, 1);
	}

	private static void createBoreIron() {
		BORE_IRON = quickCreate(Material.END_ROD, "Iron-tipped Drill Bore", null, 1);
	}

	private static void createGearBoxSturdy() {
		GEARBOX_STURDY = quickCreate(Material.LOOM, "Strengthened Gear Box", null, 1);
	}

	private static void createBloodApple() {
		List<String> lore = new ArrayList<String>();
		lore.add("§3§o\"A blood apple a day scares");
		lore.add("§3§o the doctors away.\"");
		BLOOD_APPLE = quickCreate(Material.APPLE, "Blood Apple", lore, 1);
	}

	private static void createDrillBasic() {
		List<String> lore = new ArrayList<String>();
		lore.add("§6§lDrill quality:");
		lore.add("§8§lBasic");
		lore.add("§3§o\"For all your drilling needs!\"");
		DRILL_BASIC = quickCreate(Material.GRINDSTONE, "Basic Drill", lore, 1);
	}

	private static void createDrillCrude() {
		List<String> lore = new ArrayList<String>();
		lore.add("§6§lDrill quality:");
		lore.add("§8§lBasic");
		lore.add("§3§o\"[Advancement made!] Getting an Upgrade\"");
		DRILL_CRUDE = quickCreate(Material.GRINDSTONE, "Crude Drill", lore, 1);
	}

	private static void createSmelter() {
		List<String> lore = new ArrayList<String>();
		lore.add("§8Takes fuel and resources to");
		lore.add("§8smelt into a hybrid resource.");
		lore.add("§3§o\"Not to be confused with a furnace.\"");
		SMELTER = quickCreate(Material.BLAST_FURNACE, "Smelter", lore, 1);
	}

	private static void createDrillMech() {
		List<String> lore = new ArrayList<String>();
		lore.add("§6§lDrill quality:");
		lore.add("§2§lMechanical");
		lore.add("§3§o\"Slow don't always mean bad.\"");
		DRILL_MECHANICAL = quickCreate(Material.GRINDSTONE, "Mechanical Drill", lore, 1);
	}

	private static void createDrillHybrid() {
		List<String> lore = new ArrayList<String>();
		lore.add("§6§lDrill quality:");
		lore.add("§2§lMechanical");
		lore.add("§3§o\"This new-fangled smelting");
		lore.add("§3§oseems to work alright.\"");
		DRILL_HYBRID = quickCreate(Material.GRINDSTONE, "Hybrid-fused drill", lore, 1);

	}

	private static void createArmory() {
		List<String> lore = new ArrayList<String>();
		lore.add("§8Allows for the forging of");
		lore.add("§8basic armor and weapons.");
		lore.add("§3§o\"Oh yeah, combat is a thing.\"");
		ARMORY = quickCreate(Material.ANVIL, "Armory", lore, 1);
	}

	private static void createNode() {
		List<String> lore = new ArrayList<String>();
		lore.add("§8Place on any grass tile to");
		lore.add("§8change it to a resource tile.");
		lore.add("§3§o\"Nature isn't convenient");
		lore.add("§3§oenough. Let's change that.\"");
		NODE = quickCreate(Material.BEACON, "Resource Node Synthesizer", lore, 1);
	}

	private static void createFactory() {
		List<String> lore = new ArrayList<String>();
		lore.add("§8Allows for the construction of");
		lore.add("§8mobile units to fight for you.");
		lore.add("§3§o\"You and what army? Oh, that one.\"");
		FACTORY = quickCreate(Material.LODESTONE, "Factory", lore, 1);
	}

	public static boolean queryDrill(ItemStack drill) {
		ItemStack[] drills = { DRILL_BASIC, DRILL_CRUDE, DRILL_MECHANICAL, DRILL_HYBRID};
		for (ItemStack drillCheck : drills) {
			if (drillCheck.equals(drill))
				return true;
		}
		return false;
	}

	static {
		createBlankPane();
		createBreaker();
		createChisel();

		createBore();
		createGearBox();
		createBoreIron();
		createGearBoxSturdy();

		createButton();
		createStone();
		createStick();
		createPlanks();
		createShard();
		createIron();
		createIronBlock();
		createCoalDust();
		createCoal();
		createCoalBlock();
		createGoldFlake();
		createGold();
		createSandPile();
		createCopperLump();
		createCopper();
		createBigDiamond();
		createSteelCube();
		createSteelPlate();
		createBlood();

		createHybridIngot();

		createSmelter();
		createDrillBasic();
		createDrillCrude();
		createDrillMech();
		createDrillHybrid();
		createArmory();
		createFactory();
		createNode();

		createMobileMenu();

		createBloodApple();

		notPlaceable.add(GEARBOX);
		notPlaceable.add(BORE);
		notPlaceable.add(SCRAP);
		notPlaceable.add(BORE_IRON);
		notPlaceable.add(SAND_PILE);
		notPlaceable.add(GEARBOX_STURDY);
		notPlaceable.add(STEEL_CUBE);
		notPlaceable.add(BLOOD);
	}

	public static boolean customItem(ItemStack item) {
		for (ItemStack compare : items) {
			if (compare.isSimilar(item))
				return true;
		}
		return false;
	}

	public static List<ItemStack> getItems() {
		return items;
	}

	public static boolean canPlace(ItemStack item) {
		for (ItemStack compare : notPlaceable) {
			if (compare.isSimilar(item))
				return false;
		}
		return true;
	}

}
