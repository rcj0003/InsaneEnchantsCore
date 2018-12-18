package me.rcj0003.insaneenchants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import me.rcj0003.insaneenchants.enchant.InsaneEnchant;
import me.rcj0003.insaneenchants.enchant.InsaneIncompatibleEnchant;
import me.rcj0003.insaneenchants.itemdata.Enchantable;
import me.rcj0003.insaneenchants.itemdata.Identifiable;
import me.rcj0003.insaneenchants.itemdata.ItemData;
import me.rcj0003.insaneenchants.itemdata.Loreable;

public class EnchantedItemData implements ItemData, Identifiable, Enchantable, Loreable {
	private Map<String, String> properties;
	private Map<InsaneEnchant, Integer> enchantMap;
	private String itemId;
	private List<String> lore;

	public static EnchantedItemData from(ItemData itemData) {
		Map<InsaneEnchant, Integer> enchantMap = itemData instanceof Enchantable
				? new HashMap<>(((Enchantable) itemData).getEnchants())
				: new HashMap<InsaneEnchant, Integer>();

		String itemId = itemData instanceof Identifiable ? ((Identifiable) itemData).getID()
				: UUID.randomUUID().toString();

		List<String> lore = itemData instanceof Loreable ? new ArrayList<>(((Loreable) itemData).getLore())
				: new ArrayList<>();

		return new EnchantedItemData(itemData.getProperties(), enchantMap, itemId, lore);
	}

	public EnchantedItemData(Map<String, String> properties, Map<InsaneEnchant, Integer> enchantMap, String itemId,
			List<String> lore) {
		this.properties = properties;
		this.enchantMap = enchantMap;
		this.itemId = itemId;
		this.lore = lore;
	}
	
	public void setProperty(String property, String value) {
		properties.put(property, value);
	}
	
	public void removeProperty(String property) {
		properties.remove(property);
	}
	
	public Map<String, String> getProperties() {
		return Collections.unmodifiableMap(properties);
	}
	
	public String getProperty(String property) {
		return properties.get(property);
	}

	public String getID() {
		return itemId;
	}

	public Map<InsaneEnchant, Integer> getEnchants() {
		return Collections.unmodifiableMap(enchantMap);
	}

	public void addEnchant(InsaneEnchant enchant, int level) {
		if (canAddEnchant(enchant))
			enchantMap.put(enchant, Math.min(Math.max(1, level), enchant.getMaxLevel()));
	}

	public void removeEnchant(InsaneEnchant enchant) {
		enchantMap.remove(enchant);
	}

	public boolean canAddEnchant(InsaneEnchant enchant) {
		if (enchantMap.size() >= 10)
			return false;

		for (InsaneEnchant enc : enchantMap.keySet()) {
			if (!(enc instanceof InsaneIncompatibleEnchant))
				continue;
			if (!((InsaneIncompatibleEnchant) enc).isEnchantCompatible(enchant))
				return false;
		}

		return true;
	}

	public boolean hasEnchants() {
		return enchantMap.size() > 0;
	}

	public boolean hasEnchant(InsaneEnchant enchant) {
		return enchantMap.containsKey(enchant);
	}

	public boolean hasEnchant(String internalName) {
		for (InsaneEnchant enchant : enchantMap.keySet())
			if (enchant.getInternalName().equals(internalName))
				return true;
		return false;
	}

	public int getEnchantLevel(InsaneEnchant enchant) {
		return enchantMap.containsKey(enchant) ? enchantMap.get(enchant) : 0;
	}

	public int getEnchantLevelByInternal(String internalName) {
		for (InsaneEnchant enchant : enchantMap.keySet())
			if (enchant.getInternalName().equals(internalName))
				return enchantMap.get(enchant);
		return 0;
	}

	public int getEnchantAmount() {
		return enchantMap.size();
	}

	public void setLore(List<String> lore) {
		this.lore = lore;
	}

	public List<String> getLore() {
		return lore;
	}
}