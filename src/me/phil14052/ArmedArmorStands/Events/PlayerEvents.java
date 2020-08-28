/**
 * ArmedArmorStands By @author Philip Flyvholm
 * PlayerEvents.java
 */
package me.phil14052.ArmedArmorStands.Events;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.phil14052.ArmedArmorStands.ArmedArmorStands;
import me.phil14052.ArmedArmorStands.Managers.PlayerManager;

/**
 * @author Philip
 *
 */
public class PlayerEvents implements Listener{

	private static ArmedArmorStands plugin = ArmedArmorStands.getInstance();
	private static PlayerManager pm = PlayerManager.getInstance();
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if(!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
		Player p = e.getPlayer();
		ItemStack is = p.getInventory().getItemInMainHand();
		if(!is.getType().equals(Material.ARMOR_STAND)) return;
		if(plugin.getConfig().getBoolean("options.permissions.enabled")) {
			if(!plugin.getPermissionManager().hasPermission(p, plugin.getConfig().getString("options.permissions.permissionNode"), true))
				return;
		}
		if(!pm.isArmsEnabled(p.getUniqueId())) return;
		
		e.setCancelled(true);
		Location l = e.getClickedBlock().getLocation();
		l.add(0.5, 1, 0.5);
		float yaw = p.getLocation().getYaw()+180F;
		l.setYaw(yaw);
		Entity entity = p.getWorld().spawnEntity(l, EntityType.ARMOR_STAND);
		if(entity instanceof ArmorStand) {
			ArmorStand as = (ArmorStand) entity;
			as.setArms(true);
		}
		if(!p.getGameMode().equals(GameMode.CREATIVE)) { //Test if gamemode is survival, adventure or spectator
			if(is.getAmount() > 1) { //If more than one armor stand in the hand
				is.setAmount(is.getAmount()-1);
			}else {
				is = null;
			}
			p.getInventory().setItemInMainHand(is);		
		}
	}
	
}
