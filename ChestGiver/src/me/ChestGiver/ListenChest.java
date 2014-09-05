package me.ChestGiver;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ListenChest implements Listener {

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Block b = event.getClickedBlock();
			if (b.getType() == Material.CHEST
					|| b.getType() == Material.TRAPPED_CHEST) {
				Cofre c = Base.getCofre(b.getLocation());
				if (c == null) {
					return;
				}
				event.setCancelled(true);
				Player pl = event.getPlayer();
				if (c.containPlayer(pl.getName())) {
					return;
				}
				c.addPlayer(pl.getName());
				Chest chest = (Chest) b.getState();
				ItemStack[] list = chest.getBlockInventory().getContents();

				for (int i = 0; i < list.length; i++) {
					if (list[i] != null) {
						pl.getInventory().addItem(list[i]);
					}
				}
				pl.updateInventory();
				return;
			}
		}

	}

	@EventHandler
	public void onPlayerInteract(BlockBreakEvent event) {
		Block b = event.getBlock();
		if (b.getType() == Material.CHEST
				|| b.getType() == Material.TRAPPED_CHEST) {
			Base.removeCofre(b.getLocation());
		}
	}
}
