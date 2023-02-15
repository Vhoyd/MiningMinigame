package me.Vhoyd.miningMinigame.station;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import me.Vhoyd.miningMinigame.Main;
import me.Vhoyd.miningMinigame.ActionManaging.Shop.SmelterShopAction;
import me.Vhoyd.miningMinigame.DataHandling.SmelterRecipe;
import me.Vhoyd.pluginUtility.inventory.InteractInventory;
import me.Vhoyd.pluginUtility.inventory.InteractInventoryAction;

public class Smelter {
	private static List<Smelter> smelters = new ArrayList<Smelter>();
	private static InteractInventory shop;

	private Location location;
	private SmelterRecipe currentRecipe = SmelterRecipe.EMPTY;
	private InteractInventory inventory;

	static {
		InteractInventory inventory = new InteractInventory("Smelting menu", null, null, Sound.BLOCK_FIRE_AMBIENT);
		for (SmelterRecipe recipe : SmelterRecipe.values()) {
			if (recipe == SmelterRecipe.EMPTY)
				continue;
			SmelterShopAction action = new SmelterShopAction(recipe) {

				@Override
				public void run(final InventoryClickEvent e) {
					SmelterRecipe recipe = SmelterRecipe.queryRecipe(shop.query(e.getCurrentItem()).stripPreviewCost());
					if (recipe == null)
						return;
					this.setRecipe(recipe);
					InventoryState state = this.validInventory(e.getWhoClicked().getInventory(),
							getShop(e.getClickedInventory()).query(e.getCurrentItem()));
					this.handleStateSound(state, getShop(e.getClickedInventory()), (Player) e.getWhoClicked());
					if (state == InteractInventoryAction.InventoryState.VALID) {
						final ItemStack drop = recipe.getResult();
						final Location where = getShop(e.getClickedInventory()).getLocation();
						final Player player = getShop(e.getClickedInventory()).getPlayer();
						this.removeItems(e.getWhoClicked().getInventory(),
								getShop(e.getClickedInventory()).query(e.getCurrentItem()));
						BukkitRunnable run = new BukkitRunnable() {
							private int tick = 200;

							public void run() {
								tick--;
								Location place = (player != null ? player.getLocation().clone().add(-0.5, 0, -0.5)
										: where);
								if (tick % 30 == 0) {

									place.getWorld().playSound(place, Sound.BLOCK_FURNACE_FIRE_CRACKLE, 1, 1);
								}
								if (Math.abs(new Random().nextInt() % 7) == 1)
									place.getWorld().spawnParticle(Particle.SMALL_FLAME,
											place.clone().add(new Random().nextFloat() % 1,
													1 + new Random().nextFloat() % 0.2, new Random().nextFloat() % 1),
											0, 0, 0.01, 0);
								if (tick == 0) {
									place.getWorld().playSound(place, Sound.ENTITY_BLAZE_SHOOT, 1,
											(float) (new Random().nextFloat() % 0.4 + 1));
									Item d = place.getWorld().dropItem(place.add(0.5, 1.5, 0.5), drop);
									place.add(-0.5, -1.5, -0.5);
									d.setVelocity(new Vector(0, 0.1, 0));
									tick = 200;
									cancel();
								}
							}
						};
						run.runTaskTimer(Main.getPlugin(), 0, 1L);
					}
				}
			};
			inventory.addAction(action, recipe.getResult(), recipe.getCosts());
			shop = inventory;
			shop.update();
		}
	}

	public Smelter(final Location location) {
		this.location = location;
		Smelter.smelters.add(this);
		inventory = shop.clone();
		inventory.setLocation(location);
	}

	public Location getLocation() {
		return location;
	}

	public void setRecipe(SmelterRecipe recipe) {
		currentRecipe = recipe;
	}

	public SmelterRecipe getRecipe() {
		return currentRecipe;
	}

	public InteractInventory getInventory() {
		return inventory;
	}

	public static Smelter querySmelter(Location location) {
		for (Smelter smelter : smelters) {
			if (smelter.getLocation().equals(location))
				return smelter;
		}
		return null;
	}

	public static void removeSmelter(Location location) {
		for (Smelter smelter : smelters) {
			if (smelter.getLocation().equals(location)) {
				smelters.remove(smelter);
				return;
			}
		}
	}

	public static InteractInventory getShop() {
		return shop;
	}
}
