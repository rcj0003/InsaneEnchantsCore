package me.rcj0003.insaneenchants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.rcj0003.insaneenchants.api.EnchantPlugin;
import me.rcj0003.insaneenchants.api.EnchantServicePlugin;
import me.rcj0003.insaneenchants.commands.AddPropertyCommand;
import me.rcj0003.insaneenchants.commands.EnchantAddCommand;
import me.rcj0003.insaneenchants.commands.EnchantBookCommand;
import me.rcj0003.insaneenchants.commands.EnchantDebugCommand;
import me.rcj0003.insaneenchants.commands.FeedCommand;
import me.rcj0003.insaneenchants.commands.HealCommand;
import me.rcj0003.insaneenchants.commands.HelpCommand;
import me.rcj0003.insaneenchants.commands.InsaneEnchantsCommand;
import me.rcj0003.insaneenchants.commands.RemovePropertyCommand;
import me.rcj0003.insaneenchants.commands.RenameCommand;
import me.rcj0003.insaneenchants.commands.StressTestCommand;
import me.rcj0003.insaneenchants.enchant.EnchantHandler;
import me.rcj0003.insaneenchants.enchant.InsaneEnchant;
import me.rcj0003.insaneenchants.itemdata.ItemDataFactory;
import me.rcj0003.insaneenchants.listeners.InsaneComboEnchantListener;
import me.rcj0003.insaneenchants.listeners.InsaneEnchantsEventListener;

public class InsaneEnchantsPlugin extends JavaPlugin implements EnchantServicePlugin {
	private static InsaneEnchantsPlugin instance;
	private EnchantServicePlugin servicePlugin;
	private InsaneEnchantsCommand commandProcessor;
	private EnchantHandler enchantHandler;
	private ItemDataFactory enchantDataFactory;

	public static InsaneEnchantsPlugin getInstance() {
		return instance;
	}

	public EnchantServicePlugin getEnchantService() {
		return servicePlugin;
	}

	@Override
	public void onEnable() {
		try {
			System.out.println("[InsaneEnchantsCore] Pre-initializing...");

			instance = this;
			servicePlugin = this;

			commandProcessor = new InsaneEnchantsCommand();
			enchantHandler = new EnchantHandler();

			getCommand("insaneenchants").setExecutor(commandProcessor);

			new BukkitRunnable() {
				public void run() {
					System.out.println("[InsaneEnchantsCore] Initializing!");
					List<InsaneEnchant> enchants = new ArrayList<>();

					try {
						for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
							if (plugin instanceof EnchantServicePlugin) {
								EnchantServicePlugin foundServicePlugin = (EnchantServicePlugin) plugin;

								if (foundServicePlugin.getPluginPriority() > servicePlugin.getPluginPriority())
									servicePlugin = foundServicePlugin;
							}
							if (plugin instanceof EnchantPlugin) {
								EnchantPlugin foundEnchantPlugin = (EnchantPlugin) plugin;
								if (!foundEnchantPlugin.canEnable())
									continue;
								System.out.println(
										"[InsaneEnchantsCore] Enchant Plugin Found: " + foundEnchantPlugin.getName());
								enchants.addAll(Arrays.asList(foundEnchantPlugin.getEnchants()));
							}
						}
					} catch (Exception ex) {
						ex.printStackTrace();
						System.out.println(
								"[InsaneEnchantsCore] Pre-initialization has failed! Disabling InsaneEnchantsCore...");
						Bukkit.getPluginManager().disablePlugin(InsaneEnchantsPlugin.getInstance());
						return;
					}

					servicePlugin.getEnchantHandler().registerEnchant(enchants.toArray(new InsaneEnchant[0]));
					enchantDataFactory = new JsonItemDataFactory(servicePlugin.getEnchantHandler());

					commandProcessor.registerSubcommand(new HelpCommand("InsaneEnchants", commandProcessor),
							new EnchantAddCommand(servicePlugin), new EnchantDebugCommand(servicePlugin),
							new EnchantBookCommand(servicePlugin), new StressTestCommand(servicePlugin),
							new HealCommand(), new FeedCommand(), new RenameCommand(),
							new AddPropertyCommand(servicePlugin), new RemovePropertyCommand(servicePlugin));

					Bukkit.getPluginManager().registerEvents(new InsaneEnchantsEventListener(servicePlugin),
							InsaneEnchantsPlugin.getInstance());
					
					Bukkit.getPluginManager().registerEvents(new InsaneComboEnchantListener(servicePlugin),
							InsaneEnchantsPlugin.getInstance());

					System.out.println("[InsaneEnchantsCore] Initialization sucessful! Enchant Service Plugin: "
							+ servicePlugin.getName());
				}
			}.runTask(this);

			System.out.println("[InsaneEnchantsCore] Pre-initialization sucessful!");
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("[InsaneEnchantsCore] Pre-initialization has failed! Disabling InsaneEnchants...");
			Bukkit.getPluginManager().disablePlugin(this);
		}
	}

	public void onDisable() {
		System.out.println("[InsaneEnchantsCore] Disabling plugin!");
		Bukkit.getScheduler().cancelTasks(this);
	}

	public InsaneEnchantsCommand getCommandProcessor() {
		return commandProcessor;
	}

	public short getPluginPriority() {
		return 0;
	}

	public EnchantHandler getEnchantHandler() {
		return servicePlugin == this ? enchantHandler : servicePlugin.getEnchantHandler();
	}

	public ItemDataFactory getEnchantDataFactory() {
		return servicePlugin == this ? enchantDataFactory : servicePlugin.getEnchantDataFactory();
	}
}