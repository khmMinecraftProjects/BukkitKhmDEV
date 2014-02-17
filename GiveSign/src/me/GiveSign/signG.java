package me.GiveSign;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import me.API.Almacenes.Almacen;
import me.API.Almacenes.Datos;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class signG implements Datos {
	private static final char separator=';';
	private List<String> Jugadores;
	private Location loc;
	private int id,ammount;
	private short data;
	public signG() {
		Jugadores = new LinkedList<String>();
		id=0;data=0;
		loc=null;
		ammount=1;
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
		String j = nbt.getString("jug");
		if(l.length==0||ws.length()==0){return;}
		World w = getWorld(ws);
		loc = (new Location(w, l[0], l[1], l[2]));
		Block b=loc.getBlock();
		if(b==null||(b.getType() != Material.SIGN && 
				b.getType() != Material.SIGN_POST
				&& b.getType() != Material.WALL_SIGN)){
			loc=null;
			return;
		}
		if(j.length()==0){return;}
		Jugadores = new LinkedList<String>();
		restoreJugadores(j);
		id=nbt.getInteger("id");
		ammount=nbt.getInteger("amou");
		data=nbt.getShort("data");

	}
	
	private void restoreJugadores(String s){
		if(s==""){return;}
		String j=getWord(s,0);
		if(j.length()==0){return;}
		Jugadores.add(j);
		restoreJugadores(s.substring(j.length()));
	}
	
	private String getWord(String e, int i) {
		String s = "";

		for (int j = 0; j < e.length() && i >= 0; j++) {
			if (e.charAt(j) == separator) {
				i--;
			} else if (i == 0) {
				s += e.charAt(j);
			}
		}
		return s;
	}
	@Override
	public void guardar(Almacen nbt) {
		if (loc != null) {
			int[] l = { (int) loc.getX(), (int) loc.getY(), (int) loc.getZ() };
			String ws = loc.getWorld().getName();
			nbt.setIntArray("loc", l);
			nbt.setString("w", ws);
			String js="";
			Iterator<String> it=Jugadores.iterator();

			while (it.hasNext()) {
				js+=it.next()+separator;
			}
			nbt.setString("jug", js);
			nbt.setInteger("id", id);
			nbt.setInteger("amou", ammount);
			nbt.setShort("data", data);

		}
	}

	public World getWorld(String world) {
		return Bukkit.getServer().getWorld(world);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setData(short dat) {
		data=dat;
	}
	public short getData() {
		return data;
	}
	
	public void setAmount(int Amount) {
		ammount=Amount;
	}
	
	public int getAmount() {
		return ammount;
	}
}
