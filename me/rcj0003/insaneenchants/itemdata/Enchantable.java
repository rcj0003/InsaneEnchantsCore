package me.rcj0003.insaneenchants.itemdata;

import java.util.Map;

import me.rcj0003.insaneenchants.enchant.InsaneEnchant;

public interface Enchantable {
	Map<InsaneEnchant, Integer> getEnchants();
	
	int getEnchantAmount();
	
	boolean hasEnchants();
	boolean hasEnchant(InsaneEnchant enchant);
	boolean hasEnchant(String internalName);
	
	int getEnchantLevel(InsaneEnchant enchant);
	int getEnchantLevelByInternal(String internalName);
	
	boolean canAddEnchant(InsaneEnchant enchant);
	void addEnchant(InsaneEnchant enchant, int level);
	void removeEnchant(InsaneEnchant enchant);
}