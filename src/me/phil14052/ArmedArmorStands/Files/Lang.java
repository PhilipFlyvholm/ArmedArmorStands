package me.phil14052.ArmedArmorStands.Files;
 
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
 
/**
* An enum for requesting strings from the language file.
* Made by gomeow.
* Lang added by phil14052.
* @author gomeow
*/

public enum Lang {
	
    PREFIX("prefix", "&8[&3ArmedArmorStands&8]"),
    INVALID_ARGS("invalid-args", "&cInvalid args!"),
    PLAYER_ONLY("player.players-only", "&cSorry but that can only be run by a player!"),
    PLAYER_TOGGLE("player.toggle", "&8Toggled arms on armorstands to &6%s1"),
    STRING_ENABLE("string.enabled", "&aEnabled"),
    STRING_DISABLED("string.disabled", "&cdisabled"),
    NO_PERMS("player.no-permission", "&cYou do not have permission for this"),
    TOGGLEABLE_DISABLED("player.toggleable-arms-disabled", "&cToggleable arms are disabled");
    
    
    private String path;
    private String def;
    private static YamlConfiguration LANG;

    /**
    * Lang enum constructor.
    * @param path The string path.
    * @param start The default string.
    */
    Lang(String path, String start) {
        this.path = path;
        this.def = start;
    }
 
    /**
    * Set the {@code YamlConfiguration} to use.
    * @param config The config to set.
    */
    public static void setFile(YamlConfiguration config) {
        LANG = config;
    }
 
    public static String color(String s) {
    	return ChatColor.translateAlternateColorCodes('&', s);
    }
    
    @Override
    public String toString() {
    	String string = color(LANG.getString(this.getPath()));
        if (this == PREFIX) string = string + " ";
        return string;
    }
    
    public static String replacePlaceholders(Player p, String string) {
    	if(p != null && p.isOnline()) {
    		string = string.replace("%player_name%", p.getName());
    		
    	}
    	return string;
    }
    
    public String toString(Player p) {
    	String string = this.toString();
    	string = replacePlaceholders(p, string);
        return string;
    }
    public String toString(String... strings) {
    	String string = this.toString();
    	int i = 0;
    	for(String s : strings) {
    		i++;
    		string = string.replaceFirst("%s" + i, s);
    	}
    	return string;
    }
    
    public List<String> toStringList(){
    	List<String> s = LANG.getStringList(this.path);
    	List<String> colored_s = new ArrayList<String>();
    	for(String string : s){
    		colored_s.add(color(string));
    	}
    	return colored_s;
    }
    
    public List<String> toStringList(Player p){
    	List<String> s = LANG.getStringList(this.path);
    	List<String> colored_s = new ArrayList<String>();
    	for(String string : s){
    		string = color(string);
    		if(p != null && p.isOnline()) {
        		string = string.replace("%player_name%", p.getName());
        		
        	}
    		colored_s.add(string);
    	}
    	return colored_s;
    }
    
    public List<String> toStringList(String... strings){
    	List<String> stringList = LANG.getStringList(this.path);
    	List<String> colored_s = new ArrayList<String>();
    	for(String string : stringList){
    		string = color(string);
    		int i = 0;
    		for(String s : strings) {
        		i++;
        		string = string.replaceFirst("%s" + i, s);
        	}
    		colored_s.add(string);
    	}
    	return colored_s;
    }
    
    /**
    * Get the default value of the path.
    * @return The default value of the path.
    */
    public String getDefault() {
        return this.def;
    }
 
    /**
    * Get the path to the string.
    * @return The path to the string.
    */
    public String getPath() {
        return this.path;
    }
}