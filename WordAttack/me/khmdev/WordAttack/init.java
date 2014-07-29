package me.khmdev.WordAttack;

import me.khmdev.WordAttack.API.CItems;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class init extends JavaPlugin{
	private WordItem item;
	@Override
	public void onEnable(){
		if (!hasPluging("HoloAPI")) {
			getLogger().severe(
					getName()
							+ " se desactivo debido ha que no encontro " +
							"HoloAPI");
			setEnabled(false);
			return;
		}
		CItems.initCItems(this);
		CargarWords.cargar(this);
		item=new WordItem(this);
		CItems.addItem(item);
	}
	private static boolean hasPluging(String s) {
		try {
			return Bukkit.getPluginManager().getPlugin(s).isEnabled();
		} catch (Exception e) {

		}
		return false;
	}

	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if(cmd.getName().equalsIgnoreCase("getWord")){
			Player pl=Bukkit.getServer().getPlayer(sender.getName());
			if(pl==null){return true;}
			pl.getInventory().addItem(item.getItem());
			pl.updateInventory();
			return true;
		}
		return false;
	}
}
