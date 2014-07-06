package de.inventivegames.utils.command;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CommandHandler implements CommandExecutor {

	private Command_		cmd;
	private String			name;
	private String			perm;
	private CommandType		type;
	private List<Argument>	args;

	public CommandHandler(Command_ cmd) {
		this.cmd = cmd;
		this.name = cmd.getName();
		this.perm = cmd.getPermission();
		this.args = cmd.getArguments();
		this.type = cmd.getType();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (cmd.testPermission(sender)) {
			if (this.type.equals(CommandType.BOTH)) {

			}
			if (this.type.equals(CommandType.PLAYER)) {
				if (sender instanceof Player) {
					Player p = (Player) sender;

				}
			}
			if (this.type.equals(CommandType.CONSOLE)) {
				if (sender instanceof ConsoleCommandSender) {
					ConsoleCommandSender s = (ConsoleCommandSender) sender;

				}
			}
			return false;
		}
		return false;
	}

}
