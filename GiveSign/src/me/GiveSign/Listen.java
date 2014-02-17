package me.GiveSign;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Listen implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Block b = event.getClickedBlock();
			if (b.getType() == Material.SIGN || 
					b.getType() == Material.SIGN_POST
					|| b.getType() == Material.WALL_SIGN) {
				signG c = Base.getSign(b.getLocation());
				if (c == null) {
					return;
				}

				event.setCancelled(true);
				Player pl = event.getPlayer();
				if (!pl.hasPermission("SignGiver.use")) {
					pl.sendMessage(Base.sendMessage(
							"You haven't permission"));
				}
				if (c.containPlayer(pl.getName())) {
					pl.sendMessage(Base.sendMessage(
							"This sign has yet been used"));

					return;
				}
				System.out.println(c.getId()+" "+c.getData()+" "+
										c.getAmount());
				ItemStack it=new ItemStack(c.getId(),c.getAmount(),c.getData());
				pl.getInventory().addItem(it);
				pl.updateInventory();

				pl.sendMessage(Base.sendMessage("Give you "+it.getType()));
				c.addPlayer(pl.getName());

				return;
			}
		}

	}
	private static final String name="[GiveSign]";
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onSignChange(SignChangeEvent event) {
		String s = event.getLine(0);

		if (getWord(s, 0).equals(name)) {
			if (!event.getPlayer().hasPermission("SignGiver.create")) {
				event.getPlayer().sendMessage(Base.sendMessage(
						"You haven't permission"));
				return;
			}
			
			s = getWordSeparator(event.getLine(1), 0);
			if (isInteger(s)) {
				signG sign = new signG();
				sign.setId(Integer.valueOf(s));
				sign.setLoc(event.getBlock().getLocation());
				Base.addSign(sign);
				s = getWordSeparator(event.getLine(1), 1);
				ItemStack it;
				if(isInteger(s)){
					int data=Integer.valueOf(s);
					sign.setData((short)data);

				}
				it=new ItemStack(sign.getId(),sign.getData());
				s = getWord(event.getLine(2), 0);
				if(isInteger(s)){
					int amount=Integer.valueOf(s);
					sign.setAmount(amount);
					event.setLine(2, amount+"");
				}
				
				event.setLine(1, it.getType().toString());
				event.setLine(0, ChatColor.GREEN+name);
			} else {
				event.setLine(0, ChatColor.RED+"Wrong format");
				event.setLine(1, "");
				event.setLine(2, "");
				event.setLine(3, "");
			}
		}

	}

	private boolean isInteger(String s) {
		try {
			Integer.valueOf(s);
			return true;
		} catch (Exception e) {

		}
		return false;
	}

	private String getWord(String e, int i) {
		String s = "";

		for (int j = 0; j < e.length() && i >= 0; j++) {
			if (e.charAt(j) == ' ') {
				i--;
			} else if (i == 0) {
				s += e.charAt(j);
			}
		}
		return s;
	}
	private static final char separator=':';
	private String getWordSeparator(String e, int i) {
		String s = "";

		for (int j = 0; j < e.length() && i >= 0; j++) {
			if (e.charAt(j) == separator) {
				i--;
			} else if (i == 0) {
				s += e.charAt(j);
			}
		}
		return s;
	}
	@EventHandler
	public void onPlayerInteract(BlockBreakEvent event) {
		Block b = event.getBlock();
		if (b.getType() == Material.SIGN || b.getType() == Material.SIGN_POST
				|| b.getType() == Material.WALL_SIGN) {
			if (Base.getSign(b.getLocation()) != null) {
				if (event.getPlayer() != null) {
					Player pl=event.getPlayer();
					if(!pl.hasPermission("SignGiver.remove")){
						event.setCancelled(true);
						pl.sendMessage(Base.sendMessage(
								"You haven't permissions"));
						return;
					}
					Base.removeSign(b.getLocation());
					pl.sendMessage(Base.sendMessage("SignGiver remove"));

				}
			}
		}
	}
	@EventHandler
	public void onDeath(PlayerDeathEvent event) {

		Base.clearJugador(event.getEntity());
	}
}
