package me.NoFall;

import me.NoFallAux.Almacen;
import me.NoFallAux.Central;
import me.NoFallAux.Datos;
import me.NoFallAux.LocAlmacen;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Base extends JavaPlugin implements Datos {

	private static String message = ChatColor.GREEN + "[NoFall] "
			+ ChatColor.WHITE;
	private Location spawn=null;
	private Central central;
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new Listen(this), this);
		getLogger().info("Pluging Cargado");
		central=new Central(this);
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

		if (cmd.getName().equalsIgnoreCase("nf")) {
			if (args.length == 0) {
				sender.sendMessage(help());
				return true;
			}
			if (args[0].equalsIgnoreCase("setspawn")) {
				if (sender.getName() == "CONSOLE") {
					return true;
				}
				Player pl = Bukkit.getServer().getPlayer(sender.getName());

				if (!pl.hasPermission("nf.command")) {
					sender.sendMessage(ChatColor.RED+"Mo tienes permisos"
							+ " nf.command");

					return true;
				}
				spawn=pl.getLocation();
				sender.sendMessage( sendMessage("Spawn creado"));

				return true;
			}

			sender.sendMessage(help());
			return true;
		}

		return false;
	}

	public Location getSpawn() {
		return spawn;
	}

	@Override
	public void cargar(Almacen nbt) {
		spawn=LocAlmacen.cargar(nbt, "spawn");
	}

	@Override
	public void guardar(Almacen nbt) {
		LocAlmacen.guardar(nbt, spawn, "spawn");
	}



}
