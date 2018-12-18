package me.rcj0003.insaneenchants.enchant;

import java.util.List;

import org.bukkit.inventory.ItemStack;

import me.rcj0003.insaneenchants.utilities.ItemType;

public abstract class SimpleInsaneEnchant implements InsaneEnchant {
	public abstract List<String> getDescriptionList();

	public String[] getDescription() {
		return getDescriptionList().toArray(new String[0]);
	}

	public double getModifierForLevel(int level) {
		return getModifiers()[Math.min(Math.max(level, 0), getMaxLevel())];
	}
	public boolean isEnchantApplicable(ItemStack stack) {
		return getEnchantableTypes().contains(ItemType.getType(stack.getType()));
	}
}