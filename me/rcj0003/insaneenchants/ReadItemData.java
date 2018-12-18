package me.rcj0003.insaneenchants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import me.rcj0003.insaneenchants.enchant.EnchantHandler;
import me.rcj0003.insaneenchants.enchant.InsaneEnchant;
import me.rcj0003.insaneenchants.itemdata.ItemData;

public class ReadItemData implements ItemData {
	private Map<String, String> properties;
	private Map<String, Integer> parsedEnchants;
	private String itemId;
	private List<String> lore;

	public ReadItemData(Map<String, String> properties, Map<String, Integer> parsedEnchants, String itemId, List<String> lore) {
		this.properties = properties;
		this.parsedEnchants = parsedEnchants;
		this.itemId = itemId;
		this.lore = lore;
	}

	public ItemData get(EnchantHandler handler) {
		Map<InsaneEnchant, Integer> enchantMap = new HashMap<>();

		for (Entry<String, Integer> entry : parsedEnchants.entrySet())
			enchantMap.put(handler.getEnchantByInternalName(entry.getKey()), entry.getValue());

		if (itemId == null) {
			return new UnregisteredEnchantedItemData(properties, enchantMap, lore);
		} else
			return new EnchantedItemData(properties, enchantMap, itemId, lore);
	}

	public void setProperty(String property, String value) {
		throw new UnsupportedOperationException();
	}
	
	public void removeProperty(String property) {
		throw new UnsupportedOperationException();
	}

	public Map<String, String> getProperties() {
		throw new UnsupportedOperationException();
	}

	public String getProperty(String property) {
		throw new UnsupportedOperationException();
	}
}