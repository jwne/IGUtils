package de.inventivegames.utils.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Command_ {

	private Plugin				plugin;
	private String				name;
	private String				perm;
	private ArrayList<Argument>	args	= new ArrayList<Argument>();
	private CommandHandler		handler;
	private PluginCommand		command;
	private CommandType			type	= CommandType.BOTH;

	public Command_(Plugin plugin, String name, CommandType type) {
		this.name = name;
		this.plugin = plugin;
		this.handler = new CommandHandler(this);
		this.type = type;

		this.command = ((JavaPlugin) this.plugin).getCommand(this.name);
		this.command.setExecutor(this.handler);
	}

	public void setPermission(String perm) {
		this.perm = perm;
	}

	public String getPermission() {
		return this.perm;
	}

	public String getName() {
		return this.name;
	}

	public List<Argument> getArguments() {
		return this.args;
	}

	public Argument addArgument(String name) {
		Argument arg = new Argument(name);
		args.add(arg);
		return arg;
	}

	public void removeArgument(String name) {
		args.remove(name);
	}

	public List<Argument> getArgumentsByPos(int pos) {
		List<Argument> args = new ArrayList<Argument>();
		for (Argument arg : this.args) {
			if (arg.getPos() == pos) {
				args.add(arg);
			}
		}
		return args;
	}

	public CommandType getType() {
		return this.type;
	}

}
