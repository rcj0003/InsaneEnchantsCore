package me.rcj0003.insaneenchants.enchant;

import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageEvent;

import me.rcj0003.insaneenchants.itemdata.Enchantable;

public interface InsaneDefensiveEnchant extends InsaneEnchant {
	void onDamaged(EntityDamageEvent event, Entity entity, Enchantable enchantData);
}