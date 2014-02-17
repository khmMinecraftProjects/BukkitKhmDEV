package me.ChestGiver;

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
import org.bukkit.plugin.java.JavaPlugin;

public class Base extends JavaPlugin implements Datos {

	private static List<Cofre> chest;
	Central central;

	public void cargaConf() {

	}

	public void onEnable() {
		central = new Central(this);
		central.insertar(this);
		cargaConf();
		getServer().getPluginManager().registerEvents(new ListenChest(), this);
		central.cargar();
		getLogger().info("Pluging Cargado");

	}

	public void onDisable() {
		central.guardar();

	}

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {

		if (cmd.getName().equalsIgnoreCase("confirmchest")) {
			Player pl=Bukkit.getServer().getPlayer(sender.getName());
			if(!pl.hasPermission("CIG.confirmchest")){
				return false;}
			@SuppressWarnings("deprecation")
			Block b=pl.getTargetBlock(null, 3);
			if (b!=null&&(b.getType() == Material.CHEST
					|| b.getType() == Material.TRAPPED_CHEST)) {
				Cofre c=new Cofre();
				c.setLoc(b.getLocation());
				chest.add(c);
			}
			return true;
		}
		return false;
	}

	@Override
	public void cargar(Almacen nbt) {
		chest=new LinkedList<Cofre>();
		int max=nbt.getInteger("Chest_size"),i=0;
		
		nbt.hasKey("Chest_"+i);
		while (nbt.hasKey("Chest_"+i)&&i<max) {
			Cofre c=new Cofre();
			nbt.leer(c,"Chest_"+i);
		}

	}

	public World getWorld(String world) {
		return Bukkit.getServer().getWorld(world);

	}

	@Override
	public void guardar(Almacen nbt) {
			Iterator<Cofre> ws=chest.iterator();
			int i=0;
			nbt.setInteger("Chest_size", chest.size());
			while(ws.hasNext()){
				nbt.escribir(ws.next(), "Chest_"+i);
				i++;
			}

	}
	public static Cofre getCofre(Location l){
		Iterator<Cofre> it=chest.iterator();
		while(it.hasNext()){
			Cofre c=it.next();
			if(c.getLoc().equals(l)){
				return c;
			}
		}
		return null;
	}

	public static boolean removeCofre(Location l) {
		Iterator<Cofre> it=chest.iterator();
		while(it.hasNext()){
			Cofre c=it.next();
			if(c.getLoc().equals(l)){
				chest.remove(c);
				return true;
			}
		}
		return false;
	}
	

}
