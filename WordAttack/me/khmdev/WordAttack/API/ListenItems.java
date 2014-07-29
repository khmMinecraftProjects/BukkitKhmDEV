package me.khmdev.WordAttack.API;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ListenItems implements Listener {



	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {

		if (event.isCancelled()
				&&event.getAction()!=Action.RIGHT_CLICK_AIR){return;}
		if (event.getAction() != Action.RIGHT_CLICK_AIR
				&& event.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}
		ItemStack it = event.getPlayer().getItemInHand();
		if (it == null || it.getItemMeta() == null) {
			return;
		}
		CustomItem item = CItems.getItem(it);
		if (item == null) {
			return;
		}
		  
		if(item.hasPerms(event.getPlayer())){item.execute(event);}
	}


	@EventHandler
	public void onPlayerInteract(PlayerInteractEntityEvent event) {
		if (event.isCancelled()){return;}
		ItemStack it = event.getPlayer().getItemInHand();
		if (it == null || it.getItemMeta() == null) {
			return;
		}
		CustomItem item = CItems.getItem(it);
		if (item == null) {
			return;
		}
		  
		if(item.hasPerms(event.getPlayer())){item.execute(event);}
	}

}
