package me.rcj0003.insaneenchants.enchant;

import java.util.List;

import org.bukkit.inventory.ItemStack;

import me.rcj0003.insaneenchants.utilities.ItemType;

public interface InsaneEnchant {
	String getDisplayName();
	String getInternalName();
	String[] getDescription();

	int getMaxLevel();
	
	double[] getModifiers();
	double getModifierForLevel(int level);
	
	EnchantRarity getRarity();
	
	List<ItemType> getEnchantableTypes();
	boolean isEnchantApplicable(ItemStack stack);
}