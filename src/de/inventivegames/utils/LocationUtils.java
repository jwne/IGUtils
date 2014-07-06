package de.inventivegames.utils;

import org.bukkit.Location;

public class LocationUtils {

	private IGUtils	utils;

	public LocationUtils(IGUtils utils) {
		this.utils = utils;
	}

	public boolean moved(Location loc1, Location loc2) {
		if (loc1 != loc2)
			return true;
		return false;
	}

	public boolean movedBlock(Location loc1, Location loc2) {
		Location l1 = new Location(loc1.getWorld(), loc1.getBlockX(), loc1.getBlockY(), loc1.getBlockZ());
		Location l2 = new Location(loc2.getWorld(), loc2.getBlockX(), loc2.getBlockY(), loc2.getBlockZ());
		if (l1 != l2)
			return true;
		return false;
	}

	public boolean movedDistance(Location loc1, Location loc2, double distance) {
		double d = loc1.distance(loc2);
		if (d >= distance)
			return true;
		return false;
	}

}
