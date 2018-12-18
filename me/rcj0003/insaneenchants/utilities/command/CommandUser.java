package me.rcj0003.insaneenchants.utilities.command;

import java.util.Arrays;
import java.util.Collection;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.rcj0003.insaneenchants.utilities.StringUtils;

public class CommandUser {
	private CommandSender sender;

	public CommandUser(final CommandSender sender) {
		this.sender = sender;
	}

	public CommandSender getSender() {
		return sender;
	}

	public void sendFormattedMessage(final Collection<String> message) {
		message.stream().map(e -> getFormattedMessage(StringUtils.convertColorCodes(e)))
				.forEach(e -> sender.sendMessage(e));
	}

	public void sendFormattedMessage(final String... message) {
		sendFormattedMessage(Arrays.asList(message));
	}

	public String getFormattedMessage(final String message) {
		return message.replace("{player}", sender.getName());
	}

	public String getName() {
		return sender.getName();
	}

	public Player getPlayer() {
		return (Player) sender;
	}

	public boolean isPlayer() {
		return sender instanceof Player;
	}
}