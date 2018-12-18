package me.rcj0003.insaneenchants.enchant;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.rcj0003.insaneenchants.itemdata.Enchantable;

public interface InsaneInteractiveEnchant extends InsaneEnchant {
	void onInteract(PlayerInteractEvent event, Player player, ItemStack stack, Enchantable enchantData);
}