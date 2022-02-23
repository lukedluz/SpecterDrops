package com.lucas.specterdrops.events;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import com.lucas.specterdrops.Main;
import com.lucas.specterdrops.utils.Title;
import net.milkbowl.vault.economy.Economy;

public class Events implements Listener {

	@EventHandler
	public void onBreakBlock(BlockBreakEvent e) {
		Player p = e.getPlayer();
		Block b = e.getBlock();
		for (String key : Main.config.getConfig().getConfigurationSection("Grupos").getKeys(false)) {
			for (String blocos : Main.config.getConfig().getStringList("Grupos." + key + ".drops")) {
				if (b.getType() == Material.getMaterial(blocos)) {
					if (p.getItemInHand().getType() == Material.DIAMOND_SWORD) {
						return;
					}
					if (p.getItemInHand().getType() == Material.GOLD_SWORD) {
						return;
					}
					if (p.getItemInHand().getType() == Material.IRON_SWORD) {
						return;
					}
					if (p.getItemInHand().getType() == Material.STONE_SWORD) {
						return;
					}
					if (p.getItemInHand().getType() == Material.WOOD_SWORD) {
						return;
					}
					if (!p.hasPermission(Main.config.getConfig().getString("Grupos." + key + ".permission"))) {
						return;
					}
					if (b.getType() == Material.NETHER_WARTS) {
						return;
					}
					int dropsqntd = b.getDrops().size();
					int xp = e.getExpToDrop();
					p.giveExp(xp);
					e.setCancelled(true);
					String blocotipo = b.getType().toString();
					int numero = Main.data.getConfig().getInt("Jogadores." + p.getName() + "." + blocotipo);
					if (Main.data.getConfig().contains("Jogadores." + p.getName() + "." + blocotipo)) {
						Main.data.getConfig().set("Jogadores." + p.getName() + "." + blocotipo, numero + dropsqntd);
					} else {
						Main.data.getConfig().set("Jogadores." + p.getName() + "." + blocotipo, dropsqntd);
					}
					Main.data.saveConfig();
					Title.ActionBar(Main.config.getConfig().getString("actionbar").replace("&", "§"), p);
					b.setType(Material.AIR);
				}
			}
		}
	}

	@EventHandler
	public void onKill(EntityDeathEvent e) {
		Player p = (Player) e.getEntity().getKiller();
		for (String mobs : Main.config.getConfig().getStringList("Mobs")) {
			if (e.getEntity().getType() == EntityType.valueOf(mobs)) {
				int drops = e.getDrops().size();
				String drop1243 = e.getDrops().get(0).toString();
				String dropnome = drop1243.replace("ItemStack", "").replace("{", "").replace("}", "").replace(" ", "")
						.replace("x", "").replace(",", "").replace("[", "").replace("]", "").replace("0", "")
						.replace("1", "").replace("2", "").replace("3", "").replace("4", "").replace("5", "")
						.replace("6", "").replace("7", "").replace("8", "").replace("9", "");

				int numero = Main.data.getConfig().getInt("Jogadores." + p.getName() + "." + dropnome);
				if (Main.data.getConfig().contains("Jogadores." + p.getName() + "." + dropnome)) {
					Main.data.getConfig().set("Jogadores." + p.getName() + "." + dropnome, numero + drops);
				} else {
					Main.data.getConfig().set("Jogadores." + p.getName() + "." + dropnome, drops);
				}
				Main.data.saveConfig();
				e.getDrops().clear();
			}
		}
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		Economy eco = Main.getPlugin().getEconomy();
		if (e.getInventory().getTitle().equals("Drops")) {
			e.setCancelled(true);
			if (e.getInventory().getItem(10) == null) {
				return;
			}
			eco.depositPlayer(p, Integer.parseInt(e.getCurrentItem().getItemMeta().getLore().get(2)
					.replace("Preço: ", "").replace("§", "").replace("f", "").replace("a", "")));
			if (Integer.parseInt(e.getCurrentItem().getItemMeta().getLore().get(1).replace("Quantidade: ", "")
					.replace("§", "").replace("f", "").replace("a", "")) >= 2) {
				p.sendMessage(
						"§aVocê vendeu "
								+ Integer.parseInt(e.getCurrentItem().getItemMeta().getLore().get(1)
										.replace("Quantidade: ", "").replace("§", "").replace("f", "").replace("a", ""))
								+ " itens por "
								+ Integer.parseInt(e.getCurrentItem().getItemMeta().getLore().get(2)
										.replace("Preço: ", "").replace("§", "").replace("f", "").replace("a", ""))
								+ " coins.");
			} else {
				p.sendMessage(
						"§aVocê vendeu "
								+ Integer.parseInt(e.getCurrentItem().getItemMeta().getLore().get(1)
										.replace("Quantidade: ", "").replace("§", "").replace("f", "").replace("a", ""))
								+ " item por "
								+ Integer.parseInt(e.getCurrentItem().getItemMeta().getLore().get(2)
										.replace("Preço: ", "").replace("§", "").replace("f", "").replace("a", ""))
								+ " coins.");
			}
			Main.data.getConfig().set("Jogadores." + p.getName() + "." + e.getCurrentItem().getType(), null);
			Main.data.saveConfig();
			p.chat("/drops");

		}
	}

}
