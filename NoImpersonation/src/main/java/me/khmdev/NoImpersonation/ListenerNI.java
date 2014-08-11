package me.khmdev.NoImpersonation;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class ListenerNI implements Listener {
	public ListenerNI() {
		for (Player pl : Bukkit.getOnlinePlayers()) {
			if (!testPlayer(pl)) {
				pl.kickPlayer("Te estas haciendo pasar por un usuario premium");
			}
		}
	}

	private boolean testPlayer(Player pl) {
		UUID id = UUIDConverter.getUUIDOf(pl.getName());

		System.out.println(id + " - " + pl.getUniqueId());
		if (id != null && id != pl.getUniqueId()) {
			return false;
		}
		return true;
	}

	@EventHandler
	public void Login(PlayerLoginEvent e) {
		if (!testPlayer(e.getPlayer())) {
			e.disallow(null, "Te estas haciendo pasar por un usuario premium");
		}
	}

}
