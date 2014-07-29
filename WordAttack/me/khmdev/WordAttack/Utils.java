package me.khmdev.WordAttack;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Utils {
	@SuppressWarnings("deprecation")
	public static ItemStack getItem(Material m, String name, int id,
			String... params) {
		ItemStack item = new ItemStack(m, 1, (short)1,(byte) id);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		item.setItemMeta(meta);
		item=addDescription(item,params);

		return item;
	}
	private static final int max = 40;
	public static ItemStack addDescription(ItemStack it, String... s) {
		if (it == null) {
			return it;
		}

		ItemMeta meta = it.getItemMeta();
		List<String> lore = meta.getLore();
		if (lore == null) {
			lore = new LinkedList<>();
		}

		for (int i = 0; i < s.length; i++) {
			String lor =ChatColor.translateAlternateColorCodes('&', s[i]);

			while (lor.length() != 0) {
				int m = Math.min(max, lor.length());
				String aux = lor.substring(0, m);
				lor = lor.substring(m);
				lore.add(aux);
			}

		}
		meta.setLore(lore);
		it.setItemMeta(meta);
		return it;
	}
}
