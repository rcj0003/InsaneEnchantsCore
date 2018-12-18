package me.rcj0003.insaneenchants.commands;

import org.bukkit.inventory.ItemStack;

import me.rcj0003.insaneenchants.api.EnchantServicePlugin;
import me.rcj0003.insaneenchants.itemdata.ItemData;
import me.rcj0003.insaneenchants.itemdata.ItemDataFactory;
import me.rcj0003.insaneenchants.utilities.command.CommandUser;
import me.rcj0003.insaneenchants.utilities.command.SubCommand;

public class RemovePropertyCommand implements SubCommand {
	private ItemDataFactory dataFactory;
	
	public RemovePropertyCommand(EnchantServicePlugin servicePlugin) {
		dataFactory = servicePlugin.getEnchantDataFactory();
	}
	
	public boolean getRequiresPlayer() {
		return true;
	}

	public int getMinimumArguments() {
		return 1;
	}

	public String getPermission() {
		return "insaneenchants.admin.command.removeproperty";
	}

	public boolean hasPermission(CommandUser user) {
		return user.getSender().isOp() || user.getSender().hasPermission(getPermission());
	}

	public String getName() {
		return "removeproperty";
	}

	public String[] getDescription() {
		return new String[] { "Remove a property to the item!" };
	}

	public String getUsage() {
		return "insaneenchants removeproperty [Property]";
	}

	public void execute(CommandUser user, String[] arguments) {
		ItemStack stack = user.getPlayer().getItemInHand();
		ItemData data = dataFactory.getItemData(stack);
		data.removeProperty(arguments[0]);
		dataFactory.updateItem(stack, data);
		user.sendFormattedMessage("&aProperty has been removed from item!");
	}
}