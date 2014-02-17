package me.API.Almacenes;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class Zona implements Datos {
	private Location a, b;
	private int x, y, z;

	public Zona(Location aa, Location bb) {
		a = aa;
		b = bb;
		a = new Location(aa.getWorld(), Math.min(aa.getX(), bb.getX()),
				Math.min(aa.getY(), bb.getY()), Math.min(aa.getZ(), bb.getZ()));
		b = new Location(aa.getWorld(), Math.max(aa.getX(), bb.getX()),
				Math.max(aa.getY(), bb.getY()), Math.max(aa.getZ(), bb.getZ()));
		x = (int) (b.getX() - a.getX());
		y = (int) (b.getY() - a.getY());
		z = (int) (b.getZ() - a.getZ());

	}

	public Zona(Location aa, int xx, int yy, int zz) {
		a = aa;
		x = xx;
		y = yy;
		z = zz;

	}

	public Zona() {
	}

	public Location getLocA() {
		return a;
	}

	public Location getLocB() {
		return b;
	}

	public int minX() {
		return (int) a.getX();
	}

	public int maxX() {
		return minX() + x;
	}

	public int minY() {
		return (int) a.getY();
	}

	public int maxY() {
		return minY() + y;
	}

	public int minZ() {
		return (int) a.getZ();
	}

	public int maxZ() {
		return minZ() + z;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	public String toString() {
		return "X: " + minX() + "-" + maxX() + " Y: " + minY() + "-" + maxY()
				+ " Z: " + minZ() + "-" + maxZ();
	}

	public World getWorld() {
		return a.getWorld();
	}

	@Override
	public void cargar(Almacen nbt) {
		String w = nbt.getString("w");
		World wd = Bukkit.getServer().getWorld(w);

		int[] vecA = nbt.getIntArray("A");
		int[] vecB = nbt.getIntArray("B");
		a = new Location(wd, vecA[0], vecA[1], vecA[2]);
		b = new Location(wd, vecB[0], vecB[1], vecB[2]);
		x = (int) (b.getX() - a.getX());
		y = (int) (b.getY() - a.getY());
		z = (int) (b.getZ() - a.getZ());
	}

	@Override
	public void guardar(Almacen nbt) {

		if (a != null) {
			nbt.setString("w", a.getWorld().getName());
			int[] vecA = { (int) a.getX(), (int) a.getY(), (int) a.getZ() };
			nbt.setIntArray("A", vecA);
		}

		if (b != null) {
			int[] vecB = { (int) b.getX(), (int) b.getY(), (int) b.getZ() };
			nbt.setIntArray("B", vecB);
		}
	}

	public boolean esta(Location loc) {

		return (minX() <= loc.getX() && loc.getX() <= maxX()
				&& minY() <= loc.getY() && loc.getY() <= maxY()
				&& minZ() <= loc.getZ() && loc.getZ() <= maxZ());
	}
}
