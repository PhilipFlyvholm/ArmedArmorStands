/**
 * ArmedArmorStands By @author Philip Flyvholm
 * PlayerEvents.java
 */
package me.phil14052.ArmedArmorStands.Events;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
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
		Block block = e.getClickedBlock();
		Player p = e.getPlayer();
		plugin.debug(block, (block.getType().isInteractable() && !p.isSneaking()));
		if(block.getType().isInteractable() && !p.isSneaking()) return; //Material#isInteractable is 1.13+
		ItemStack is = e.getItem();
		if(is == null) return;
		if(!is.getType().equals(Material.ARMOR_STAND)) return;
		if(plugin.getConfig().getBoolean("options.permissions.enabled")) {
			if(!plugin.getPermissionManager().hasPermission(p, plugin.getConfig().getString("options.permissions.permissionNode"), true))
				return;
		}
		plugin.debug(pm.isArmsEnabled(p.getUniqueId()));
		if(!pm.isArmsEnabled(p.getUniqueId())) return;
		e.setCancelled(true);
		Location l = block.getLocation();
		if(p.isSneaking()) l.add(e.getBlockFace().getDirection());
		else l.add(0, 1, 0); //If not sneaking place it on the block above
		l.add(0.5, 0, 0.5); //Center
		float yaw = p.getLocation().getYaw()+180F;
		l.setYaw(yaw);
		Entity entity = p.getWorld().spawnEntity(l, EntityType.ARMOR_STAND);
		if(entity instanceof ArmorStand) {
			ArmorStand as = (ArmorStand) entity;
			as.setArms(true);
		}
		if(!p.getGameMode().equals(GameMode.CREATIVE)) { //Test if gamemode is survival, adventure or spectator

			ItemStack mainHandIs = p.getInventory().getItemInMainHand();
			ItemStack offHandIs = p.getInventory().getItemInOffHand();
			if(e.getHand().equals(EquipmentSlot.HAND)) {
				if(mainHandIs.getAmount() > 1) { //If more than one armor stand in the hand
					mainHandIs.setAmount(mainHandIs.getAmount()-1);
				}else {
					mainHandIs = null;
				}
				p.getInventory().setItemInMainHand(mainHandIs);	
			}else if(e.getHand().equals(EquipmentSlot.OFF_HAND)) {
				if(offHandIs.getAmount() > 1) { //If more than one armor stand in the hand
					offHandIs.setAmount(offHandIs.getAmount()-1);
				}else {
					offHandIs = null;
				}
				p.getInventory().setItemInOffHand(offHandIs);	
			}
		}
	}
	
}
