package me.rcj0003.insaneenchants;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import me.rcj0003.insaneenchants.enchant.InsaneEnchant;

public class UnmodifiableEnchantItemData extends UnregisteredEnchantedItemData {
	public UnmodifiableEnchantItemData(Map<String, String> properties, Map<InsaneEnchant, Integer> enchantMap, String itemId, List<String> lore) {
		super(properties, enchantMap, lore);
	}
	
	public void setProperty(String property, String value) {
	}

	public boolean canAddEnchant(InsaneEnchant enchant) {
		return false;
	}

	public void addEnchant(InsaneEnchant enchant, int level) {
	}

	public void removeEnchant(InsaneEnchant enchant) {
	}

	public void setLore(List<String> lore) {
	}

	public List<String> getLore() {
		return Collections.unmodifiableList(super.getLore());
	}
}