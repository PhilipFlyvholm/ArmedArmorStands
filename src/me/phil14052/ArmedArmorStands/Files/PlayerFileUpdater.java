package me.phil14052.ArmedArmorStands.Files;

import org.bukkit.plugin.PluginDescriptionFile;

import me.phil14052.ArmedArmorStands.ArmedArmorStands;

public class PlayerFileUpdater {
	public PlayerFileUpdater(ArmedArmorStands plugin){
		PluginDescriptionFile pluginYml = plugin.getDescription();
		plugin.getPlayerConfig().options().header(pluginYml.getName() + "! Version: " + pluginYml.getVersion() + 
				" By Phil14052"
				+ "\nIMPORTANT: ONLY EDIT THIS IF YOU KNOW WHAT YOU ARE DOING!!");
		if(plugin.getPlayerConfig().getConfigurationSection("players") == null){
			plugin.getPlayerConfig().createSection("players");
		}
		
		plugin.getPlayerConfig().options().copyDefaults(true);
		plugin.savePlayerConfig();
	}

}
