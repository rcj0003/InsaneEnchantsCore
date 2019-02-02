package me.rcj0003.insaneenchants.enchant;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.rcj0003.insaneenchants.itemdata.Enchantable;
import me.rcj0003.insaneenchants.listeners.ComboData;

public interface InsaneComboEnchant {
	void onCombo(Player attacker, ComboData comboData, EntityDamageByEntityEvent event, Enchantable enchantData);
}