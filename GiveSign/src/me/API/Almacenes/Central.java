package me.API.Almacenes;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;


public class Central {
	Almacen almacen;
	List<Datos> lista;
	public Central(JavaPlugin plug){
		almacen = new AlmacenNBT(plug.getName()+File.separator+plug.getName()+".dat");
		lista=new LinkedList<Datos>();
	}
	
	public void CargaConfig(JavaPlugin plug){
		plug.getConfig().options().copyDefaults(true); 
		Conf.initConfig(plug.getConfig());
		plug.saveConfig();
	}
	public void insertar(Datos dat){
		lista.add(dat);
	}
	
	public void cargar(){
		Iterator<Datos> it=lista.iterator();
		while(it.hasNext()){
			it.next().cargar(almacen);
		}
	}
	public void guardar() {
		Iterator<Datos> it=lista.iterator();
		while(it.hasNext()){
			it.next().guardar(almacen);
		}
		almacen.finalizar();
	}
}
