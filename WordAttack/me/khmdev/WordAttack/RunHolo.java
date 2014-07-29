package me.khmdev.WordAttack;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.dsh105.holoapi.HoloAPI;
import com.dsh105.holoapi.api.Hologram;

public class RunHolo extends BukkitRunnable {
	private Vector vec;
	private Hologram holo;
	private int id = -1, dam, v;
	private Player pl;

	public RunHolo(Vector vv, Hologram h, int damange, int velocity,
			Player player) {
		vec = vv;
		holo = h;
		dam = damange;
		v = velocity;
		pl = player;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		Location l = holo.getDefaultLocation();
		for (Player p : Bukkit.getOnlinePlayers()) {

			if (p.getLocation().distance(l) < v ||
					p.getEyeLocation().distance(l) < v
					&& pl != p) {
				p.damage(dam, pl);
				kill();
				return;
			}
		}
		if (l.getWorld().getBlockAt(l).getType() != Material.AIR) {
			kill();
			return;
		}

		holo.move(l.add(vec.multiply(v)));

	}

	private void kill() {
		HoloAPI.getManager().stopTracking(holo);
		if (id == -1) {
			return;
		}
		Bukkit.getServer().getScheduler().cancelTask(id);
		return;
	}

	public void setId(int idd) {
		id = idd;
	}
}
