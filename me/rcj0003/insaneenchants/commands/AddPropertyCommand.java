package me.rcj0003.insaneenchants.commands;

import org.bukkit.inventory.ItemStack;

import me.rcj0003.insaneenchants.api.EnchantServicePlugin;
import me.rcj0003.insaneenchants.itemdata.ItemData;
import me.rcj0003.insaneenchants.itemdata.ItemDataFactory;
import me.rcj0003.insaneenchants.utilities.command.CommandUser;
import me.rcj0003.insaneenchants.utilities.command.SubCommand;

public class AddPropertyCommand implements SubCommand {
	private ItemDataFactory dataFactory;
	
	public AddPropertyCommand(EnchantServicePlugin servicePlugin) {
		dataFactory = servicePlugin.getEnchantDataFactory();
	}
	
	public boolean getRequiresPlayer() {
		return true;
	}

	public int getMinimumArguments() {
		return 2;
	}

	public String getPermission() {
		return "insaneenchants.admin.command.addproperty";
	}

	public boolean hasPermission(CommandUser user) {
		return user.getSender().isOp() || user.getSender().hasPermission(getPermission());
	}

	public String getName() {
		return "addproperty";
	}

	public String[] getDescription() {
		return new String[] { "Add a property to the item!" };
	}

	public String getUsage() {
		return "insaneenchants addproperty [Property] [Value]";
	}

	public void execute(CommandUser user, String[] arguments) {
		ItemStack stack = user.getPlayer().getItemInHand();
		ItemData data = dataFactory.getItemData(stack);
		data.setProperty(arguments[0], arguments[1]);
		dataFactory.updateItem(stack, data);
		user.sendFormattedMessage("&aProperty has been added to item!");
	}
}