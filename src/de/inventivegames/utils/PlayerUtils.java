package de.inventivegames.utils;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.comphenix.packetwrapper.WrapperPlayClientPosition;
import com.comphenix.packetwrapper.WrapperPlayServerPosition;

import de.inventivegames.utils.lang.Language;

public class PlayerUtils {

	private IGUtils	utils;

	public PlayerUtils(IGUtils utils) {
		this.utils = utils;
	}

	public String getLocale(Player p) {
		Object locale = null;
		try {
			Object handle = Reflection.getHandle(p);
			locale = Reflection.getField(handle.getClass(), "locale").get(handle);
		} catch (Exception exc) {
			exc.printStackTrace();
		}

		return (String) locale + " " + Language.getByCode((String) locale);
	}

	public Player getClosestPlayer(Player p) {
		boolean found = false;
		for (int i = 0; i < 200; i++) {
			List<Entity> entities = p.getNearbyEntities(i, 64, i);
			for (Entity e : entities) {
				if (e.getType().equals(EntityType.PLAYER)) {
					return (Player) e;
				}
			}
			if (found)
				break;
		}
		return p;
	}

	public Entity getClosestEntity(Player p) {
		boolean found = false;
		for (int i = 0; i < 200; i++) {
			List<Entity> entities = p.getNearbyEntities(i, 64, i);
			for (Entity e : entities) {

				return e;

			}
			if (found)
				break;
		}
		return p;
	}

	public boolean hasLineOfSight(Player p, Player p1) {
		return p.hasLineOfSight(p1);
	}

	public void clientsideTeleport(Player p, Location loc) {
		if (p.getLocation().distance(loc) > 32)
			return;
		WrapperPlayClientPosition pos = new WrapperPlayClientPosition();
		pos.setX(loc.getX());
		pos.setY(loc.getY());
		pos.setZ(loc.getZ());
		pos.sendPacket(p);
	}

	public void serversideTeleport(Player p, Location loc) {
		if (p.getLocation().distance(loc) > 32)
			return;
		WrapperPlayServerPosition pos = new WrapperPlayServerPosition();
		pos.setX(loc.getX());
		pos.setY(loc.getY());
		pos.setZ(loc.getZ());
		pos.sendPacket(p);
	}

}
