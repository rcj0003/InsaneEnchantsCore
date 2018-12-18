package me.rcj0003.insaneenchants.enchant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.rcj0003.insaneenchants.utilities.StringUtils;
import net.md_5.bungee.api.ChatColor;

public class EnchantHandler {
	private Map<String, InsaneEnchant> enchantMap = new HashMap<>();
	
	public List<InsaneEnchant> getRegisteredEnchants() {
		return new ArrayList<>(enchantMap.values());
	}
	
	public void registerEnchant(InsaneEnchant... enchants) {
		for (InsaneEnchant enchant : enchants)
			enchantMap.putIfAbsent(enchant.getInternalName(), enchant);
	}

	public InsaneEnchant getEnchantByDisplayName(String displayName) {
		for (InsaneEnchant enchant : enchantMap.values()) {
			String display = ChatColor.stripColor(enchant.getDisplayName()).replace("\0", "").toLowerCase();
			displayName = displayName.toLowerCase().replace("_", " ");
			if (displayName.equals(display))
				return enchant;
		}
		return null;
	}

	public InsaneEnchant getEnchantByInternalName(String internalName) {
		String[] internalSplit = internalName.split("\\.");
		return getEnchantByInternalName(internalName,
				StringUtils.toDisplayCase(internalSplit[internalSplit.length - 1].replace("_", " ")));
	}

	public InsaneEnchant getEnchantByInternalName(String internalName, String displayName) {
		if (enchantMap.containsKey(internalName))
			return enchantMap.get(internalName);
		else {
			InsaneEnchant enchant = new UnknownEnchant(displayName, internalName);
			registerEnchant(enchant);
			return enchant;
		}
	}
}