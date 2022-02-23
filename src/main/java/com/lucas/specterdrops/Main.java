package com.lucas.specterdrops;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import com.lucas.specterdrops.commands.CommandDrop;
import com.lucas.specterdrops.events.Events;
import com.lucas.specterdrops.utils.Config;
import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin {
	
	public static Economy econ;
	public static Config data;
	public static Config config;
	
	@Override
	public void onEnable() {
		if (!setupEconomy()) {
			Bukkit.getPluginManager().disablePlugin((Plugin) this);
			return;
		}
		data = new Config(this, "data.yml");
		config = new Config(this, "config.yml");
		data.saveDefaultConfig();
		config.saveDefaultConfig();
		getCommand("drop").setExecutor(new CommandDrop());
		getCommand("drops").setExecutor(new CommandDrop());
		Bukkit.getPluginManager().registerEvents(new Events(), this);
	}
	
	@Override
	public void onDisable() {
		HandlerList.unregisterAll();
	}
	
	public static Main getPlugin() {
		return (Main) getPlugin(Main.class);
	}
	
	private boolean setupEconomy() {
		if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = (Economy) rsp.getProvider();
		return (econ != null);
	}
	
    public Economy getEconomy() {
        return econ;
    }

}
