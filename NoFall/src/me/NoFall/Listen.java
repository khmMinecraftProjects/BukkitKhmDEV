package me.NoFall;

import org.bukkit.Location;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.player.PlayerMoveEvent;

public class Listen implements Listener {
	private Base base;
	public Listen(Base b){
		base=b;
	}
	@EventHandler
	public void getMoves(PlayerMoveEvent event) {
		if(event.getTo().getY()<=1){
			Location r=base.getSpawn();
			if (r==null) {
				r=event.getTo().getWorld().getSpawnLocation();
			}
			event.getPlayer().setFallDistance(0);
			event.getPlayer().teleport(r);
			
		}
		
	}

}
