package me.rcj0003.insaneenchants.commands;

import me.rcj0003.insaneenchants.utilities.command.MessageWrapper;
import me.rcj0003.insaneenchants.utilities.command.SuperCommand;

public class InsaneEnchantsCommand extends SuperCommand {
	public InsaneEnchantsCommand() {
		super(new MessageWrapper(
				new String[] { "&8===[&6Insane Enchants by short1der&8]===",
						"&aDo &6/insaneenchants help&a for help!" },
				"&8[&6InsaneEnchants&8]&c Sub-command not found!",
				"&8[&6InsaneEnchants&8]&c You do not have perms to execute this sub-command!",
				"&8[&6InsaneEnchants&8]&c This command requires a player to execute!",
				"&8[&6InsaneEnchants&8]&c Usage: {usage}"));
	}
}