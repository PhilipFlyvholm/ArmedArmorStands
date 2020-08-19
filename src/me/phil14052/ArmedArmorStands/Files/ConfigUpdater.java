package me.phil14052.ArmedArmorStands.Files;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;

import me.phil14052.ArmedArmorStands.ArmedArmorStands;

public class ConfigUpdater extends YamlConfiguration {
	
	private ArmedArmorStands plugin = ArmedArmorStands.getInstance();
	
	public ConfigUpdater() {
		PluginDescriptionFile pluginYml = plugin.getDescription();
		FileConfiguration config = plugin.getConfig();
		config
				.options()
				.header(pluginYml.getName() + "! Version: "
						+ pluginYml.getVersion()
						+ " By " + pluginYml.getAuthors().get(0));
		config.options().copyHeader();
		config.addDefault("debug", false);
		config.addDefault("options.permissions.enabled", false);
		config.addDefault("options.permissions.permissionNode", "armedarmorstands.use");
		config.addDefault("options.togglable.enabled", true);
		config.addDefault("options.togglable.defaultMode", true);
		config.options().copyDefaults(true);
		plugin.saveDefaultConfig();
	}

}
