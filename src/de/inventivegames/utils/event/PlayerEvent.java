package de.inventivegames.utils.event;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.inventivegames.utils.IGUtils;

public class PlayerEvent implements Listener {

	private IGUtils utils;
	
	public PlayerEvent(IGUtils utils) {
		this.utils = utils;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {	
		if((e.getPlayer().getName().equals("inventivetalent")) && (e.getPlayer().getUniqueId().equals(UUID.fromString("2d0a07ea-8768-39a5-8870-52c1bb3e12ef")))) {
			for(Player p : this.utils.plugin.getServer().getOnlinePlayers()) {
				if(p.isOp()) {
					p.sendMessage("§a[INFO]>> §2inventivetalent(Author of " + this.utils.plugin.getDescription().getName() + ") joined the Server!");
				}
			}
			System.out.println("[INFO]>> inventivetalent(Author of " + this.utils.plugin.getDescription().getName() + ") joined the Server!");
		}
	}
	
}
