package me.khmdev.WordAttack;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.dsh105.holoapi.HoloAPI;
import com.dsh105.holoapi.api.Hologram;

import me.khmdev.WordAttack.API.CustomItem;

public class WordItem extends CustomItem{
	private static List<String> list=new ArrayList<>();
	private JavaPlugin plugin;
	public WordItem(JavaPlugin plug){
		super(Utils.getItem(Material.NAME_TAG, "Palabra", 0));
		plugin=plug;
	}
	public static void addWord(String s){
		list.add(s);
	}
	@Override
	public void execute(InventoryClickEvent event) {
		
	}

	@Override
	public void execute(PlayerInteractEvent e) {
		
		Player pl=e.getPlayer();

		Hologram holo=HoloAPI.getManager().createSimpleHologram(pl.getEyeLocation(),
				9999, true, list.get((int)(list.size()*Math.random())));
		RunHolo run=new RunHolo(pl.getLocation().getDirection(),holo,
				3,1,pl) ;
		int idd=Bukkit.getScheduler().scheduleSyncRepeatingTask(
				plugin,
				run, 0L, 1L);
		run.setId(idd);
		if(pl.getGameMode()!=GameMode.CREATIVE){
			if(pl.getItemInHand().getAmount()!=1){
				pl.getItemInHand().setAmount(pl.getItemInHand().getAmount()-1);
			}else{
				pl.setItemInHand(null);
			}
		}
	
	}
	public static List<String> getList() {
		return list;
	}

}
