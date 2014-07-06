package de.inventivegames.utils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class UtilCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (!cmd.getLabel().equalsIgnoreCase("IGU"))
			return false;
		if (args.length == 0)
			return false;
		if (!sender.isOp())
			return false;
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("reportIssue")) {
				if (args.length == 2) {

				}
			}
		}
		return false;
	}

}
