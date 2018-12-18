package me.rcj0003.insaneenchants.commands;

import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.entity.Player;

import me.rcj0003.insaneenchants.api.EnchantServicePlugin;
import me.rcj0003.insaneenchants.enchant.InsaneEnchant;
import me.rcj0003.insaneenchants.itemdata.Enchantable;
import me.rcj0003.insaneenchants.itemdata.Identifiable;
import me.rcj0003.insaneenchants.itemdata.ItemData;
import me.rcj0003.insaneenchants.itemdata.ItemDataFactory;
import me.rcj0003.insaneenchants.utilities.command.CommandUser;
import me.rcj0003.insaneenchants.utilities.command.SubCommand;

public class EnchantDebugCommand implements SubCommand {
	private ItemDataFactory dataFactory;

	public EnchantDebugCommand(EnchantServicePlugin servicePlugin) {
		dataFactory = servicePlugin.getEnchantDataFactory();
	}

	@Override
	public boolean getRequiresPlayer() {
		return true;
	}

	@Override
	public int getMinimumArguments() {
		return 0;
	}

	@Override
	public String getPermission() {
		return "insaneenchants.admin.command.debug";
	}

	@Override
	public boolean hasPermission(CommandUser user) {
		return user.getSender().isOp() || user.getSender().hasPermission(getPermission());
	}

	@Override
	public String getName() {
		return "enchantdebug";
	}

	@Override
	public String[] getDescription() {
		return new String[] { "Lists enchant data and debug information for enchant fetching." };
	}

	@Override
	public String getUsage() {
		return "N/A";
	}

	@Override
	public void execute(CommandUser user, String[] arguments) {
		Player player = user.getPlayer();
		ItemData data = dataFactory.getItemData(player.getItemInHand());
		user.sendFormattedMessage("&8===[&6Item Data&8]===", "", "&aProperties: ");
		for (Entry<String, String> entry : data.getProperties().entrySet())
			user.sendFormattedMessage("&a" + entry.getKey() + ": " + entry.getValue());

		if (data instanceof Identifiable)
			user.sendFormattedMessage("&aUnique Item ID:&6 " + ((Identifiable) data).getID());

		if (data instanceof Enchantable) {
			user.sendFormattedMessage("&aEnchants: ");
			Map<InsaneEnchant, Integer> enchants = ((Enchantable) data).getEnchants();
			for (InsaneEnchant enchant : enchants.keySet())
				user.sendFormattedMessage(
						"&aDisplay Name:&6 " + enchant.getRarity().getColor() + enchant.getDisplayName(),
						"&aInternal Name:&6 " + enchant.getInternalName(), "&aRarity:&6 " + enchant.getRarity().name(),
						"&aLevel:&6 " + enchants.get(enchant), "");
		}
	}
}