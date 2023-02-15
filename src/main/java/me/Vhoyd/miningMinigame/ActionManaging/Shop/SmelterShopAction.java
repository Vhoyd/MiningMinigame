package me.Vhoyd.miningMinigame.ActionManaging.Shop;

import org.bukkit.event.inventory.InventoryClickEvent;

import me.Vhoyd.miningMinigame.DataHandling.SmelterRecipe;
import me.Vhoyd.pluginUtility.inventory.InteractInventoryAction;

public abstract class SmelterShopAction extends InteractInventoryAction {

	private SmelterRecipe recipe;

	public abstract void run(InventoryClickEvent e);

	public SmelterShopAction(SmelterRecipe recipe) {
		this.setRecipe(recipe);
	}

	public SmelterRecipe getRecipe() {
		return recipe;
	}

	public void setRecipe(SmelterRecipe recipe) {
		this.recipe = recipe;
	}
}
