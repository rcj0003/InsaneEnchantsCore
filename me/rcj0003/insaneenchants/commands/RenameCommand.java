package me.rcj0003.insaneenchants.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.rcj0003.insaneenchants.utilities.ItemBuilder;
import me.rcj0003.insaneenchants.utilities.StringUtils;
import me.rcj0003.insaneenchants.utilities.command.CommandUser;
import me.rcj0003.insaneenchants.utilities.command.SubCommand;

public class RenameCommand implements SubCommand {
	public boolean getRequiresPlayer() {
		return true;
	}

	public int getMinimumArguments() {
		return 1;
	}

	public String getPermission() {
		return "insaneenchants.admin.command.rename";
	}

	public boolean hasPermission(CommandUser user) {
		return user.getSender().hasPermission(getPermission()) || user.getSender().isOp();
	}

	public String getName() {
		return "rename";
	}

	public String[] getDescription() {
		return new String[] { "Renames the item with converted color codes." };
	}

	public String getUsage() {
		return "insaneenchants rename <Name>";
	}

	public void execute(CommandUser user, String[] arguments) {
		Player player = user.getPlayer();
		String name = StringUtils.convertColorCodes(String.join(" ", arguments).replace("_", " "));

		if (!ChatColor.stripColor(name).replace(" ", "").isEmpty()) {
			new ItemBuilder(player.getItemInHand()).setDisplayName(name).createItem();
			user.sendFormattedMessage("&aRename successful!");
		} else
			user.sendFormattedMessage("&cRename failed!");
	}
}