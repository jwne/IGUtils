package de.inventivegames.utils.xptimer;

import org.bukkit.entity.Player;

import de.inventivegames.utils.IGUtils;

public class XPTimer {

	private IGUtils	utils;
	private Player	p			= null;
	private double		time		= -1;
	private int		interval	= -1;
	private double		counter		= 0;
	private int	lvlCounter		= 0;
	private float	maxXP		= 1;
	private float	currXP		= 0;
	private float	unit		= 0;
	private int		task		= -1;
	private int task1 = -1;
	private boolean	lvl			= false;

	public XPTimer(IGUtils utils) {
		this.utils = utils;
	}

	private XPTimer(IGUtils utils, Player p, int time, int interval, boolean text) {
		this.utils = utils;
		this.p = p;
		this.time = time;
		this.interval = interval;
		this.lvl = text;
	}
	
	@Override
	public String toString() {
		return "XPTimer{player=" + this.p + ",time=" + this.time + ",interval=" + this.interval + ",text=" + this.lvl + "}";
	}
	
	public XPTimer forPlayer(Player p) {
		this.p = p;
		return this;
	}

	public XPTimer withInterval(int interval) {
		this.interval = interval;
		return this;
	}

	public int getInterval() {
		return this.interval;
	}
	
	public XPTimer withTime(double d) {
		this.time = d;
		this.counter = d;
		this.lvlCounter = (int) d;
		this.unit = (float) (this.maxXP / this.time);
		this.currXP = this.maxXP;
		return this;
	}
	
	public double getTime() {
		return this.time;
	}
	
	public double getCurrentTime() {
		return this.counter;
	}

	public XPTimer withText() {
		this.lvl = true;
		return this;
	}

	public void start() {
		p.setExp(this.maxXP);
		if (lvl) {
			p.setLevel((int) this.counter);
		}
		task = utils.plugin.getServer().getScheduler().scheduleSyncRepeatingTask(utils.plugin, new Runnable() {

			@Override
			public void run() {
				currXP -= unit;
				p.setExp(currXP);
				lvlCounter--;

				if (lvl) {
					p.setLevel(lvlCounter);
				}

				if (counter < ((1.0 / interval))) {
					utils.plugin.getServer().getScheduler().cancelTask(task);
					utils.plugin.getServer().getScheduler().cancelTask(task1);
					p.setExp(0);
					
				}
			}
		}, this.interval, this.interval);
		task1 = utils.plugin.getServer().getScheduler().scheduleSyncRepeatingTask(utils.plugin, new Runnable() {
			
			@Override
			public void run() {
				counter -= (1.0 / interval);
//				System.out.println(counter);				
			}
		}, 1, 1);
	}
	
}
