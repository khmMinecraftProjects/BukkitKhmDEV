package me.khmdev.WordAttack;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;


public class CargarWords {

	public static void cargar(JavaPlugin plug) {
		ConfigFile conf = new ConfigFile(plug.getDataFolder(), "Words");
		FileConfiguration section = conf.getConfig();
		List<String> list=section.getStringList("Words");
		if(list==null||list.size()==0){
			WordItem.addWord("Tío");
			WordItem.addWord("Mola");
			WordItem.addWord("¿Pero que pone en mi tatuaje?");
			WordItem.addWord("¿Y en el mio?");
			section.set("Words", WordItem.getList());
			conf.saveConfig();
			return;}
		for (String s : list) {
			WordItem.addWord(s);
		}
	}

}
