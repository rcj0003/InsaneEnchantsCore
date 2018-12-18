package me.rcj0003.insaneenchants.enchant;

import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import me.rcj0003.insaneenchants.itemdata.Enchantable;

public interface InsaneKillEnchant extends InsaneEnchant {
	void onKillEntity(EntityDamageByEntityEvent event, ItemStack stack, Enchantable enchantData);
}