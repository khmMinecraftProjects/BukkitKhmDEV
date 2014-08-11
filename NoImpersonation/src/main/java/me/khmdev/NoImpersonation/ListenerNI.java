package me.khmdev.NoImpersonation;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class ListenerNI implements Listener {
	public ListenerNI(){
		for (Player pl : Bukkit.getOnlinePlayers()) {
			testPlayer(pl);
		}
	}
	private void testPlayer(Player pl){
		UUID id = UUIDConverter.getUUIDOf(pl.getName());
		
		System.out.println(id+" - "+pl.getUniqueId());
		if (id != null && id != pl.getUniqueId()) {
			pl.kickPlayer(
					"Te estas haciendo pasar por un usuario premium");
		}
	}
	@EventHandler
	public void Login(PlayerLoginEvent e) {
		testPlayer(e.getPlayer());
	}

}
