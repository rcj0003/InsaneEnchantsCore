package me.rcj0003.insaneenchants.commands;

import org.bukkit.entity.Player;

import me.rcj0003.insaneenchants.EnchantedItemData;
import me.rcj0003.insaneenchants.api.EnchantServicePlugin;
import me.rcj0003.insaneenchants.enchant.EnchantHandler;
import me.rcj0003.insaneenchants.enchant.InsaneEnchant;
import me.rcj0003.insaneenchants.itemdata.ItemDataFactory;
import me.rcj0003.insaneenchants.utilities.StringUtils;
import me.rcj0003.insaneenchants.utilities.command.CommandUser;
import me.rcj0003.insaneenchants.utilities.command.SubCommand;

public class EnchantAddCommand implements SubCommand {
	private ItemDataFactory dataFactory;
	private EnchantHandler enchantHandler;
	
	public EnchantAddCommand(EnchantServicePlugin servicePlugin) {
		dataFactory = servicePlugin.getEnchantDataFactory();
		enchantHandler = servicePlugin.getEnchantHandler();
	}
	
	@Override
	public boolean getRequiresPlayer() {
		return true;
	}

	@Override
	public int getMinimumArguments() {
		return 2;
	}

	@Override
	public String getPermission() {
		return "insaneenchants.admin.command.addenchant";
	}

	@Override
	public boolean hasPermission(CommandUser user) {
		return user.getSender().isOp() || user.getSender().hasPermission(getPermission());
	}

	@Override
	public String getName() {
		return "addenchant";
	}

	@Override
	public String[] getDescription() {
		return new String[] { "Attempts to add an enchant to the item." };
	}

	@Override
	public String getUsage() {
		return "insaneenchants addenchant [Enchant Name] [Level]";
	}

	@Override
	public void execute(CommandUser user, String[] arguments) {
		Player player = user.getPlayer();
		
		if (player.getItemInHand().getAmount() != 1) {
			user.sendFormattedMessage("&cYou can only enchant one item at once!");
			return;
		}
		
		EnchantedItemData data = EnchantedItemData.from(dataFactory.getItemData(player.getItemInHand()));
		InsaneEnchant enchant = enchantHandler.getEnchantByDisplayName(arguments[0]);
		
		if (!enchant.isEnchantApplicable(player.getItemInHand())) {
			user.sendFormattedMessage("&cYou cannot apply this enchant to this item!");
			return;
		}
		
		int level = StringUtils.tryParse(arguments[1]);
		
		if (level > 0 && enchant != null) {
			data.addEnchant(enchant, level);
			dataFactory.updateItem(player.getItemInHand(), data);
			player.updateInventory();
			user.sendFormattedMessage("&aEnchant added!");
		}
		else {
			user.sendFormattedMessage("&cThe enchant could not be added.");
		}
	}
}
