package me.rcj0003.insaneenchants.itemdata;

import org.bukkit.inventory.ItemStack;

public interface ItemDataFactory {
	void updateItem(ItemStack stack, ItemData enchantData);
	ItemData getItemData(ItemStack stack);
}