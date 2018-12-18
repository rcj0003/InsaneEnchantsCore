package me.rcj0003.insaneenchants.enchant;

import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import me.rcj0003.insaneenchants.itemdata.Enchantable;

public interface InsaneOffensiveEnchant extends InsaneEnchant {
	void onAttackEntity(EntityDamageByEntityEvent event, ItemStack stack, Enchantable enchantData);
}