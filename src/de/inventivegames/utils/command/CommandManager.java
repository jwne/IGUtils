package de.inventivegames.utils.command;

import java.util.ArrayList;

import org.bukkit.plugin.Plugin;

public class CommandManager {

	private Plugin				plugin;

	private ArrayList<Command_>	commands	= new ArrayList<Command_>();

	public CommandManager(Plugin plugin) {
		this.plugin = plugin;
	}

	public Command_ registerNewCommand(String name, CommandType type) {
		Command_ cmd = new Command_(this.plugin, name, type);
		commands.add(cmd);
		return cmd;
	}

	public void unregisterCommand(String name) {
		commands.remove(name);
	}

}
