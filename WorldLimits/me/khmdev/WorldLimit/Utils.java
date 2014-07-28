package me.khmdev.WorldLimit;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class Utils {
	public static Location getLocation(String pos) {
		World world = getWorld(getSeparate(pos, 0, ';'));

		double x = getDouble(getSeparate(pos, 1, ';'), 0), 
				y = getDouble(getSeparate(pos, 2, ';'), 0), 
				z = getDouble(getSeparate(pos, 3, ';'), 0);
		return new Location(world, x, y, z);
	}

	public static World getWorld(String s) {

		World w = Bukkit.getWorld(s);
		return w == null ? Bukkit.getWorlds().get(0) : w;

	}

	public static String getSeparate(String txt, int i, char sp) {
		String s = "";
		int o = 0, word = 0;
		if (txt == null) {
			return "";
		}
		while (o < txt.length() && word <= i) {
			s = "";

			while (o < txt.length() && !(txt.charAt(o) == sp)) {

				s += txt.substring(o, o + 1);
				o++;

			}

			o++;
			word++;
		}
		if (word <= i) {
			return "";
		}
		return s;
	}

	public static double getDouble(String s, int defaul) {
		try {

			return Double.valueOf(s);
		} catch (Exception e) {
			return defaul;
		}
	}

}
