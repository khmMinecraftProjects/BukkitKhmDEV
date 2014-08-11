package me.khmdev.NoImpersonation;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class init extends JavaPlugin {
	@Override
	public void onEnable(){
		Bukkit.getServer().getPluginManager().registerEvents(new ListenerNI(), this);
	}

}
