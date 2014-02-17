package me.API.Almacenes;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class Map implements Datos {

	private Zona zona;
	byte[] data;
	int[] blocks;
	String world, name;
	private int x,y,z;
	private boolean reparando;

	public Map(){
		setReparando(false);
	}
	
	public boolean isReparando() {
		return reparando;
	}

	public void setReparando(boolean reparando) {
		this.reparando = reparando;
	}

	public void setBlock(int[] block) {
		blocks = block;
	}

	public void setData(byte[] dat) {
		data = dat;
	}

	public int[] getBlock() {
		return blocks;
	}

	public byte[] getData() {
		return data;
	}

	public void setWorld(String id2) {
		world = id2;
	}

	public World getWorld() {
		return Bukkit.getServer().getWorld(world);
	}

	public void setName(String id2) {
		name = id2;
	}

	public String getName() {
		return name;
	}

	public void iniciarIt() {

	}

	@Override
	public void guardar(Almacen nbt) {
		if(zona!=null){
		int[] vecA = {zona.minX(), zona.minY(), zona.minZ() };
		int[] vecB = { zona.maxX(), zona.maxY(), zona.maxZ() };
		
		nbt.setIntArray("A", vecA);
		nbt.setIntArray("B", vecB);
		}
		if(getWorld()!=null){
		nbt.setString("World", getWorld().getName());
		}else{
			if(zona!=null){
				nbt.setString("World", zona.getWorld().getName());
			}
		}
		
		nbt.setByteArray("Data", getData());
		nbt.setIntArray("Blocks", getBlock());

	}

	@Override
	public void cargar(Almacen nbt) {

		setWorld(nbt.getString("World"));

		int[] vecA = nbt.getIntArray("A");

		int[] vecB = nbt.getIntArray("B");

		if(vecA.length>0&&vecB.length>0){
		zona=new Zona(new Location(getWorld(), vecA[0], vecA[1], vecA[2])
					, new Location(getWorld(), vecB[0], vecB[1], vecB[2]));
			}
		setData(nbt.getByteArray("Data"));

		setBlock(nbt.getIntArray("Blocks"));

		setName(nbt.getName());

	}


	public Zona getZona() {
		return zona;
	}

	public void setZona(Zona zona) {
		this.zona = zona;
	}
	public String toString(){
		
		return name+": "+zona;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}
}
