package me.rcj0003.insaneenchants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.ChatColor;

import me.rcj0003.insaneenchants.enchant.EnchantHandler;
import me.rcj0003.insaneenchants.enchant.InsaneEnchant;
import me.rcj0003.insaneenchants.itemdata.Enchantable;
import me.rcj0003.insaneenchants.itemdata.Identifiable;
import me.rcj0003.insaneenchants.itemdata.ItemData;
import me.rcj0003.insaneenchants.itemdata.Loreable;
import me.rcj0003.insaneenchants.utilities.StringUtils;

@Deprecated
public class EnchantedItemData extends UnregisteredEnchantedItemData implements Identifiable {
	private String itemId;

	public static UnregisteredEnchantedItemData from(ItemData itemData, EnchantHandler enchantHandler) {
		Map<String, String> itemProperties = new HashMap<>(itemData.getProperties());
		Map<InsaneEnchant, Integer> enchantMap;
		String itemId;
		List<String> lore;

		if (itemData instanceof Enchantable)
			enchantMap = new HashMap<>(((Enchantable) itemData).getEnchants());
		else {
			Iterator<Entry<String, String>> propertyIterator = itemProperties.entrySet().iterator();
			enchantMap = new HashMap<>();

			while (propertyIterator.hasNext()) {
				Entry<String, String> property = propertyIterator.next();
				if (property.getKey().startsWith("enchant.")) {
					enchantMap.put(enchantHandler.getEnchantByInternalName(property.getKey().replace("enchant.", "")),
							StringUtils.tryParse(property.getValue()));
					propertyIterator.remove();
				}
			}
		}

		if (itemData instanceof Loreable)
			lore = new ArrayList<>(((Loreable) itemData).getLore());
		else {
			String loreData = itemProperties.remove("lore");
			lore = loreData == null ? new ArrayList<>() : new ArrayList<>(Arrays.asList(loreData.split(ChatColor.COLOR_CHAR + ",")));
		}

		itemId = itemData instanceof Identifiable ? ((Identifiable) itemData).getID() : itemProperties.remove("id");

		return itemId != null ? new EnchantedItemData(itemProperties, enchantMap, itemId, lore)
				: new UnregisteredEnchantedItemData(itemProperties, enchantMap, lore);
	}

	public EnchantedItemData(Map<String, String> properties, Map<InsaneEnchant, Integer> enchantMap, String itemId,
			List<String> lore) {
		super(properties, enchantMap, lore);
		this.itemId = itemId;
	}

	public String getID() {
		return itemId;
	}
}