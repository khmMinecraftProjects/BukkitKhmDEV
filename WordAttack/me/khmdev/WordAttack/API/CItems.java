package me.khmdev.WordAttack.API;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class CItems {
	private static ReloadItem time;
	public static void initCItems(JavaPlugin apii){
		apii.getServer().getPluginManager()
		.registerEvents(new ListenItems(), apii);
		time=new ReloadItem();
		int idd = Bukkit.getScheduler().scheduleSyncRepeatingTask(
				apii,
				time, 0L, 20L);
		time.setId(idd);
	}
	
	public static void addTimer(ItemStack item,long tim){
		if(time==null){return;}
		time.addTime(new TimerItem(item, tim));
	}
	public static void addTimer(ItemStack item,long tim,Player pl){
		if(time==null){return;}
		time.addTime(new TimerItem(item, tim,pl));
	}

			
	private static List<CustomItem> items=
			new LinkedList<>();	

	
	public static List<CustomItem> getItems() {
		return items;
	}

	public static void addItem(CustomItem item) {
		items.add(item);
	}
	
	public static void removeItem(String name){
		items.remove(name);
	}

	public static CustomItem getItem(ItemStack item) {
		Iterator<CustomItem> it=items.iterator();
		while(it.hasNext()){
			CustomItem next=it.next();
			if(next.isthis(item)){
				return next;
			}
		}
		return null;
	}
	

}
