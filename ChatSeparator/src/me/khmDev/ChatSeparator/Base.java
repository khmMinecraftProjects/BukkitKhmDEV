package me.khmDev.ChatSeparator;

import java.util.HashMap;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Base extends JavaPlugin {

	private static HashMap<String, WorldMessage> list;
	private static String prefix="&a<",suffix="> ",
						general="General";
	private boolean isInvisibles=true;
	private void cargaConfig(){
		
		if(getConfig().isBoolean("Tab_Invisibles")){
			isInvisibles=getConfig().getBoolean("Tab_Invisibles");
		}else{
			getConfig().set	("Tab_Invisibles", isInvisibles);
		}
		
		prefix=getStringConfig(getConfig(), "Prefijo_Estandar", prefix);

		suffix=getStringConfig(getConfig(), "Sufijo_Estandar", suffix);

		general=getStringConfig(getConfig(), "General_Estandar", general);

		
		Iterator<String> it=getConfig().getKeys(false).iterator();
		while (it.hasNext()) {
			String next=it.next();
			if(getConfig().isConfigurationSection(next)){
				ConfigurationSection section=getConfig().getConfigurationSection(next);
				
				String p=getStringConfig(section, "Prefijo", ""),
						s=getStringConfig(section, "Sufijo", "")
						,a=getStringConfig(section, "Alias", "");
				
				list.put(next, new WorldMessage(p, s, a));
				
			}
		}
		this.saveConfig();
	}
	
	private String getStringConfig(ConfigurationSection section,
									String path,String defaul){
		String a=defaul;
		if(section.isString(path)){
			a=section.getString(path);
		}else{
			section.set(path, defaul);
		}
		return ChatColor.translateAlternateColorCodes('&',a);
	}
	
	public void onEnable() {
		list=new HashMap<>();
		cargaConfig();
		if(isInvisibles){
			getServer().getPluginManager()
					.registerEvents(new ListenVisible(), this);

		}
		getServer().getPluginManager().registerEvents(new Listen(), this);
		getLogger().info("Pluging Cargado");

	}

	public void onDisable() {

	}


	
	public static String generarMensaje(String w,String m){
		WorldMessage c=list.get(w);
		if(c!=null){
			if(c.getAlias().length()==0){
				m=c.getPref()+w+c.getSuf()+ChatColor.WHITE+m;
			}else{
				m=c.getPref()+c.getAlias()+c.getSuf()+ChatColor.WHITE+m;
			}
		}else{
			m=prefix+w+suffix+ChatColor.WHITE+m;
		}
		
		return m;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("all")) {
			String m="";
			for (int i = 0; i < args.length; i++) {
				m=args[i];
			}
			m=ChatColor.translateAlternateColorCodes('&', m);
			m="<"+sender.getName()+"> "+prefix+general+suffix+ChatColor.WHITE+m;
			for (Player player : Bukkit.getServer().getOnlinePlayers()) {
				player.sendMessage(m);
			}
			
			return true;
		}
		return false;
	}
}
