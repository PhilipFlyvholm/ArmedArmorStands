/**
 * ArmedArmorStands By @author Philip Flyvholm
 * PlayerManager.java
 */
package me.phil14052.ArmedArmorStands.Managers;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.configuration.ConfigurationSection;

import java.util.UUID;

import me.phil14052.ArmedArmorStands.ArmedArmorStands;

/**
 * @author Philip
 *
 */
public class PlayerManager {
	private static PlayerManager instance = null;
	private ArmedArmorStands plugin = null;
	private Map<UUID, Boolean> enabledArms = null;
	
	public PlayerManager() {
		this.enabledArms = new HashMap<>();
		this.plugin = ArmedArmorStands.getInstance();
	}
	
	public void loadFromFile() {
		this.enabledArms = new HashMap<>();
		if(plugin.getPlayerConfig().contains("players")) {
			ConfigurationSection section = plugin.getPlayerConfig().getConfigurationSection("players");
			for(String s : section.getKeys(false)) {
				if(plugin.getPlayerConfig().contains("players." + s + ".armsEnabled")) {
					UUID uuid = UUID.fromString(s);
					if(uuid == null) continue;
					this.setArmsEnabled(uuid, plugin.getPlayerConfig().getBoolean("players." + s + ".armsEnabled"));
				}
			}
		}
	}
	
	public void saveToFile() {
		if(enabledArms == null || enabledArms.isEmpty()) return;
		for(Entry<UUID, Boolean> entry : enabledArms.entrySet()) {
			plugin.getPlayerConfig().set("players." + entry.getKey().toString() + ".armsEnabled", entry.getValue());
		}
	}
	
	public boolean isArmsEnabled(UUID uuid) {
		if(!plugin.getConfig().getBoolean("options.togglable.enabled")) return true;
		return this.enabledArms.containsKey(uuid)
				? this.enabledArms.get(uuid) 
				: plugin.getConfig().getBoolean("options.togglable.defaultMode");
	}
	
	public void setArmsEnabled(UUID uuid, boolean enabled) {
		if(enabledArms.containsKey(uuid)) enabledArms.remove(uuid);
		enabledArms.put(uuid, enabled);
	}
	
	public static PlayerManager getInstance() {
		if(instance == null) instance = new PlayerManager();
		return instance;
	}
}
