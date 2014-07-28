package me.khmdev.WorldLimit;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class ListenerWorld implements Listener{
	private static List<ChangeWorld> limits=new LinkedList<>();
	
	@EventHandler(priority=EventPriority.HIGH)
	public void move(PlayerMoveEvent e){
		for (ChangeWorld l : limits) {
			l.changeWorld(e.getPlayer());
		}
	}

	public static void addLimit(ChangeWorld n) {
		limits.add(n);
	}
}
