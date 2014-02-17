package me.PressureToBuy;

import java.math.BigDecimal;

import net.ess3.api.Economy;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;

public class Listen implements Listener {

	@EventHandler
	public void inventoryClick(InventoryClickEvent event)
			throws NoLoanPermittedException, ArithmeticException,
			UserDoesNotExistException {

		if (event.getInventory().getName().equals(Base.inventory.getName())) {
			ItemStack item = event.getCurrentItem();

			if (event.getRawSlot() < Base.inventory.getSize()) {
				event.setCancelled(true);
			}

			if (item == null
					|| item.getItemMeta() == null
					|| item.getItemMeta().getLore() == null
					|| item.getItemMeta().getLore().isEmpty()
					|| item.getItemMeta().getLore().get(0).substring(0, 7)
							.equals("Price:")) {
				return;
			}

			@SuppressWarnings("deprecation")
			double money = Economy.getMoney(event.getWhoClicked().getName());

			double price = getPrice(item.getItemMeta().getLore().get(0));
			if (price <= money) {
				Economy.substract(event.getWhoClicked().getName(),
						new BigDecimal(price));
				ItemStack item2 = item.clone();
				ItemMeta meta = item2.getItemMeta();
				meta.setLore(null);
				item2.setItemMeta(meta);
				event.getWhoClicked().getInventory().addItem(item2);
				Bukkit.getServer()
						.getPlayer(event.getWhoClicked().getName())
						.sendMessage(
								"You bought " + item2.getType() + " for "
										+ price + "$");
			} else {
				Bukkit.getServer().getPlayer(event.getWhoClicked().getName())
						.sendMessage("Insufficient Funds");
			}
		}
	}

	private double getPrice(String s) {
		return Double.valueOf(s.substring(7, s.length()));
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void ReSpawn(PlayerRespawnEvent event) {

	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (!Base.isEnable()) {
			return;
		}
		if (event.getAction() == Action.PHYSICAL) {
			if (Base.containLoc(event.getClickedBlock().getLocation())) {
				if (event.getPlayer().hasPermission("pps.pressureplate")) {

					Player pl = event.getPlayer();
					pl.openInventory(Base.inventory);
				} else {
					event.getPlayer().sendMessage(
							"You haven't " + "pps.pressureplate"
									+ " permissions");

				}
			}				
		}
	}


	@EventHandler
	public void onPlayerInteract(BlockBreakEvent event) {
		Block b = event.getBlock();
		if (b.getType() == Material.STONE_PLATE
				|| b.getType() == Material.WOOD_PLATE) {
			if (Base.containLoc(b.getLocation())) {
				if (event.getPlayer() != null) {
					Player pl = event.getPlayer();
					if (pl.hasPermission("pps.remove")) {
						Base.removeLoc(b.getLocation());
						pl.sendMessage("Pressure plate remove");
					} else {
						event.setCancelled(true);
						pl.sendMessage("You haven't " + "pps.remove"
								+ " permissions");
					}
				}
			}
		}
	}
}
