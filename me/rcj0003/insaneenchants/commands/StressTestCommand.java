package me.rcj0003.insaneenchants.commands;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.rcj0003.insaneenchants.EnchantedItemData;
import me.rcj0003.insaneenchants.InsaneEnchantsPlugin;
import me.rcj0003.insaneenchants.UnregisteredEnchantedItemData;
import me.rcj0003.insaneenchants.api.EnchantServicePlugin;
import me.rcj0003.insaneenchants.enchant.InsaneEnchant;
import me.rcj0003.insaneenchants.utilities.ItemBuilder;
import me.rcj0003.insaneenchants.utilities.command.CommandUser;
import me.rcj0003.insaneenchants.utilities.command.SubCommand;

public class StressTestCommand implements SubCommand {
	private EnchantServicePlugin servicePlugin;
	private int test = 1;

	public StressTestCommand(EnchantServicePlugin servicePlugin) {
		this.servicePlugin = servicePlugin;
	}

	public boolean getRequiresPlayer() {
		return false;
	}

	public int getMinimumArguments() {
		return 0;
	}

	public String getPermission() {
		return "insaneenchants.admin.command.stresstest";
	}

	public boolean hasPermission(CommandUser user) {
		return user.getSender().hasPermission(getPermission()) || user.getPlayer().isOp();
	}

	public String getName() {
		return "stresstest";
	}

	public String[] getDescription() {
		return new String[] { "Stress test the server and data factory.", "The higher the result, the better." };
	}

	public String getUsage() {
		return "insaneenchants stresstest";
	}

	public void execute(CommandUser user, String[] arguments) {
		ItemStack itemStack = new ItemBuilder(Material.BOOK, 1).setDisplayName("&6&nBook O' Enchantment").createItem();

		UnregisteredEnchantedItemData enchantData = EnchantedItemData.from(
				servicePlugin.getEnchantDataFactory().getItemData(itemStack),
				InsaneEnchantsPlugin.getInstance().getEnchantHandler());

		for (InsaneEnchant enchant : servicePlugin.getEnchantHandler().getRegisteredEnchants())
			enchantData.addEnchant(enchant, enchant.getMaxLevel());

		servicePlugin.getEnchantDataFactory().updateItem(itemStack, enchantData);

		long startTime = System.currentTimeMillis();
		long operations = 0;

		user.sendFormattedMessage("&8===[&6Stress Test #" + test + "&8]===", "");

		while (System.currentTimeMillis() - startTime <= 100) {
			enchantData = EnchantedItemData.from(servicePlugin.getEnchantDataFactory().getItemData(itemStack),
					InsaneEnchantsPlugin.getInstance().getEnchantHandler());
			operations++;
		}

		user.sendFormattedMessage("&8===[&6Data Reading Stress Test&8]===",
				"&aOperations (per millisecond):&6 " + (((double) operations) / 100), "");

		startTime = System.currentTimeMillis();
		operations = 0;

		while (System.currentTimeMillis() - startTime <= 100) {
			servicePlugin.getEnchantDataFactory().updateItem(itemStack, enchantData);
			operations++;
		}

		user.sendFormattedMessage("&8===[&6Data Writing Stress Test&8]===",
				"&aOperations (per millisecond):&6 " + (((double) operations) / 100), "");

		startTime = System.currentTimeMillis();
		operations = 0;

		while (System.currentTimeMillis() - startTime <= 1000) {
			enchantData = EnchantedItemData.from(servicePlugin.getEnchantDataFactory().getItemData(itemStack),
					InsaneEnchantsPlugin.getInstance().getEnchantHandler());

			for (InsaneEnchant enchant : servicePlugin.getEnchantHandler().getRegisteredEnchants())
				enchantData.addEnchant(enchant, enchant.getMaxLevel());

			servicePlugin.getEnchantDataFactory().updateItem(itemStack, enchantData);
			operations++;
		}

		user.sendFormattedMessage("&8===[&6Data Mixed Stress Test&8]===",
				"&aOperations (per millisecond):&6 " + (((double) operations) / 1000), "");

		user.sendFormattedMessage("&aTest Complete.", "");

		test++;
	}
}