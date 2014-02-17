package me.PressureToBuy;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import me.API.Almacenes.Almacen;
import me.API.Almacenes.Central;
import me.API.Almacenes.Datos;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Base extends JavaPlugin implements Datos {
	private static final String SEPARATOR = ",";
	private static boolean isEnable = true;
	public static List<String> list;
	public static Inventory inventory;
	private static List<Location> pressures;
	private static int pos = 0;
	Central central;

	public void initConfig() {
		list = Arrays.asList();
		this.getConfig().addDefault(this.getName() + ".List", list);
		list = this.getConfig().getStringList(this.getName() + ".List");

	}

	public void onEnable() {

		if (!hasEconomy()) {
			getLogger().severe(
					getName() + " is disabled because no Economy plugin found");
			this.setEnabled(false);
			return;
		}

		initConfig();
		initInventory();
		getServer().getPluginManager().registerEvents(new Listen(), this);
		central = new Central(this);
		central.insertar(this);
		central.cargar();

	}

	private boolean hasEconomy() {
		try {
			Class.forName("com.earth2me.essentials.api.Economy");
			return true;
		} catch (Exception e) {

		}
		return false;
	}

	public void initInventory() {
		if (list.size() == 0) {
			inventory = Bukkit.getServer().createInventory(null, 9, "Shop");
			return;
		}
		pos = 0;
		int tamano = (9 * ((list.size() - list.size() % 9) / 9));
		if (list.size() % 9 != 0) {
			tamano += 9;
		}
		inventory = Bukkit.getServer().createInventory(null, tamano, "Shop");
		Iterator<String> it = list.iterator();
		while (it.hasNext()) {
			addItem(it.next());
		}
	}

	private void addItem(String next) {

		int id = Integer.valueOf(getSeparate(next, 0));
		double price = Double.valueOf(getSeparate(next, 1));
		int amount = Integer.valueOf(getSeparate(next, 2));
		ItemStack item = new ItemStack(id);
		ItemMeta meta = item.getItemMeta();
		meta.setLore(Arrays.asList("Price: " + price));
		item.setItemMeta(meta);
		item.setAmount(amount);
		ItemStack[] cont = inventory.getContents();
		cont[pos] = item;
		inventory.setContents(cont);
		pos++;
		// inventory.addItem(item);
	}

	private String getSeparate(String txt, int i) {
		String s = "";
		int o = 0, word = 0;
		while (o < txt.length() && word <= i) {
			s = "";

			while (o < txt.length()
					&& !txt.substring(o, o + 1).equals(SEPARATOR)) {
				s += txt.substring(o, o + 1);
				o++;

			}

			o++;
			word++;
		}
		return s;
	}

	public void onDisable() {
		if (central != null) {
			central.guardar();
		}

	}

	public static boolean isEnable() {
		return isEnable;
	}

	private String help() {
		String s = "";
		s += "/pps <Command>\n";
		s += "Commands:  create : create a pps for the presser plate that you look\n";
		s += "           remove : remove a pps for the presser plate that you look\n";
		s += "           on : turn on the pps for all presser plates\n";
		s += "           off : turn off the pps for all presser plates\n";

		return s;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {

		if (cmd.getName().equalsIgnoreCase("pps")) {
			if (args.length == 0) {
				sender.sendMessage(help());
				return true;
			}
			if (args[0].equalsIgnoreCase("create")) {
				Player pl = Bukkit.getServer().getPlayer(sender.getName());
				if (sender.getName() == "CONSOLE") {
					return true;
				}
				if (!pl.hasPermission("pps.create")) {
					sender.sendMessage("You haven't " + "pps.create"
							+ " permissions");

					return true;
				}
				@SuppressWarnings("deprecation")
				Block b = pl.getTargetBlock(null, 3);
				if (b != null
						&& (b.getType() == Material.STONE_PLATE || b.getType() == Material.WOOD_PLATE)) {
					pressures.add(b.getLocation());
					sender.sendMessage("Set pressure plate");
					return true;
				}
				sender.sendMessage("Is not a wood/stone pressure plate");

				return true;
			}

			if (args[0].equalsIgnoreCase("remove")) {
				if (sender.getName() == "CONSOLE") {
					return true;
				}
				Player pl = Bukkit.getServer().getPlayer(sender.getName());
				if (!pl.hasPermission("pps.remove")) {
					sender.sendMessage("You haven't " + "pps.remove"
							+ " permissions");

					return true;
				}
				Block b = pl.getTargetBlock(null, 3);
				if (b != null && removeLoc(b.getLocation())) {
					sender.sendMessage("Pressure plate remove");
					return true;
				}
				sender.sendMessage("Is not a pressure plate");
				return true;
			}
			if (args[0].equalsIgnoreCase("on")) {
				if (sender.getName() != "CONSOLE") {
					Player pl = Bukkit.getServer().getPlayer(sender.getName());
					if (!pl.hasPermission("pps.enabler")) {
						sender.sendMessage("You haven't " + "pps.enabler"
								+ " permissions");

						return true;
					}
				}

				isEnable = true;
				sender.sendMessage("PPS on");
				return true;
			}
			if (args[0].equalsIgnoreCase("off")) {
				if (sender.getName() != "CONSOLE") {
					Player pl = Bukkit.getServer().getPlayer(sender.getName());
					if (!pl.hasPermission("pps.enabler")) {
						sender.sendMessage("You haven't " + "pps.enabler"
								+ " permissions");

						return true;
					}
				}
				isEnable = false;
				sender.sendMessage("PPS off");
				return true;
			}
		}
		sender.sendMessage(help());

		return false;
	}

	@Override
	public void cargar(Almacen nbt) {
		pressures = new LinkedList<Location>();
		if (!nbt.hasKey("Pressures_size")) {
			return;
		}
		int max = nbt.getInteger("Pressures_size"), i = 0;
		isEnable = nbt.getBoolean("Pressures_enable");

		while (i < max) {
			Almacen locA = nbt.geAlmacen("L_" + i);
			int[] l = locA.getIntArray("l");
			String w = locA.getString("w");
			pressures.add(new Location(getWorld(w), l[0], l[1], l[2]));
			i++;
		}

	}

	public World getWorld(String world) {
		return Bukkit.getServer().getWorld(world);

	}

	@Override
	public void guardar(Almacen dat) {
		Iterator<Location> ws = pressures.iterator();
		int i = 0;
		dat.setInteger("Pressures_size", pressures.size());
		dat.setBoolean("Pressures_enable", isEnable);
		while (ws.hasNext()) {
			Almacen locA = dat.geAlmacen("L_" + i);
			Location loc = ws.next();
			int[] l = { (int) loc.getX(), (int) loc.getY(), (int) loc.getZ() };
			locA.setString("w", loc.getWorld().getName());
			locA.setIntArray("l", l);
			dat.setAlmacen("L_" + i, locA);

			i++;
		}

	}
	

	public static boolean containLoc(Location l) {

		return pressures.contains(l);
	}

	public static boolean removeLoc(Location l) {
		Iterator<Location> it = pressures.iterator();
		while (it.hasNext()) {
			Location c = it.next();
			if (c.equals(l)) {
				pressures.remove(c);
				return true;
			}
		}
		return false;
	}

}
