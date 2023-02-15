package me.Vhoyd.miningMinigame.station;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import me.Vhoyd.miningMinigame.Main;
import me.Vhoyd.miningMinigame.ItemManager.ItemManager;
import me.Vhoyd.pluginUtility.inventory.InteractInventory;
import me.Vhoyd.pluginUtility.inventory.InteractInventoryAction;
import me.Vhoyd.pluginUtility.item.QuickItem;

public class Factory {

	private static InteractInventory shop;
	private static List<Factory> factories = new ArrayList<Factory>();

	private Block block;
	private InteractInventory inventory;

	public Factory(Block block) {
		this.block = block;
		inventory = shop.clone();
		inventory.setLocation(block.getLocation());
		factories.add(this);
	}

	public Block getBlock() {
		return block;
	}

	public InteractInventory getInventory() {
		return inventory;
	}

	public static Factory query(Block block) {
		for (Factory factory : factories) {
			if (factory.getBlock().equals(block))
				return factory;
		}
		return null;
	}

	public static void removeFactory(Factory factory) {
		factories.remove(factory);
	}

	public static InteractInventory getShop() {
		return shop;
	}

	static {
		InteractInventory inv = new InteractInventory("Factory shop", null, null, Sound.BLOCK_BREWING_STAND_BREW);
		InteractInventoryAction createZombie = new InteractInventoryAction() {

			public void run(InventoryClickEvent e) {
				me.Vhoyd.pluginUtility.inventory.ShopItem clicked = getShop(e.getClickedInventory())
						.query(e.getCurrentItem());
				InventoryState state = this.validInventory(e.getWhoClicked().getInventory(), clicked);
				this.handleStateSound(state, getShop(e.getClickedInventory()), (Player) e.getWhoClicked());
				if (state == InteractInventoryAction.InventoryState.VALID) {
					this.removeItems(e.getWhoClicked().getInventory(), clicked);
					final Location where = getShop(e.getClickedInventory()).getLocation();
					final Player player = getShop(e.getClickedInventory()).getPlayer();
					BukkitRunnable spawn = new BukkitRunnable() {
						private int tick = 0;

						public void run() {
							tick++;
							final Location spot = (player != null ? player.getLocation().clone().add(-0.5, -1.5, -0.5)
									: where);
							if (tick > 149) {
								Zombie spawn = spot.getWorld().spawn(spot.clone().add(0.5, 1.5, 0.5), Zombie.class);
								spawn.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100000, 1,
										false, false, false));
								spot.getWorld().playSound(spot, Sound.BLOCK_PISTON_CONTRACT, 1, 0.8F);
								this.cancel();
							}
							if ((tick + 1) % 25 == 0) {
								spot.getWorld().playSound(spot, Sound.ENTITY_VILLAGER_WORK_FLETCHER, 1,
										new Random().nextFloat() % 0.4F + 1.3F);
							}
						}
					};

					spawn.runTaskTimer(Main.getPlugin(), 0, 1L);
				}
			}
		};
		inv.addAction(createZombie, QuickItem.create(Material.ROTTEN_FLESH, 1, "Spawn Zombie"),
				QuickItem.create(ItemManager.COAL, 5), QuickItem.create(ItemManager.IRON, 5),
				QuickItem.create(ItemManager.BLOOD, 10));
		inv.update();
		shop = inv;

	}

}
