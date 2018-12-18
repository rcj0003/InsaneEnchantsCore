package me.rcj0003.insaneenchants.api;

import org.bukkit.plugin.Plugin;

import me.rcj0003.insaneenchants.enchant.EnchantHandler;
import me.rcj0003.insaneenchants.itemdata.ItemDataFactory;

public interface EnchantServicePlugin extends Plugin {
	short getPluginPriority();
	
	EnchantHandler getEnchantHandler();
	ItemDataFactory getEnchantDataFactory();
}