package me.khmdev.WordAttack.API;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public abstract class CustomItem {
	public CustomItem(){
		
	}
	public CustomItem(ItemStack it){
		item=it;
	}
	protected ItemStack item;
	protected String permisos=null;
	public ItemStack getItem() {
		return item;
	}

	public void setItem(ItemStack item) {
		this.item = item;
	}

	public abstract void execute(InventoryClickEvent event);
	
	public abstract void execute(PlayerInteractEvent event);
	public void execute(PlayerInteractEntityEvent event){};
	public boolean hasPerms(HumanEntity player){
		@SuppressWarnings("deprecation")
		Player p=(Bukkit.getServer().getPlayer(player.getName()));
		if(permisos!=null&&p.hasPermission(permisos)){
			
			p.getPlayer().sendMessage("No tienes permisos");				
			return false;
		}	
		return true;
	}
	
	public boolean isthis(ItemStack it){
		return item.isSimilar(it);
	}
		

}
