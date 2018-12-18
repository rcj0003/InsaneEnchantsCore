package me.rcj0003.insaneenchants.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import me.rcj0003.insaneenchants.utilities.command.CommandUser;
import me.rcj0003.insaneenchants.utilities.command.SubCommand;
import me.rcj0003.insaneenchants.utilities.command.SuperCommand;

public class HelpCommand implements SubCommand {
	private String pluginName;
	private SuperCommand command;

	public HelpCommand(String pluginName, SuperCommand command) {
		this.pluginName = pluginName;
		this.command = command;
	}

	public boolean getRequiresPlayer() {
		return false;
	}

	public int getMinimumArguments() {
		return 0;
	}

	public String getPermission() {
		return pluginName + ".command.help";
	}

	public boolean hasPermission(CommandUser user) {
		return user.getSender().hasPermission(getPermission()) || user.getSender().isOp();
	}

	public String[] getDescription() {
		return new String[] { "&7Gives detailed information about all " + pluginName + " commands." };
	}

	public String getUsage() {
		return pluginName + " help (Name)";
	}

	public String getName() {
		return "help";
	}

	public void execute(CommandUser user, String[] arguments) {
		switch (arguments.length) {
			case 0: {
				user.sendFormattedMessage("&8=[&6FacUtils Command List&8]=",
						"&a" + String.join(", ", command.getSubcommandNames()));
				break;
			}
			default: {
				List<SubCommand> results = new ArrayList<SubCommand>();

				for (String s : command.getSubcommandNames())
					if (s.contains(arguments[0]))
						results.add(command.getExactSubcommandByName(s));

				user.sendFormattedMessage("&8=[&6FacUtils&8]=", "&aSearch: &6" + arguments[0],
						"&6" + results.size() + "&a results found.", "");

				for (SubCommand c : results) {
					user.sendFormattedMessage("&aCommand name: &6" + c.getName(),
							c.getRequiresPlayer() ? "&cCannot be executed from console."
									: "&aCan be executed from console.",
							"&aMinimum arguments: &6" + c.getMinimumArguments());
					if (c.getDescription() != null)
						user.sendFormattedMessage(Arrays.asList(c.getDescription()).stream().map(e -> "&7" + e)
								.collect(Collectors.toList()));
					user.sendFormattedMessage("");
				}

			}
		}
	}
}