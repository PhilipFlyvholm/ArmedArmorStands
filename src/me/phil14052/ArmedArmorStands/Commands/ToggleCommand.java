/**
 * ArmedArmorStands By @author Philip Flyvholm
 * MainCommand.java
 */
package me.phil14052.ArmedArmorStands.Commands;

import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.phil14052.ArmedArmorStands.ArmedArmorStands;
import me.phil14052.ArmedArmorStands.Files.Lang;
import me.phil14052.ArmedArmorStands.Managers.PlayerManager;

/**
 * @author Philip
 *
 */
public class ToggleCommand implements CommandExecutor{
	
	private static ArmedArmorStands plugin = ArmedArmorStands.getInstance();
	private static PlayerManager pm = PlayerManager.getInstance();
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(!plugin.getConfig().getBoolean("options.togglable.enabled")) {
			sender.sendMessage(Lang.PREFIX.toString() + Lang.TOGGLEABLE_DISABLED.toString());
			return false;
		}else if(!(sender instanceof Player)) {
			sender.sendMessage(Lang.PREFIX.toString() + Lang.PLAYER_ONLY.toString());
			return false;
		}else if(!plugin.getPermissionManager().hasPermission(sender, "armedarmorstands.toggle", true)) return false;
		else {
			Player p = (Player) sender;
			UUID uuid = p.getUniqueId();
			pm.setArmsEnabled(uuid, !pm.isArmsEnabled(uuid));
			String modeMessage = pm.isArmsEnabled(uuid) ? Lang.STRING_ENABLE.toString() : Lang.STRING_DISABLED.toString();
			p.sendMessage(Lang.PREFIX.toString() + Lang.PLAYER_TOGGLE.toString(modeMessage));
			return true;
		}
	}
	
}
