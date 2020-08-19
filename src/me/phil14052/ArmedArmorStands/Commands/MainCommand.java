/**
 * ArmedArmorStands By @author Philip Flyvholm
 * MainCommand.java
 */
package me.phil14052.ArmedArmorStands.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

import me.phil14052.ArmedArmorStands.ArmedArmorStands;
import me.phil14052.ArmedArmorStands.Files.Lang;

/**
 * @author Philip
 *
 */
public class MainCommand implements CommandExecutor{
	
	private static ArmedArmorStands plugin = ArmedArmorStands.getInstance();
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(args.length < 1){
				PluginDescriptionFile pluginYml = plugin.getDescription();
			sender.sendMessage("     ");
			sender.sendMessage("§7§m---------------------------------------");
			sender.sendMessage("     ");
			sender.sendMessage("  §3§l" + pluginYml.getName() + "§7 By §3§l" + pluginYml.getAuthors());
			sender.sendMessage("     ");
			sender.sendMessage("  §7Version: §3§l" + pluginYml.getVersion());
			sender.sendMessage("     ");
			sender.sendMessage("§7§m---------------------------------------");
			sender.sendMessage("     ");
			return true;
		}else if(args[0].equalsIgnoreCase("help")) {

			//TODO add help
			sender.sendMessage("TODO");
			return true;
		}else if(args[0].equalsIgnoreCase("v")){
			sender.sendMessage("AAS V: " + plugin.getDescription().getVersion());
			return true;
		}else if(args[0].equalsIgnoreCase("reload")) {
			if(!plugin.getPermissionManager().hasPermission(sender, "armedarmorstands.admin.reload", true)) return false;
			plugin.reloadConfig();
			return true;
		}else {
			sender.sendMessage(Lang.PREFIX.toString() + Lang.INVALID_ARGS.toString());
			return false;
		}
	}
	
}
