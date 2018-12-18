package me.rcj0003.insaneenchants.api;

import org.bukkit.plugin.Plugin;

import me.rcj0003.insaneenchants.enchant.InsaneEnchant;

public interface EnchantPlugin extends Plugin {
	boolean canEnable();
	InsaneEnchant[] getEnchants();
}