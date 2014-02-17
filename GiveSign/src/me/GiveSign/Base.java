package me.GiveSign;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import me.API.Almacenes.Almacen;
import me.API.Almacenes.Central;
import me.API.Almacenes.Datos;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Base extends JavaPlugin implements Datos {

	private static List<signG> signs;
	Central central;
	private static String message=ChatColor.GREEN+"[GiveSign] "+ChatColor.WHITE;
	public void initConfig() {
		

	}

	public void onEnable() {
		initConfig();
		central = new Central(this);
		central.insertar(this);
		getServer().getPluginManager().registerEvents(new Listen(), this);
		central.cargar();
		
		getLogger().info("Pluging Cargado");
		

	}

	public void onDisable() {
		central.guardar();

	}



	@Override
	public void cargar(Almacen nbt) {
		signs=new LinkedList<signG>();
		int max=nbt.getInteger("Sign_size"),i=0;
		while (nbt.hasKey("Sign_"+i)&&i<max) {

			signG c=new signG();
			nbt.leer(c,"Sign_"+i);
			if(c.getLoc()!=null){
				signs.add(c);
			}
			
			i++;
		}
		if(max!=signs.size()){
			System.out.println("Remove "+(max-signs.size())+"/"+max);
		}

	}

	public World getWorld(String world) {
		return Bukkit.getServer().getWorld(world);

	}

	public static String sendMessage(String s){
		return message+s;
	}
	
	@Override
	public void guardar(Almacen nbt) {
			Iterator<signG> ws=signs.iterator();
			int i=0;
			nbt.setInteger("Sign_size", signs.size());
			while(ws.hasNext()){
				nbt.escribir(ws.next(), "Sign_"+i);
				i++;
			}

	}
	public static signG getSign(Location l){
		Iterator<signG> it=signs.iterator();
		while(it.hasNext()){
			signG c=it.next();
			if(c.getLoc().equals(l)){
				return c;
			}
		}
		return null;
	}
	public static void addSign(signG l) {
		signs.add(l);
	}
	public static boolean removeSign(Location l) {
		Iterator<signG> it=signs.iterator();
		while(it.hasNext()){
			signG c=it.next();
			if(c.getLoc().equals(l)){
				signs.remove(c);
				return true;
			}
		}
		return false;
	}

	public static void clearJugador(Player entity) {
		Iterator<signG> it=signs.iterator();
		while(it.hasNext()){
			it.next().removePlayer(entity.getName());	
		}
		
	}
	

}
