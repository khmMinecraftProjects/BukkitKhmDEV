package me.NSBAux;

import me.NoteSoundBlock.Base;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;

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

	public static Location cargar(Metadatable pl, String s) {
		Location l=null;
		MetadataValue mw = Base.getMetadata(pl, s + "_W"), 
				mx = Base.getMetadata(pl, s + "_X"), 
				my = Base.getMetadata(pl, s + "_Y"), 
				mz = Base.getMetadata(pl, s + "_Z");
		if(mw!=null&&mx!=null&&my!=null&&mz!=null){
			World w = getWorld(mw.asString());
			int x=mx.asInt(),y=my.asInt(),z=mz.asInt();
			if (w == null) {
				return l;
			}
			l = new Location(w, x,y,z);

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
	
	public static void guardar(Metadatable pl, Location l, String s) {
		if(l==null){
			Base.removeMetadata(pl, s+"_W");
			Base.removeMetadata(pl, s+"_X");
			Base.removeMetadata(pl, s+"_Y");
			Base.removeMetadata(pl, s+"_Z");
			return;
		}
		Base.setMetadata(pl, s+"_W", l.getWorld().getName());
		Base.setMetadata(pl, s+"_X", l.getX());
		Base.setMetadata(pl, s+"_Y", l.getY());
		Base.setMetadata(pl, s+"_Z", l.getZ());
	}

	public static void guardar(Almacen nbt, Location l,String note
														, String s) {
		if (l == null) {
			return;
		}
		int[] ints = { (int) l.getX(), (int) l.getY(), (int) l.getZ() };
		nbt.setIntArray(s, ints);
		nbt.setString(s + "W", l.getWorld().getName());
		nbt.setString(s + "note", note);

	}

}
