package me.rcj0003.insaneenchants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.inventory.ItemStack;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import me.rcj0003.insaneenchants.enchant.EnchantHandler;
import me.rcj0003.insaneenchants.enchant.InsaneEnchant;
import me.rcj0003.insaneenchants.itemdata.ItemDataFactory;
import me.rcj0003.insaneenchants.itemdata.ItemData;
import me.rcj0003.insaneenchants.itemdata.Loreable;
import me.rcj0003.insaneenchants.utilities.ItemBuilder;
import me.rcj0003.insaneenchants.utilities.JsonLoreParser;
import me.rcj0003.insaneenchants.utilities.RomanNumeralUtils;
import me.rcj0003.insaneenchants.utilities.StringUtils;

public class JsonItemDataFactory implements ItemDataFactory {
	private EnchantHandler enchantHandler;
	private Gson builder;

	public JsonItemDataFactory(EnchantHandler enchantHandler) {
		this.enchantHandler = enchantHandler;
		this.builder = new GsonBuilder().registerTypeHierarchyAdapter(ItemData.class, new JsonItemDataAdapter())
				.create();
	}

	public void updateItem(ItemStack stack, ItemData itemData) {
		UnregisteredEnchantedItemData enchantData = EnchantedItemData.from(itemData, enchantHandler);
		String gsonData = builder.toJson(itemData);
		List<String> lore = new ArrayList<>();

		if (enchantData.getEnchants().size() > 0) {
			for (Entry<InsaneEnchant, Integer> entry : enchantData.getEnchants().entrySet())
				lore.add(entry.getKey().getRarity().getColor() + entry.getKey().getDisplayName() + " "
						+ RomanNumeralUtils.toString(entry.getValue()));
			lore.add("");
		}

		lore.add("&6&lLore:");
		List<String> itemLore = ((Loreable) enchantData).getLore();

		if (itemLore.size() == 0)
			lore.add("&7This item has no lore.");
		else
			lore.addAll(itemLore);

		lore.add(StringUtils.createInvisibleString(JsonLoreParser.translateToSafeJson(gsonData)));

		new ItemBuilder(stack).setLore(lore).createItem();
	}

	public ItemData getItemData(ItemStack stack) {
		if (stack != null && stack.hasItemMeta() && stack.getItemMeta().hasLore()
				&& stack.getItemMeta().getLore().size() > 0) {
			JsonLoreParser loreData = new JsonLoreParser();
			loreData.parseLore(stack.getItemMeta().getLore());

			return loreData.hasNext()
					? EnchantedItemData.from(builder.fromJson(loreData.next(), ItemData.class), enchantHandler)
					: new EmptyItemData();
		}
		return new EmptyItemData();
	}
}
