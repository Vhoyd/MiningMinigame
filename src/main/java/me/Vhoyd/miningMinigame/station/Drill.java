package me.Vhoyd.miningMinigame.station;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import me.Vhoyd.miningMinigame.Main;
import me.Vhoyd.miningMinigame.DataHandling.DrillData;
import me.Vhoyd.miningMinigame.DataHandling.MaterialQuery;
import me.Vhoyd.miningMinigame.DataHandling.MaterialYield;
import me.Vhoyd.miningMinigame.Trackers.DrillTracker;

public class Drill extends AbstractStation {

	static {
		tracker = new DrillTracker();		
	}
	
	private Material type;
	private DrillData data;
	private BukkitRunnable timer;

	private Drill(Location l, Material m, DrillData d) {
		location = l;
		type = m;
		data = d;
	}

	public Material getType() {
		return type;
	}

	public DrillData getData() {
		return data;
	}

	public BukkitRunnable getTimer() {
		return timer;
	}

	private void addTimer(BukkitRunnable b) {
		timer = b;
	}
	
	public static void createNew(Location location, ItemStack item) {
		DrillData clone = DrillData.queryType(item);
		final Drill create = new Drill(location, location.clone().add(0, -1, 0).getBlock().getType(), clone);
		BukkitRunnable r = (new BukkitRunnable() {
			int tick = 0;

			public void run() {
				if (tick == 1) {
					create.breakBlock();
				}
				tick = (tick + 1) % 2;
			}
		});
		create.addTimer(r);
		create.getTimer().runTaskTimer(Main.getPlugin(), 0, clone.getRate());
		tracker.add(create);
	}

	public void breakBlock() {
		Item item = null;
		Block surface = location.clone().add(0, -1, 0).getBlock();
		MaterialYield yield = MaterialQuery.query(surface.getType());
		if (yield == null)
			return;
		ItemStack drop = yield.getYield().clone();
		drop.setAmount(getData().getPower());
		item = location.getBlock().getWorld().dropItem(location.clone().add(0.5, 1.5, 0.5), drop);
		surface.setType(Material.BEDROCK);
		for (Player player : Bukkit.getOnlinePlayers())
			player.playSound(location, Sound.UI_STONECUTTER_TAKE_RESULT, 1,
					(float) ((new Random().nextFloat() % 0.4) + 1));
		item.setVelocity(new Vector(0, 0.1, 0));
		final Block at = surface;
		final Material set = yield.getType();
		BukkitRunnable a = new BukkitRunnable() {
			public void run() {
				at.setType(set);
				for (Player player : Bukkit.getOnlinePlayers())
					player.playSound(at.getLocation(), Sound.BLOCK_NETHERITE_BLOCK_PLACE, 1,
							(float) ((new Random().nextFloat() % 0.4) + 1));
			}
		};
		a.runTaskLater(Main.getPlugin(), getData().getRate());

	}

	public void resetBlock() {
		location.clone().add(0, -1, 0).getBlock().setType(getType());
	}

	public static void breakAll() {
		for (int i = 0; i < tracker.getObjects().size(); i++) {
			((Drill)tracker.getObjects().get(i)).breakBlock();
		}
	}
	
	public static void init() {}

}
