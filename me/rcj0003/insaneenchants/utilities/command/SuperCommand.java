package me.rcj0003.insaneenchants.utilities.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

public abstract class SuperCommand implements CommandExecutor, TabCompleter {
	private Map<String, SubCommand> commands = new HashMap<>();
	private MessageWrapper wrapper;

	public SuperCommand(MessageWrapper wrapper) {
		this.wrapper = wrapper;
	}

	public List<SubCommand> getRegisteredSubcommands() {
		return new ArrayList<SubCommand>(commands.values());
	}

	public List<String> getSubcommandNames() {
		return new ArrayList<String>(commands.keySet());
	}

	public boolean registerSubcommand(SubCommand command) {
		if (commands.containsKey(command.getName()))
			return false;
		commands.put(command.getName(), command);
		return true;
	}

	public void registerSubcommand(SubCommand... command) {
		for (SubCommand cmd : command)
			registerSubcommand(cmd);
	}

	public SubCommand getExactSubcommandByName(String command) {
		return commands.get(command);
	}

	public List<SubCommand> getSubcommandsByName(String search) {
		return new ArrayList<SubCommand>(getRegisteredSubcommands().stream().filter(e -> e.getName().contains(search))
				.collect(Collectors.toList()));
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> commands = new ArrayList<String>();
		StringUtil.copyPartialMatches(args[0], this.commands.keySet(), commands);
		Collections.sort(commands);
		return commands;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		CommandUser user = new CommandUser(sender);

		if (args.length == 0) {
			user.sendFormattedMessage(wrapper.getNoArgumentsMessage());
			return true;
		}

		SubCommand command = commands.get(args[0]);

		if (command == null) {
			user.sendFormattedMessage(wrapper.getCommandNotFoundMessage());
			return true;
		}

		if (!command.hasPermission(user)) {
			user.sendFormattedMessage(wrapper.getNoPermsMessage());
			return true;
		}

		if (command.getRequiresPlayer() && !user.isPlayer()) {
			user.sendFormattedMessage(wrapper.getRequiresPlayerMessage());
			return true;
		}

		ArrayList<String> arguments = new ArrayList<String>(Arrays.asList(args));
		arguments.remove(0);

		if (arguments.size() < command.getMinimumArguments()) {
			user.sendFormattedMessage(wrapper.getUsageMessage().replace("{usage}", command.getUsage()));
			return true;
		}

		command.execute(user, arguments.toArray(new String[0]));

		return true;
	}
}