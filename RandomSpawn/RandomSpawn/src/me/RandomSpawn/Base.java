package me.RandomSpawn;

import me.API.Almacenes.Almacen;
import me.API.Almacenes.Central;
import me.API.Almacenes.Datos;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Base extends JavaPlugin implements Datos {

	private static Location[] spawn, random;
	Central central;
	private boolean actualizado = false;
	private int size = 0, MAX=10;
	private String message;
	public void initConfig() {
	
		this.getConfig().addDefault(this.getName() + ".Max_Spawns_points", 10);
		MAX=this.getConfig().getInt(this.getName() + ".Max_Spawns_points");

		this.getConfig().addDefault(this.getName() + ".Message", "&7[&6Kit-PvP&7]&7");
		message=this.getConfig().getString(this.getName() + ".Message");
		 message=ChatColor.translateAlternateColorCodes('&', message);
	}

	public void onEnable() {
		initConfig();

		spawn = new Location[MAX];
		central = new Central(this);
		central.insertar(this);
		central.cargar();
		getLogger().info("Pluging Cargado");
		

	}

	public void onDisable() {
		central.guardar();

	}
	private String help(){
		String s="";
		s+="/(spawnset/spawndel <0-"+(MAX-1)+">\n";
		return s;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("spawnset")) {
			if(sender.getName()=="CONSOLE"){return true;}
			Player pl=Bukkit.getServer().getPlayer(sender.getName());
			if(!pl.hasPermission("spawnpoints.create")){
				sender.sendMessage(Message("You haven't "+"spawnpoints.create"
						+" permission"));
				return true;
			}
			if(args.length==0){
				sender.sendMessage(help());
				return true;

			}
			if(!isInteger(args[0])){
				sender.sendMessage(help());
				return true;
			}
			int num=Integer.valueOf(args[0]);
			if(num>(MAX-1)||num<0){
				sender.sendMessage(help());
				return true;
			}
			addSpawn(num,pl.getLocation());

			sender.sendMessage(Message("Set spawn "+num));
			return true;
		}
		if (cmd.getName().equalsIgnoreCase("spawndel")) {
			if(sender.getName()=="CONSOLE"){return true;}
			Player pl=Bukkit.getServer().getPlayer(sender.getName());
			if(!pl.hasPermission("spawnpoints.create")){
				sender.sendMessage(Message("You haven't "+"spawnpoints.create"
			+" permission"));

				return true;
			}
			if(args.length==0){
				sender.sendMessage(help());
				return true;

			}
			if(!isInteger(args[0])){
				sender.sendMessage(help());
				return true;
			}
			int num=Integer.valueOf(args[0]);
			if(num>(MAX-1)||num<0){
				sender.sendMessage(help());
				return true;
			}
			removeSpawn(num);
			sender.sendMessage(Message("delete spawn "+num));
			return true;
		}
		if (cmd.getName().equalsIgnoreCase("join")) {
			if(sender.getName()=="CONSOLE"){return true;}

			Player pl=Bukkit.getServer().getPlayer(sender.getName());
			if(!pl.hasPermission("spawnpoints.join")){
				sender.sendMessage(Message("You haven't "+"spawnpoints.join"
						+" permission"));
				return true;
			}

			if(teleporRandom(pl)){
			sender.sendMessage(Message("Spawn random"));
			}else{
				sender.sendMessage(Message("No exist SpawnPoints"));

			}
			return true;
		}
		return false;
	}
	
	private boolean teleporRandom(Player pl){
		if(size==0){return false;}
		if(actualizado){
			pl.teleport(random[(int) (random.length*Math.random())]);
			return true;
		}else{
			createVector();
			return teleporRandom(pl);
		}
	}
	
	private String Message(String s){
		return message+" "+s;
	}
	private boolean isInteger(String s) {
		try {
			Integer.valueOf(s);
			return true;
		} catch (Exception e) {

		}
		return false;
	}
	@Override
	public void cargar(Almacen nbt) {
		if(nbt==null||!nbt.hasKey("Loc_size")){return;}
		size = nbt.getInteger("Loc_size");
		int i = 0;
		while (i < spawn.length) {
			Almacen dat = nbt.getAlmacen("Loc_" + i);
			if(dat!=null){
			int[] l = dat.getIntArray("loc");
			String w = dat.getString("w");
			if (w.length() == 0) {
				spawn[i] = null;
			} else {
				spawn[i] = (new Location(getWorld(w), l[0], l[1], l[2]));
			}
			}
			i++;
		}

	}

	public World getWorld(String world) {
		return Bukkit.getServer().getWorld(world);

	}

	private void createVector() {
		if(size==0){
			actualizado=true;
			random = null;
			return;
		}
		random = new Location[size];
		int a=0;
		for (int i = 0; i < spawn.length; i++) {
			if(spawn[i]!=null){
				random[a]=spawn[i];
				a++;
			}
		}
		actualizado=true;
	}

	@Override
	public void guardar(Almacen nbt) {
		nbt.setInteger("Loc_size", size);
		for (int i = 0; i < spawn.length; i++) {

			Almacen dat = nbt.geAlmacen("Loc_" + i);
			Location l = spawn[i];
			if (l != null) {
				int[] la = { (int) l.getX(), (int) l.getY(), (int) l.getZ() };
				dat.setIntArray("loc", la);
				dat.setString("w", l.getWorld().getName());
				nbt.setAlmacen("Loc_" + i, dat);
			} else {
				dat.setString("w", "");
				dat.setIntArray("loc", null);


			}

		}

	}

	public boolean removeSpawn(int l) {
		if (spawn[l] != null) {
			spawn[l] = null;
			size--;
			return true;
		}
		return false;
	}

	public boolean addSpawn(int l, Location lo) {
		if (spawn[l] == null) {
			spawn[l] = lo;
			size++;
			return true;
		}
		spawn[l] = lo;
		return false;
	}
}
