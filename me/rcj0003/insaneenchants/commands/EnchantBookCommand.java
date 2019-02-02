package me.rcj0003.insaneenchants.commands;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.rcj0003.insaneenchants.EnchantedItemData;
import me.rcj0003.insaneenchants.UnregisteredEnchantedItemData;
import me.rcj0003.insaneenchants.api.EnchantServicePlugin;
import me.rcj0003.insaneenchants.enchant.EnchantHandler;
import me.rcj0003.insaneenchants.enchant.InsaneEnchant;
import me.rcj0003.insaneenchants.itemdata.ItemDataFactory;
import me.rcj0003.insaneenchants.itemdata.Loreable;
import me.rcj0003.insaneenchants.utilities.ItemBuilder;
import me.rcj0003.insaneenchants.utilities.StringUtils;
import me.rcj0003.insaneenchants.utilities.command.CommandUser;
import me.rcj0003.insaneenchants.utilities.command.SubCommand;

public class EnchantBookCommand implements SubCommand {
	private ItemDataFactory dataFactory;
	private EnchantHandler enchantHandler;

	public EnchantBookCommand(EnchantServicePlugin servicePlugin) {
		dataFactory = servicePlugin.getEnchantDataFactory();
		enchantHandler = servicePlugin.getEnchantHandler();
	}

	public boolean getRequiresPlayer() {
		return true;
	}

	public int getMinimumArguments() {
		return 2;
	}

	public String getPermission() {
		return "insaneenchants.admin.command.book";
	}

	public boolean hasPermission(CommandUser user) {
		return user.getSender().hasPermission(getPermission()) || user.getSender().isOp();
	}

	public String getName() {
		return "book";
	}

	public String[] getDescription() {
		return new String[] { "Returns the specified book with the enchant." };
	}

	public String getUsage() {
		return "insaneenchants book [Enchant] [Level]";
	}

	public void execute(CommandUser user, String[] arguments) {
		Player player = user.getPlayer();

		InsaneEnchant enchant = enchantHandler.getEnchantByDisplayName(arguments[0]);

		int level = StringUtils.tryParse(arguments[1]);

		if (level > 0 && enchant != null) {
			ItemStack itemStack = new ItemBuilder(Material.BOOK, 1).setDisplayName("&6&nBook O' Enchantment")
					.createItem();

			UnregisteredEnchantedItemData data = EnchantedItemData.from(dataFactory.getItemData(player.getItemInHand()), enchantHandler);
			
			if (data instanceof Loreable) {
				Loreable loreable = (Loreable) data;
				loreable.getLore().add("&7Place this book on an item to transfer the enchants!");
			}

			data.addEnchant(enchant, level);

			dataFactory.updateItem(itemStack, data);
			player.getInventory().addItem(itemStack);
			player.updateInventory();
			user.sendFormattedMessage("&aTa-da! Given enchant book!");
		} else {
			user.sendFormattedMessage("&cThe enchant book could not be created.");
		}
	}
}