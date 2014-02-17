package me.NoteSoundBlock;

import java.util.Iterator;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.NotePlayEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.MetadataValue;

public class Listen implements Listener {
	@EventHandler
	public void playBlock(NotePlayEvent event) {
		Block b = event.getBlock();
		if (b != null && (b.getType() == Material.NOTE_BLOCK)) {
			List<MetadataValue> meta = b.getMetadata("CustomSound");
			if (meta == null) {
				return;
			}
			Iterator<MetadataValue> it = meta.iterator();
			Sound sound = null;
			while (it.hasNext() && sound == null) {
				sound = getSound(it.next().asString());
			}
			if (sound != null) {
				event.setCancelled(true);
				b.getWorld().playSound(b.getLocation(), sound, 1, 1);
			}
		}
	}

	private Sound getSound(String s) {
		try {
			return Sound.valueOf(s);
		} catch (Exception e) {
			return null;

		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
			Block b = event.getClickedBlock();
			if (b != null && (b.getType() == Material.NOTE_BLOCK)) {
				List<MetadataValue> meta = b.getMetadata("CustomSound");
				if (meta == null) {
					return;
				}
				Iterator<MetadataValue> it = meta.iterator();
				Sound sound = null;
				while (it.hasNext() && sound == null) {
					sound = getSound(it.next().asString());
				}
				if (sound != null) {
					if (!event.getPlayer().hasPermission("nsb.remove")) {
						event.setCancelled(true);
						event.getPlayer().sendMessage(
								Base.sendMessage("You haven't permissions"));
						return;
					}
					event.getPlayer().sendMessage(
							Base.sendMessage("Block Note remove"));
				}
			}
		}

	}

}
