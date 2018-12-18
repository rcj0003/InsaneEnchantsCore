package me.rcj0003.insaneenchants.enchant;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.rcj0003.insaneenchants.itemdata.Enchantable;

public interface InsaneMiningEnchant extends InsaneEnchant {
	void onBlockBreak(Player player, ItemStack stack, Enchantable enchantData, Block blockBroken);
}