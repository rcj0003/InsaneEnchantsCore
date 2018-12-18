package me.rcj0003.insaneenchants.enchant;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.ProjectileHitEvent;

import me.rcj0003.insaneenchants.itemdata.Enchantable;

public interface InsaneProjectileEnchant extends InsaneEnchant {
	void onProjectileHit(ProjectileHitEvent event, LivingEntity shooter, Enchantable enchantData);
}