package me.ChestGiver;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import me.API.Almacenes.Almacen;
import me.API.Almacenes.Datos;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class Cofre implements Datos {
	private List<String> Jugadores;
	private Location loc;

	public Cofre() {
		Jugadores = new LinkedList<String>();

	}

	public Location getLoc() {
		return loc;
	}

	public void setLoc(Location loc) {
		this.loc = loc;
	}

	public void addPlayer(String p) {
		Jugadores.add(p);
	}

	public void removePlayer(String p) {
		Jugadores.remove(p);
	}

	public void clearPlayers() {
		Jugadores.clear();
	}

	public boolean containPlayer(String p) {
		return Jugadores.contains(p);
	}

	@Override
	public void cargar(Almacen nbt) {
		int[] l = nbt.getIntArray("loc");
		String ws = nbt.getString("w");
		String[] j = nbt.getStringArray("jug");
		World w = getWorld(ws);
		loc = (new Location(w, l[0], l[1], l[2]));
		if(j==null||j.length==0){return;}
		for (int i = 0; i < j.length; i++) {
			Jugadores.add(j[i]);
		}


	}

	@Override
	public void guardar(Almacen nbt) {
		if (loc != null) {
			int[] l = { (int) loc.getX(), (int) loc.getY(), (int) loc.getZ() };
			String ws = loc.getWorld().getName();
			nbt.setIntArray("loc", l);
			nbt.setString("w", ws);
			String[] js=new String[Jugadores.size()];
			Iterator<String> it=Jugadores.iterator();
			int i=0;
			while (it.hasNext()) {
				js[i]=it.next();
				i++;
			}
			nbt.setStringArray("jug", (String[]) js);
			
		}
	}

	public World getWorld(String world) {
		return Bukkit.getServer().getWorld(world);
	}
}
