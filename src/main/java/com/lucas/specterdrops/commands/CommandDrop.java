package com.lucas.specterdrops.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import com.lucas.specterdrops.Main;
import com.lucas.specterdrops.utils.ItemBuilder;

public class CommandDrop implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] a) {
		Player p = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("drops") || cmd.getName().equalsIgnoreCase("drop")) {
			if (!(sender instanceof Player)) {
				return false;
			}
			Inventory inv = Bukkit.createInventory(null, 45, "Drops");
			int slot = 9;
			for (String key : Main.config.getConfig().getConfigurationSection("Grupos").getKeys(false)) {
				for (String blocos : Main.config.getConfig().getStringList("Grupos." + key + ".drops")) {
					if (Main.data.getConfig().getString("Jogadores." + p.getName() + "." + blocos) != null) {
						slot++;
						int qnt = Main.data.getConfig().getInt("Jogadores." + p.getName() + "." + blocos);
						int preco = qnt * Main.config.getConfig().getInt("Grupos." + key + ".preço");
						String bloco2 = blocos.replace("SUGAR_CANE_BLOCK", "SUGAR_CANE").replace("NETHER_WARTS", "NETHER_STALK");
						inv.setItem(slot, new ItemBuilder(Material.getMaterial(bloco2))
								.setLore("", "§fQuantidade: §a" + qnt, "§fPreço: §a" + preco).build());
					}
				}
			}
			
			if (inv.getItem(10) == null) {
				inv.setItem(22,
						new ItemBuilder(Material.BARRIER).setName("§cVocê não tem nenhum drop").build());

			}

			p.openInventory(inv);
		}
		return false;
	}

}
