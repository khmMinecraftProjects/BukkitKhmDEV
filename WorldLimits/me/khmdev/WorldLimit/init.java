package me.khmdev.WorldLimit;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class init extends JavaPlugin{
	public void onEnable() {
		CargarConfig.cargar(this);
		Bukkit.getServer().getPluginManager()
		.registerEvents(new ListenerWorld(), this);
	}

		
}
