package me.NoFallAux;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocAlmacen {

	public static Location cargar(Almacen nbt, String s) {
		Location l = null;
		int[] ints = nbt.getIntArray(s);
		String ww = nbt.getString(s + "W");
		if (ww.length() != 0 && ints.length > 2) {
			World w = getWorld(nbt.getString(s + "W"));
			if (w == null) {
				return l;
			}
			l = new Location(w, ints[0], ints[1], ints[2]);
		}
		return l;
	}


	private static World getWorld(String s) {
		return Bukkit.getServer().getWorld(s);
	}

	public static void guardar(Almacen nbt, Location l, String s) {
		if (l == null) {
			return;
		}
		int[] ints = { (int) l.getX(), (int) l.getY(), (int) l.getZ() };
		nbt.setIntArray(s, ints);
		nbt.setString(s + "W", l.getWorld().getName());
	}
	


}
