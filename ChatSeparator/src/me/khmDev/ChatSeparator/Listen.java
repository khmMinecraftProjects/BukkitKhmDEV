package me.khmDev.ChatSeparator;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Listen implements Listener{
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent chat){
		Player[] it=(Player[]) chat.getRecipients().toArray(new Player[chat.getRecipients().size()]);
		World w=chat.getPlayer().getWorld();
		chat.setMessage(Base.generarMensaje(w.getName(), chat.getMessage()));
		for (int i = 0; i < it.length; i++) {
			Player next=it[i];
			if(!next.getWorld().equals(w)&&
					!next.hasPermission("chatseparator.listenAll")){
				chat.getRecipients().remove(next);
			}
		}
	}
	
	
	
}
