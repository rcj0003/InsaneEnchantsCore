package me.rcj0003.insaneenchants.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.rcj0003.insaneenchants.utilities.StringUtils;
import me.rcj0003.insaneenchants.utilities.command.CommandUser;
import me.rcj0003.insaneenchants.utilities.command.SubCommand;

public class FeedCommand implements SubCommand {
	public boolean getRequiresPlayer() {
		return false;
	}

	public int getMinimumArguments() {
		return 0;
	}

	public String getPermission() {
		return "insaneenchants.admin.command.feed";
	}

	public boolean hasPermission(CommandUser user) {
		return user.getSender().hasPermission(getPermission()) || user.getSender().isOp();
	}

	public String getName() {
		return "feed";
	}

	public String[] getDescription() {
		return new String[] { "Restores user to full hunger." };
	}

	public String getUsage() {
		return "insaneenchants feed";
	}

	public void execute(CommandUser user, String[] arguments) {
		Player player = null;
		if (arguments.length == 0) {
			if (!user.isPlayer()) {
				user.sendFormattedMessage("&cYou cannot use this command!");
				return;
			}
			player = user.getPlayer();
		} else
			player = Bukkit.getPlayer(arguments[0]);

		if (player == null) {
			user.sendFormattedMessage("&cThis player cannot be found!");
			return;
		}
		
		player.setFoodLevel(20);
		player.sendMessage(StringUtils.convertColorCodes("&aYou have been fed!"));

		if (!user.isPlayer() || player.getUniqueId() != user.getPlayer().getUniqueId())
			user.sendFormattedMessage("&a" + arguments[0] + " has been fed!");
	}
}