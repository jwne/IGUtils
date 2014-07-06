package de.inventivegames.utils.command;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class TabCompletion implements TabCompleter {

	private Command_ cmd;
	
	public TabCompletion(Command_ cmd) {
		this.cmd = cmd;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
		// TODO Auto-generated method stub
		return null;
	}

}
