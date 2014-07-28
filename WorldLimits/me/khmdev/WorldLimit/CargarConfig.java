package me.khmdev.WorldLimit;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;


public class CargarConfig {

	public static void cargar(JavaPlugin plug) {
		ConfigFile conf = new ConfigFile(plug.getDataFolder(), "Limits");
		FileConfiguration section = conf.getConfig();

		for (String s : section.getKeys(false)) {
			if (section.isConfigurationSection(s)) {
				ConfigurationSection p=section.getConfigurationSection(s);
				World w=Utils.getWorld(s);
				ChangeWorld n=new ChangeWorld(w);
				
				if(p.contains("minX")){
					Double minX=p.getDouble("minX");
					Location minXP=Utils.getLocation(p.getString("minXP"));
					n.setMinX(minX, minXP);
				}
				if(p.contains("maxX")){
					Double maxX=p.getDouble("maxX");
					Location maxXP=Utils.getLocation(p.getString("maxXP"));
					n.setMaxX(maxX, maxXP);
				}
				
				if(p.contains("minY")){
					Double minY=p.getDouble("minY");
					Location minYP=Utils.getLocation(p.getString("minYP"));
					n.setMinY(minY, minYP);
				}
				if(p.contains("maxY")){
					Double maxY=p.getDouble("maxY");
					Location maxYP=Utils.getLocation(p.getString("maxYP"));
					n.setMaxY(maxY, maxYP);
				}
				
				if(p.contains("minZ")){
					Double minZ=p.getDouble("minZ");
					Location minZP=Utils.getLocation(p.getString("minZP"));
					n.setMinZ(minZ, minZP);
				}
				if(p.contains("maxZ")){
					Double maxZ=p.getDouble("maxZ");
					Location maxZP=Utils.getLocation(p.getString("maxZP"));
					n.setMaxZ(maxZ, maxZP);
				}
				ListenerWorld.addLimit(n);
			}
		}

	}


}
