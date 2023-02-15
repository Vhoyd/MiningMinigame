package me.Vhoyd.miningMinigame.listeners;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.ShulkerBox;
import org.bukkit.boss.BossBar;
import org.bukkit.boss.KeyedBossBar;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.GrindstoneInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import me.Vhoyd.miningMinigame.Main;
import me.Vhoyd.miningMinigame.Bossbars.BarManager;
import me.Vhoyd.miningMinigame.Bossbars.VivBar;
import me.Vhoyd.miningMinigame.Commands.Cmds;
import me.Vhoyd.miningMinigame.DataHandling.BreakerQuery;
import me.Vhoyd.miningMinigame.DataHandling.DataComparisons;
import me.Vhoyd.miningMinigame.DataHandling.DrillData;
import me.Vhoyd.miningMinigame.DataHandling.MaterialQuery;
import me.Vhoyd.miningMinigame.DataHandling.MaterialYield;
import me.Vhoyd.miningMinigame.Dialogue.ShopNoResources;
import me.Vhoyd.miningMinigame.Dialogue.ShopTooFull;
import me.Vhoyd.miningMinigame.Inventories.DrillInventory;
import me.Vhoyd.miningMinigame.Inventories.PeekInventory;
import me.Vhoyd.miningMinigame.ItemManager.ItemManager;
import me.Vhoyd.miningMinigame.station.AbstractStation;
import me.Vhoyd.miningMinigame.station.Armory;
import me.Vhoyd.miningMinigame.station.Drill;
import me.Vhoyd.miningMinigame.station.Factory;
import me.Vhoyd.miningMinigame.station.Smelter;
import me.Vhoyd.pluginUtility.inventory.InteractInventory;
import me.Vhoyd.pluginUtility.inventory.InteractInventoryAction;
import me.Vhoyd.pluginUtility.inventory.ShopItem;
import me.Vhoyd.pluginUtility.item.QuickItem;

public class EventHandlers implements Listener {

	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent e) {
		final Player player = e.getPlayer();
		BukkitRunnable run = new BukkitRunnable() {
			//give player attributes
			public void run() {
				player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(20);
				player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
				try {
				} catch (Exception e1) {
					//ignore failed application
				}
			}
		};
		run.runTaskLater(Main.getPlugin(), 20L);//delay execution by 20 ticks (1 second) to ensure player login before attribute modifying
	}

	@EventHandler
	public void onPlayerPlaceBlock(BlockPlaceEvent e) {
		Block block = e.getBlock();
		//check if item shouldn't be placed
		if (ItemManager.customItem(e.getItemInHand()) && !ItemManager.canPlace(e.getItemInHand())) {
			e.setCancelled(true);
		} else {
			//check if item is drill
			if (block.getType().equals(Material.GRINDSTONE)) {
				if (e.getItemInHand().getItemMeta().hasLore()) {
					if (e.getItemInHand().getItemMeta().getLore().contains("§6§lDrill quality:")) {
						//check resource drill was placed on
						Block surface = e.getBlock().getRelative(BlockFace.DOWN);
						MaterialYield yield = DataComparisons.validSurface(surface);
						if (yield == null || yield == MaterialYield.GRASS) {
							e.getPlayer().sendMessage("§cThat isn't a valid resource!");
							e.setCancelled(true);
						} else {
							//get drill data based on item in hand
							DrillData query = DrillData.queryType(e.getItemInHand());
							if (yield.getTier() > query.getTier()) {
								e.setCancelled(true);
								e.getPlayer().sendMessage("§cYour drill is not a high enough tier to mine that!");
							} else {
								//all requirements are met, register the placed drill
								Drill.createNew(block.getLocation(), e.getItemInHand());							
							}
						}
					}
				}
			//check if item is smelter
			} else if (block.getType().equals(Material.BLAST_FURNACE)) {
				if (e.getItemInHand().getItemMeta().hasLore()) {
					if ((e.getItemInHand().isSimilar(ItemManager.SMELTER))) {
						//TODO: update smelter registration
						new Smelter(block.getLocation());									
					}
				}
			//check if item is armory
			} else if (block.getType().equals(Material.ANVIL)) {
				if (e.getItemInHand().getItemMeta().hasLore() && e.getItemInHand().isSimilar(ItemManager.ARMORY)) {
					Armory.createNew(block.getLocation());				
				}
			//check if item is factory
			} else if (block.getType().equals(Material.LODESTONE)) {
				if (e.getItemInHand().getItemMeta().hasLore()) {
					if (e.getItemInHand().isSimilar(ItemManager.FACTORY)) {
						//TODO: update factory registration
						new Factory(block);						
					}
				}
			//check if item is resource node
			} else if (block.getType().equals(Material.BEACON)) {
				if (e.getItemInHand().getItemMeta().hasLore()) {
					
					if (e.getItemInHand().getItemMeta().getDisplayName().equals("Resource Node Synthesizer")) {
						e.setCancelled(true);
						/* construct node interface
						 * title prompts resource pick
						 * no bound entity
						 * registers the inventory's location to the placed block's location
						 * inventory trigger sound is beacon activating
						 */
						InteractInventory tile = new InteractInventory("Pick a resource", null, e.getBlock().getLocation(),
								Sound.BLOCK_BEACON_ACTIVATE);
						for (final MaterialYield data : MaterialYield.values()) {
							InteractInventoryAction action;
							if (data == MaterialYield.GRASS) {
								action = new InteractInventoryAction() {
									@Override
									public void run(InventoryClickEvent e) {
										e.getWhoClicked().getEquipment().getItemInMainHand()
												.setAmount(e.getWhoClicked().getEquipment().getItemInMainHand().getAmount() - 1);
										getShop(e.getClickedInventory()).getLocation().add(0, -1, 0).getBlock()
												.setType(data.getType());
										e.getWhoClicked().closeInventory();
									}
								};
								tile.addAction(action, QuickItem.create(Material.GRASS_BLOCK, 1, "Revert tile to grass"), null,
										null);
								continue;
							}
							action = new InteractInventoryAction() {
								@Override
								public void run(InventoryClickEvent e) {
									InventoryState state = validInventory(e.getWhoClicked().getInventory(),
											getShop(e.getClickedInventory()).query(e.getCurrentItem()));
									handleStateSound(state, getShop(e.getClickedInventory()), ((Player) e.getWhoClicked()));
									if (state == InventoryState.VALID) {
										e.getWhoClicked().getEquipment().getItemInMainHand()
												.setAmount(e.getWhoClicked().getEquipment().getItemInMainHand().getAmount() - 1);
										removeItems(e.getWhoClicked().getInventory(),
												getShop(e.getClickedInventory()).query(e.getCurrentItem()));
										getShop(e.getClickedInventory()).getLocation().add(0, -1, 0).getBlock()
												.setType(data.getType());
										e.getWhoClicked().closeInventory();
									}
								}
							};
							tile.addAction(action, QuickItem.create(data.getType(), 1, data.toString().toLowerCase()),
									QuickItem.create(data.getYield(), 32));
						}
						tile.update();
						e.getPlayer().openInventory(tile.getInventory());
					}
				}
			}
		}
	}

	@EventHandler
	public void onPlayerBreakBlock(BlockBreakEvent e) {
		Block block = e.getBlock();
		if (block.getType().equals(Material.GRINDSTONE)) {
			AbstractStation a = Drill.getTracker().query(block.getLocation());
			if (a != null) {
				e.setCancelled(true);
				e.setDropItems(false);
				ItemStack drop = ((Drill)a).getData().getData().clone();
				block.getWorld().dropItem(block.getLocation(), drop);
				Drill.getTracker().remove(a);
				block.setType(Material.AIR);
			}
		} else if (block.getType().equals(Material.BLAST_FURNACE)) {
			Smelter smelter = Smelter.querySmelter(block.getLocation());
			if (smelter == null)
				return;
			e.setCancelled(true);
			e.setDropItems(false);
			Smelter.removeSmelter(block.getLocation());
			block.getWorld().dropItem(block.getLocation(), ItemManager.SMELTER);
			block.setType(Material.AIR);
		} else if (block.getType().equals(Material.ANVIL)) {
			Armory armory = (Armory)Armory.getTracker().query(block.getLocation());
			if (armory == null)
				return;
			e.setCancelled(true);
			e.setDropItems(false);
			Armory.getTracker().remove(armory);
			block.getWorld().dropItem(block.getLocation(), ItemManager.ARMORY);
			block.setType(Material.AIR);
		} else {
			if (e.getPlayer().getGameMode().equals(GameMode.CREATIVE))
				return;
			MaterialYield query = MaterialQuery.query(block.getType());
			if (query == null)
				return;
			if (query.getTier() <= BreakerQuery.query(e.getPlayer().getEquipment().getItemInMainHand()).getTier()) {
				e.setCancelled(true);
				ItemStack drop = query.getYield().clone();
				try {
					Item item = block.getWorld().dropItem(block.getLocation().add(0.5, 1.5, 0.5), drop);
					item.setVelocity(new Vector(0, 0.1, 0));
				} catch (Exception e1) {
					;
				}
				final Material set = block.getType();
				final Block at = block;
				block.setType(Material.BEDROCK);
				BukkitRunnable a = new BukkitRunnable() {
					public void run() {
						at.setType(set);
						for (Player player : Bukkit.getOnlinePlayers())
							player.playSound(at.getLocation(), Sound.BLOCK_NETHERITE_BLOCK_PLACE, 1,
									(float) ((new Random().nextFloat() % 0.4) + 1));
					}
				};
				a.runTaskLater(Main.getPlugin(), 40L);
			} else {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onInventoryOpen(InventoryOpenEvent e) {
		if (e.getInventory() instanceof GrindstoneInventory) {
			AbstractStation a = Drill.getTracker().query(e.getInventory().getLocation());
			if (a != null) {
				ItemStack display = ((Drill)a).getData().getData();
				for (HumanEntity p : e.getViewers()) {
					e.setCancelled(true);
					p.openInventory(new DrillInventory(display).getInventory());
				}
			}
		} else if (e.getInventory() instanceof FurnaceInventory
				&& e.getInventory().getLocation().getBlock().getType() == Material.BLAST_FURNACE) {
			Smelter query = Smelter.querySmelter(e.getInventory().getLocation());
			if (query == null)
				return;
			e.setCancelled(true);
			e.getPlayer().openInventory(query.getInventory().getInventory());
		} else if (e.getInventory() instanceof AnvilInventory) {
			Armory query = (Armory)Armory.getTracker().query(e.getInventory().getLocation());
			if (query == null)
				return;
			e.setCancelled(true);
			e.getPlayer().openInventory(query.getShop().getInventory());
		}
	}

	@EventHandler
	public void onInventoryClick(final InventoryClickEvent e) {
		if (e.getSlotType() == SlotType.OUTSIDE)
			return;
		if (e.getInventory().getHolder() instanceof ShulkerBox) {
			if (!(e.getAction().toString().contains("PICKUP")
					|| e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY)
					&& e.getClickedInventory().equals(e.getInventory())) {
				e.setCancelled(true);
			}
			return;
		}
		if (InteractInventory.BLANK_PANE.isSimilar(e.getCurrentItem())) {
			e.setCancelled(true);
			return;
		}
		if (e.getInventory().getHolder() instanceof PeekInventory
				&& e.getWhoClicked().getGameMode() != GameMode.CREATIVE) {
			e.setCancelled(true);
			return;
		}
		if (DrillData.queryType(e.getInventory().getItem(4)) != null) {
			e.setCancelled(true);
			return;
		} else {
			if (e.getInventory().getHolder() instanceof InteractInventory) {
				ShopItem clicked = ((InteractInventory) e.getInventory().getHolder()).query(e.getCurrentItem());
				if (clicked == null)
					return;
				e.setCancelled(true);
				if (clicked.getType() == ShopItem.Type.CATEGORY) {
					e.getWhoClicked().closeInventory();
					e.getWhoClicked().openInventory(clicked.getInventory().getInventory());
					((Player) e.getWhoClicked()).playSound(e.getWhoClicked().getLocation(), Sound.ENTITY_CHICKEN_EGG, 1,
							1.5F);
					return;
				}
				if (clicked.getType() == ShopItem.Type.ACTION) {
					clicked.getAction().run(e);
					return;
				}
				if (clicked.getType() == ShopItem.Type.INFO)
					return;
				if (clicked.getCosts() == null || clicked.getCosts().size() == 0) {
					if (e.getWhoClicked().getInventory().firstEmpty() == -1) {
						e.getWhoClicked().sendMessage(ChatColor.RED + ShopTooFull.randomLine());
						((Player) e.getWhoClicked()).playSound(e.getWhoClicked().getLocation(),
								Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
						return;
					}
					((Player) e.getWhoClicked()).playSound(e.getWhoClicked().getLocation(),
							clicked.getOwner().getSound(), 1, (float) 1);
					e.getWhoClicked().getInventory().addItem(clicked.getResult());
					return;
				}
				for (ItemStack check : clicked.getCosts()) {
					if (!e.getWhoClicked().getInventory().containsAtLeast(check, check.getAmount())) {
						e.getWhoClicked().sendMessage(ChatColor.RED + ShopNoResources.randomLine());
						((Player) e.getWhoClicked()).playSound(e.getWhoClicked().getLocation(),
								Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
						return;
					}
				}
				if (e.getWhoClicked().getInventory().firstEmpty() == -1) {
					e.getWhoClicked().sendMessage(ChatColor.RED + ShopTooFull.randomLine());
					((Player) e.getWhoClicked()).playSound(e.getWhoClicked().getLocation(),
							Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
					return;
				}
				for (ItemStack remove : clicked.getCosts()) {
					ItemStack store = remove.clone();
					while (store.getAmount() > 0) {
						for (ItemStack loop : e.getWhoClicked().getInventory().getContents()) {
							if (loop == null)
								continue;
							if (loop.isSimilar(store)) {
								if (loop.getAmount() > store.getAmount()) {
									loop.setAmount(loop.getAmount() - store.getAmount());
									store.setAmount(0);
									break;
								} else {
									int change = store.getAmount() - loop.getAmount();
									loop.setAmount(0);
									store.setAmount(change);
								}
							}
						}
					}
				}
				((Player) e.getWhoClicked()).playSound(e.getWhoClicked().getLocation(), clicked.getOwner().getSound(),
						1, (float) 1);
				e.getWhoClicked().getInventory().addItem(clicked.getResult());
				return;
			}
		}
	}

	@EventHandler
	public void onEntityHitEntity(final EntityDamageByEntityEvent e) {
		BukkitRunnable velocity = new BukkitRunnable() {
			public void run() {
				e.getEntity().setVelocity(new Vector(0, 0, 0));
			}
		};
		velocity.runTaskLater(Main.getPlugin(), 1L);
		if (e.getDamager() instanceof Player && e.getEntity() instanceof LivingEntity) {
			((Player) e.getDamager()).getInventory().addItem(QuickItem.create(ItemManager.BLOOD, 1));
		}
	}

	@EventHandler
	public void OnEntityDamage(EntityDamageEvent e) {
		final LivingEntity en;
		try {
			en = (LivingEntity) e.getEntity();
		} catch (Exception e1) {
			return;
		}
		BukkitRunnable reset = new BukkitRunnable() {
			public void run() {
				en.setNoDamageTicks(0);
			}
		};
		reset.runTaskLater(Main.getPlugin(), 1L);
		if (e.getEntity() instanceof Player) {
			if (((LivingEntity) e.getEntity()).getHealth() - e.getDamage() <= 0) {
				e.setCancelled(true);
				if (e.getEntity().getPersistentDataContainer().get(new NamespacedKey(Main.getPlugin(), "deathsound"),
						PersistentDataType.STRING) != null) {
					Bukkit.getWorld("world").playSound(e.getEntity().getLocation(),
							Sound.valueOf(e.getEntity().getPersistentDataContainer()
									.get(new NamespacedKey(Main.getPlugin(), "deathsound"), PersistentDataType.STRING)),
							1, (float) ((new Random().nextFloat() % 0.1) + 0.9));
				}
				((LivingEntity) e.getEntity()).setHealth(
						((LivingEntity) e.getEntity()).getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
				if (((Player) e.getEntity()).getBedSpawnLocation() == null) {
					e.getEntity().teleport(e.getEntity().getWorld().getSpawnLocation());
					return;
				}
				Player player = (Player) e.getEntity();
				player.getServer().broadcastMessage(((Player) e.getEntity()).getName() + " died.");
				ItemStack lootBox = QuickItem.create(Material.WHITE_SHULKER_BOX, 1, "Inventory Lootbox");
				BlockStateMeta meta = (BlockStateMeta) lootBox.getItemMeta();
				ShulkerBox state = (ShulkerBox) meta.getBlockState();
				for (int i = 9; i < 36; i++) {
					ItemStack item = player.getInventory().getItem(i);
					if (item == null)
						continue;
					ItemMeta imeta = item.getItemMeta();
					if (imeta == null) {
						state.getInventory().setItem(i - 9, item);
						player.getInventory().remove(item);
						continue;
					}
					List<String> lore = imeta.getLore();
					if (lore == null) {
						state.getInventory().setItem(i - 9, item);
						player.getInventory().remove(item);
						continue;
					}
					boolean add = true;
					for (String check : lore) {
						if (check.equals("§a§lSoulbound")) {
							add = false;
							break;
						}
					}
					if (add) {
						state.getInventory().setItem(i - 9, (player.getInventory().getItem(i)));
						player.getInventory().remove(item);
					}
				}
				meta.setBlockState(state);
				lootBox.setItemMeta(meta);

				ItemStack gearBox = QuickItem.create(Material.BLACK_SHULKER_BOX, 1, "Equipment Lootbox");
				meta = (BlockStateMeta) gearBox.getItemMeta();
				state = (ShulkerBox) meta.getBlockState();
				for (int i = 0; i < 9; i++) {
					ItemStack item = player.getInventory().getItem(i);
					if (item == null)
						continue;
					ItemMeta imeta = item.getItemMeta();
					if (imeta == null) {
						state.getInventory().setItem(i, item);
						player.getInventory().remove(item);
						continue;
					}
					List<String> lore = imeta.getLore();
					if (lore == null) {
						state.getInventory().setItem(i, item);
						player.getInventory().remove(item);
						continue;
					}
					boolean add = true;
					for (String check : lore) {
						if (check.equals("§a§lSoulbound")) {
							add = false;
							break;
						}
					}
					if (add) {
						state.getInventory().setItem(i, (player.getInventory().getItem(i)));
						player.getInventory().remove(item);
					}
				}
				ItemStack[] armor = { player.getEquipment().getHelmet(), player.getEquipment().getChestplate(),
						player.getEquipment().getLeggings(), player.getEquipment().getBoots(),
						player.getEquipment().getItemInOffHand() };
				for (int i = 0; i < 5; i++) {
					ItemStack item = armor[i];
					if (item == null)
						continue;
					ItemMeta imeta = item.getItemMeta();
					if (imeta == null) {
						state.getInventory().setItem(i + 9, item);
						player.getInventory().remove(item);
						continue;
					}
					List<String> lore = imeta.getLore();
					if (lore == null) {
						state.getInventory().setItem(i + 9, item);
						player.getInventory().remove(item);
						continue;
					}
					boolean add = true;
					for (String check : lore) {
						if (check.equals("§a§lSoulbound")) {
							add = false;
							break;
						}
					}
					if (add) {
						state.getInventory().setItem(i + 9, (player.getInventory().getItem(i)));
						player.getInventory().remove(item);
					}
				}
				meta.setBlockState(state);
				gearBox.setItemMeta(meta);
				player.getWorld().dropItem(player.getLocation(), gearBox);
				player.getWorld().dropItem(player.getLocation(), lootBox);
				player.teleport(player.getBedSpawnLocation());

			}
		} else if (e.getEntity() instanceof Villager) {
			VivBar bar = BarManager.queryVivBar((Villager) e.getEntity());
			if (bar != null) {
				try {
					if (((LivingEntity) e.getEntity()).getHealth() - e.getDamage() <= 0) {
						bar.delete();
						bar.getViv().getWorld().playSound(bar.getViv().getLocation(), Sound.ENTITY_WITHER_DEATH, 1,
								(float) ((new Random().nextFloat() % 0.1) + 1.2));
						return;
					}
					final VivBar b = bar;
					BukkitRunnable a = new BukkitRunnable() {
						public void run() {
							b.update();
							b.getViv().getWorld().playSound(b.getViv().getLocation(),
									Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 1,
									(float) ((new Random().nextFloat() % 0.1) + 1.2));
						}
					};
					a.runTaskLater(Main.getPlugin(), 1L);
				} catch (Exception e1) {
					return;
				}
				((LivingEntity) e.getEntity()).setHealth(((LivingEntity) e.getEntity()).getHealth());
			} else {
				if (e.getEntity().getCustomName() == null)
					return;
				BossBar b = Bukkit
						.getBossBar(new NamespacedKey(Main.getPlugin(), e.getEntity().getUniqueId().toString()));
				if (b == null && e.getEntity().getCustomName().contains("V.I.V")) {
					e.getEntity().remove();
					return;
				}
				bar = new VivBar((Villager) e.getEntity(), b);
				BarManager.addVivBar(bar);
				((LivingEntity) e.getEntity()).damage(e.getDamage());
				bar.update();
				if (((LivingEntity) e.getEntity()).getHealth() == 0) {
					bar.delete();
					Iterator<KeyedBossBar> i = Bukkit.getBossBars();
					while (i.hasNext()) {
						KeyedBossBar k = i.next();
						k.removeAll();
					}
					return;
				}
			}
		}
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
	}

	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent e) {
		if (e.getItemDrop().getItemStack().getItemMeta().getLore() == null)
			return;
		if (e.getItemDrop().getItemStack().getItemMeta().getLore().contains("§c§oWill dissappear if dropped.")) {
			final Item item = e.getItemDrop();
			BukkitRunnable clear = new BukkitRunnable() {

				public void run() {
					item.remove();
				}
			};
			clear.runTaskLater(Main.getPlugin(), 1L);
		} else if (e.getItemDrop().getItemStack().equals(ItemManager.MOBILE_MENU)) {
			e.setCancelled(true);
			Armory.init();
			InteractInventory display = new InteractInventory(ItemManager.MOBILE_MENU.getItemMeta().getDisplayName(),
					null, null, Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
			display.addCategory(QuickItem.create(Material.GRINDSTONE, 1, "V.I.V shop"), Cmds.getMenu().clone(), false);
			display.addCategory(QuickItem.create(Material.ANVIL, 1, "Armory shop"), Armory.getStaticShop().clone(), false);
			InteractInventory smelter = Smelter.getShop().clone();
			smelter.setPlayer(e.getPlayer());
			display.addCategory(QuickItem.create(Material.BLAST_FURNACE, 1, "Smelting shop"), smelter, false);
			InteractInventory factory = Factory.getShop().clone();
			factory.setPlayer(e.getPlayer());
			display.addCategory(QuickItem.create(Material.LODESTONE, 1, "Factory"), factory, false);
			display.update();
			display.setPlayer(e.getPlayer());
			e.getPlayer().openInventory(display.getInventory());
		}
	}

	@EventHandler
	public void onPlayerInteractEntity(final PlayerInteractEntityEvent e) {
		if (e.getPlayer().getEquipment().getItemInMainHand().getItemMeta() != null && e.getPlayer().getEquipment()
				.getItemInMainHand().getItemMeta().getDisplayName().equals("Blood Apple")) {
			if (e.getRightClicked() instanceof Player || e.getRightClicked() instanceof Villager 
				&& ((LivingEntity)e.getRightClicked()).getHealth() < ((LivingEntity) e.getRightClicked()).getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()) {
				
				
				e.getPlayer().getEquipment().getItemInMainHand()
						.setAmount(e.getPlayer().getEquipment().getItemInMainHand().getAmount() - 1);
				if (((LivingEntity) e.getRightClicked()).getHealth() + 5 > ((LivingEntity) e.getRightClicked())
						.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()) {
					((LivingEntity) e.getRightClicked()).setHealth(
							((LivingEntity) e.getRightClicked()).getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
				} else {
					((LivingEntity) e.getRightClicked()).setHealth(((LivingEntity) e.getRightClicked()).getHealth() + 5);
				}
				e.setCancelled(true);
				((LivingEntity) e.getRightClicked()).damage(0);
				BukkitRunnable sound = new BukkitRunnable() {
					public void run() {
						for (Player p : Bukkit.getOnlinePlayers())
							p.stopSound(Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR);
						e.getRightClicked().getWorld().playSound(e.getRightClicked().getLocation(),
								Sound.ENTITY_GENERIC_EAT, 1, 1);
					}
				};
				sound.runTaskLater(Main.getPlugin(), 1L);
				return;
			} else {
				System.out.println("Blood apple");
				if (e.getPlayer().getHealth() < 20) {
					e.getPlayer().getEquipment().getItemInMainHand()
					.setAmount(e.getPlayer().getEquipment().getItemInMainHand().getAmount() - 1);
					if (e.getPlayer().getHealth() + 5 > 20) {
						e.getPlayer().setHealth(20);
					} else {
						e.getPlayer().setHealth(e.getPlayer().getHealth() + 5);
					}
				}
			}
		}
		if (e.getRightClicked() instanceof Villager) {
			if (BarManager.queryVivBar((Villager) e.getRightClicked()) != null) {
				e.setCancelled(true);
				e.getPlayer().openInventory(Cmds.getMenu().getInventory());
			}
		} else if (e.getRightClicked() instanceof ItemFrame & e.getPlayer().isSneaking()) {
			e.setCancelled(true);
			e.getPlayer().getWorld().playSound(e.getRightClicked().getLocation(), Sound.ENTITY_ITEM_FRAME_ROTATE_ITEM,
					1, 1);
			((ItemFrame) e.getRightClicked()).setVisible(!((ItemFrame) e.getRightClicked()).isVisible());
		}
	}

	@EventHandler
	public void onPlayerScroll(PlayerItemHeldEvent e) {
		PotionEffect haste = new PotionEffect(PotionEffectType.FAST_DIGGING, 1000000, 5, false, false, false);
		if (ItemManager.CHISEL.equals(e.getPlayer().getInventory().getItem(e.getNewSlot()))) {
			e.getPlayer().addPotionEffect(haste);
		} else {
			e.getPlayer().removePotionEffect(PotionEffectType.FAST_DIGGING);
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getClickedBlock().getType() == Material.LODESTONE) {
				Factory query = Factory.query(e.getClickedBlock());
				if (query != null) {
					e.setCancelled(true);
					e.getPlayer().openInventory(query.getInventory().getInventory());
				}
			}
		}
		if (e.getItem() == null)
			return;
		if (e.getItem().getItemMeta() instanceof CrossbowMeta) {
			final ItemStack wait = e.getItem().clone();
			final Player player = e.getPlayer();
			BukkitRunnable run = new BukkitRunnable() {
				public void run() {
					player.getInventory().getItemInMainHand().setItemMeta(wait.getItemMeta());
				}
			};
			run.runTaskLater(Main.getPlugin(), 1L);
		}
	}

	@EventHandler
	public void onPlayerProjectile(final ProjectileLaunchEvent e) {
		if (e.getEntity().getShooter() instanceof Player && e.getEntityType() == EntityType.ENDER_PEARL) {
			String label = ((Player) e.getEntity().getShooter()).getEquipment().getItemInMainHand().getItemMeta()
					.getDisplayName();
			if (label.equals("Quiet pearl")) {
				e.getEntity().setCustomName("quiet");

				BukkitRunnable shush = new BukkitRunnable() {
					public void run() {
						for (Player player : Bukkit.getOnlinePlayers()) {
							player.stopSound(Sound.ENTITY_EGG_THROW);
						}
					}
				};
				shush.runTaskLater(Main.getPlugin(), 1L);
			}
		}
	}

	@EventHandler
	public void onProjectileLand(ProjectileHitEvent e) {
		if (e.getEntityType() == EntityType.ENDER_PEARL) {
			if (e.getEntity().getCustomName().equals("quiet")) {
				e.setCancelled(true);
				Vector facing = ((Player) e.getEntity().getShooter()).getLocation().getDirection();
				Location where = e.getEntity().getLocation();
				where.setDirection(facing);
				((Player) e.getEntity().getShooter()).teleport(where);
				e.getEntity().remove();
			}
		} else if (e.getEntityType() == EntityType.SHULKER_BULLET) {
			if (e.getEntity().getCustomName().equals("orbit")) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onCloseInventory(InventoryCloseEvent e) {
		if (e.getInventory().getHolder() instanceof ShulkerBox) {
			if (e.getInventory().isEmpty())
				e.getInventory().getLocation().getBlock().setType(Material.AIR);
		}
	}
}
