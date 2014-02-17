package me.NoteSoundBlock;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import me.NSBAux.Almacen;
import me.NSBAux.Central;
import me.NSBAux.Datos;
import me.NSBAux.LocAlmacen;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;
import org.bukkit.plugin.java.JavaPlugin;

public class Base extends JavaPlugin implements Datos {

	private static String message = ChatColor.GREEN + "[NoteSoundBlock] "
			+ ChatColor.WHITE;
	private static JavaPlugin instance;
	private Central central;

	public void onEnable() {
		getServer().getPluginManager().registerEvents(new Listen(), this);
		getLogger().info("Pluging Cargado");
		lista = new HashMap<>();
		instance = this;
		central = new Central(this);
		central.insertar(this);
		central.cargar();
	}

	public void onDisable() {
		central.guardar();

	}

	public static String sendMessage(String s) {
		return message + s;
	}

	private String help() {
		String s = "";
		s += "/nsb <Command>\n";
		s += "Commands:  create (SoundEffect)\n";
		return s;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {

		if (cmd.getName().equalsIgnoreCase("nsb")) {
			if (args.length == 0) {
				sender.sendMessage(help());
				return true;
			}
			if (args[0].equalsIgnoreCase("create")) {
				Player pl = Bukkit.getServer().getPlayer(sender.getName());
				if (sender.getName() == "CONSOLE") {
					return true;
				}
				if (!pl.hasPermission("nsb.create")) {
					sender.sendMessage("You haven't " + "nsb.create"
							+ " permissions");

					return true;
				}
				if (args.length < 2) {
					sender.sendMessage(help());
					return true;
				}
				Sound effect = getSound(args[1]);
				@SuppressWarnings("deprecation")
				Block b = pl.getTargetBlock(null, 3);
				if (effect != null && b != null
						&& (b.getType() == Material.NOTE_BLOCK)) {
					b.setMetadata("CustomSound", new FixedMetadataValue(this,
							args[1]));
					lista.put(b.getLocation(),  args[1]);
					sender.sendMessage("Set Note Block");
					return true;
				}
				sender.sendMessage("Is not a Note Block or Sound effect");

				return true;
			}

			sender.sendMessage(help());
			return true;
		}

		return false;
	}

	private Sound getSound(String s) {
		try {
			return Sound.valueOf(s);
		} catch (Exception e) {
			return null;

		}
	}

	public static MetadataValue getMetadata(Metadatable b, String s) {
		if (b == null) {
			return null;
		}
		List<MetadataValue> list = b.getMetadata(s);
		if (list == null) {
			return null;
		}
		Iterator<MetadataValue> it = list.iterator();
		while (it.hasNext()) {
			MetadataValue next = it.next();
			if (next.getOwningPlugin().equals(instance)) {
				return next;
			}
		}
		return null;
	}

	public static void setMetadata(Metadatable b, String string, Object o) {
		b.setMetadata(string, new FixedMetadataValue(instance, o));
	}

	public static void removeMetadata(Metadatable b, String string) {
		b.removeMetadata(string, instance);
	}

	@Override
	public void cargar(Almacen nbt) {
		Iterator<String> it = nbt.getKeys().iterator();
		while (it.hasNext()) {
			String s = it.next();
			Location l = LocAlmacen.cargar(nbt, s);
			String sound = nbt.getString(s + "note");
			lista.put(l ,  sound);
			if (l != null) {
				Block b = l.getBlock();
				if (b != null) {
					b.setMetadata("CustomSound", new FixedMetadataValue(this,
							sound));
				}
			}
		}
	}

	private HashMap<Location, String> lista;

	@Override
	public void guardar(Almacen nbt) {
		Iterator<Entry<Location , String>> it = lista.entrySet().iterator();
		int i = 0;
		while (it.hasNext()) {
			Entry<Location , String> ent = it.next();
			LocAlmacen.guardar(nbt,ent.getKey()  ,  ent.getValue(), i + "");
			i++;
		}

	}

}
