package me.khmDev.ChatSeparator;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class ListenVisible implements Listener {


	private static final String PERMISOS="chatseparator.visibleAll";


	@EventHandler
	public void logIn(PlayerJoinEvent  event) {
		World w=event.getPlayer().getWorld();
		Boolean perms=event.getPlayer().hasPermission(PERMISOS);
		
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			if (!player.getWorld().equals(w)) {
				
				if(!player.hasPermission(PERMISOS)){
					player.hidePlayer(event.getPlayer());}
				
				if(!perms)
				{event.getPlayer().hidePlayer(player);}		

			}else{
				player.showPlayer(event.getPlayer());
				event.getPlayer().showPlayer(player);
			}
		}

	}
	
	
	@EventHandler
	public void onWorldChange(PlayerChangedWorldEvent event){
		World w=event.getPlayer().getWorld();
		Boolean perms=event.getPlayer().hasPermission(PERMISOS);
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			if (!player.getWorld().equals(w)) {
				
				if(!player.hasPermission(PERMISOS)){
					player.hidePlayer(event.getPlayer());}
				
				if(!perms)
				{event.getPlayer().hidePlayer(player);}		

			}else{
				player.showPlayer(event.getPlayer());
				event.getPlayer().showPlayer(player);
			}
		}
	}


}
