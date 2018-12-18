package me.rcj0003.insaneenchants.enchant;

import org.bukkit.ChatColor;

public enum EnchantRarity {
	Common('f'), Uncommon('a'), Rare('b'), Superior('e'), Elusive('c');
	
	private char color;
	
	EnchantRarity() {
		this('6');
	}
	
	EnchantRarity(char color) {
		this.color = color;
	}
	
	public String getDisplayName() {
		return getColor() + name();
	}
	
	public String getColor() {
		return "" + ChatColor.COLOR_CHAR + color;
	}
}