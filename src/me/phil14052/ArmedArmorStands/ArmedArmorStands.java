package me.phil14052.ArmedArmorStands;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.phil14052.ArmedArmorStands.Commands.MainCommand;
import me.phil14052.ArmedArmorStands.Commands.ToggleCommand;
import me.phil14052.ArmedArmorStands.Events.PlayerEvents;
import me.phil14052.ArmedArmorStands.Files.ConfigUpdater;
import me.phil14052.ArmedArmorStands.Files.Files;
import me.phil14052.ArmedArmorStands.Files.Lang;
import me.phil14052.ArmedArmorStands.Files.LangFileUpdater;
import me.phil14052.ArmedArmorStands.Files.PlayerFileUpdater;
import me.phil14052.ArmedArmorStands.Managers.PermissionManager;
import me.phil14052.ArmedArmorStands.Managers.PlayerManager;

public class ArmedArmorStands extends JavaPlugin implements Listener {
	private static ArmedArmorStands plugin;
	private PlayerManager pm = null;
	public Files lang;
	private FileConfiguration playerConfig;
	private File playerConfigFile;
	private PermissionManager permissionManager;
	
	@Override
	public void onEnable(){
		plugin = this;
		plugin.debug("Enabling ArmedArmorStands plugin");
		plugin.log("&cIF YOU ENCOUNTER ANY BUGS OR ERRORS PLEASE REPORT THEM ON SPIGOT!");
		plugin.log("&4&lIF YOU WANT TO SUPPORT US JOIN OUR PATERON: https://www.patreon.com/woollydevelopment");
		permissionManager = new PermissionManager();
		// Setup config
		new ConfigUpdater();
		saveConfig();
		this.debug("The config is now setup&2 \u2713");
		// Setup lang file
		lang = new Files(this, "lang.yml");
		new LangFileUpdater(plugin);
		Lang.setFile(lang);
		this.debug("Lang is now setup&2 \u2713");
		// Setup player configs
		playerConfig = null;
		playerConfigFile = null;
		new PlayerFileUpdater(plugin);
		this.pm = PlayerManager.getInstance();
		this.pm.loadFromFile();
		this.debug("Players is now setup&2 \u2713");
		registerEvents();
		registerCommands();
		plugin.log("ArmedArmorStands is now enabled&2 \u2713");
	}
	
	@Override
	public void onDisable(){
		// Unload everything
		this.pm.saveToFile();
		this.savePlayerConfig();
		pm = null;
		plugin = null;
	}
	
	

	public void reloadPlugin() {
		this.reloadConfig();
		this.lang.reload();
		this.reloadPlayerConfig();
	}
	
	public void reloadPlayerConfig(){
		if(this.playerConfigFile == null){
			this.playerConfigFile = new File(new File(plugin.getDataFolder(), "Data"),"players.yml");
			this.playerConfig = YamlConfiguration.loadConfiguration(this.playerConfigFile);
			
		}
	}
	 //Return the player config
    public FileConfiguration getPlayerConfig() {
 
        if(this.playerConfigFile == null) this.reloadPlayerConfig();
 
        return this.playerConfig;
 
    }
 
    //Save the player config
    public void savePlayerConfig() {
 
        if(this.playerConfig == null || this.playerConfigFile == null) return;
 
        try {
            this.getPlayerConfig().save(this.playerConfigFile);
        } catch (IOException ex) {
            plugin.getServer().getLogger().log(Level.SEVERE, "Could not save Player config to " + this.playerConfigFile +"!", ex);
        }
 
    }
	
    private void registerCommands() {

		plugin.getCommand("armedarmorstands").setExecutor(new MainCommand());

		plugin.getCommand("togglearms").setExecutor(new ToggleCommand());
    }
    
	private void registerEvents(){
	    PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new PlayerEvents(), this);
	}
	
	public void debug(boolean overrideConfigOption, Object... objects) {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for(Object s : objects) {
			if(!first) {
				sb.append(", ");
			}else first = false;
			if(s == null) {
				sb.append("NULL");
			}else if(s instanceof String) {
				sb.append((String) s);
			}else {
				sb.append("[" + s.getClass().getTypeName() + ": " + s.toString() + "]");	
			}
		}
		this.debug(sb.toString());
	}
	
	public void debug(Object... objects) {
		this.debug(false, objects);
	}

	
	public void debug(Boolean booleanObject){
		this.debug(booleanObject.getClass().getTypeName() + ": "+ booleanObject);
	}
	public void debug(String message){
		this.debug(message, false);
	}
	public void debug(String message, boolean overrideConfigOption){
		if(overrideConfigOption == false && plugin.getConfig().getBoolean("debug") == false) return;
		Bukkit.getConsoleSender().sendMessage(("&8[&3&lArmedArmorStands&8]: &c&lDebug &8-&7 " + message).replace("&", "\u00A7"));
	}
	
	public void log(Object... objects) {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for(Object s : objects) {
			if(!first) {
				sb.append(", ");
			}else first = false;
			if(s == null) {
				sb.append("NULL");
			}else if(s instanceof String) {
				sb.append((String) s);
			}else {
				sb.append("[" + s.getClass().getTypeName() + ": " + s.toString() + "]");	
			}
		}
		this.log(sb.toString());
	}
	
	public void log(String message){
		Bukkit.getConsoleSender().sendMessage(("&8[&3&lArmedArmorStands&8]: &8&lLog &8-&7 " + message).replace("&", "\u00A7"));
	}
	
	public void error(String message) {
		this.error(message, false);
	}
	
	public void error(String message, boolean userError) {
		if(userError) {
			Bukkit.getConsoleSender().sendMessage(("&8[&3&lArmedArmorStands&8]: &4&lUser error &8-&c " + message).replace("&", "\u00A7"));	
		}else {

			Bukkit.getConsoleSender().sendMessage(("&8[&3&lArmedArmorStands&8]: &4&lError &8-&c " + message).replace("&", "\u00A7"));
		}
	}
	
	public void warning(String message) {
		Bukkit.getConsoleSender().sendMessage(("&8[&3&lArmedArmorStands&8]: &4&lWarning &8-&7 " + message).replace("&", "\u00A7"));
	}
	
	
	public static ArmedArmorStands getInstance(){
		return plugin;
	}

	public PermissionManager getPermissionManager() {
		return permissionManager;
	}

		
	
}
