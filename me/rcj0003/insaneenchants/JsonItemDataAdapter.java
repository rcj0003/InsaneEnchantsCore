package me.rcj0003.insaneenchants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.ChatColor;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import me.rcj0003.insaneenchants.enchant.InsaneEnchant;
import me.rcj0003.insaneenchants.itemdata.Enchantable;
import me.rcj0003.insaneenchants.itemdata.Identifiable;
import me.rcj0003.insaneenchants.itemdata.ItemData;
import me.rcj0003.insaneenchants.itemdata.Loreable;

public class JsonItemDataAdapter extends TypeAdapter<ItemData> {
	public ReadItemData read(JsonReader reader) throws IOException {
		Map<String, String> properties = new HashMap<>();
		reader.beginObject();

		while (reader.hasNext() && reader.peek() != JsonToken.END_DOCUMENT) {
			String name = reader.nextName();
			switch (name) {
				default:
					properties.put(name, reader.nextString());
					break;
				case "enchants":
					reader.beginObject();

					while (reader.hasNext())
						properties.put("enchant." + reader.nextName(), "" + reader.nextInt());

					reader.endObject();
					break;
				case "lore":
					reader.beginArray();
					List<String> lore = new ArrayList<>();

					while (reader.hasNext())
						lore.add(reader.nextString());

					properties.put("lore", String.join(ChatColor.COLOR_CHAR + ",", lore.toArray(new String[0])));
					reader.endArray();
					break;
				case "id":
					properties.put("id", reader.nextString());
					break;
			}
		}

		reader.endObject();
		return new ReadItemData(properties);
	}

	public void write(JsonWriter writer, ItemData data) throws IOException {
		if (data == null) {
			writer.nullValue();
			return;
		}

		writer.beginObject();
		Map<String, String> properties = new HashMap<>(data.getProperties());

		if (data instanceof Enchantable) {
			Enchantable enchantData = (Enchantable) data;

			if (enchantData.getEnchants().size() > 0) {
				writer.name("enchants").beginObject();

				for (Entry<InsaneEnchant, Integer> enchant : enchantData.getEnchants().entrySet())
					writer.name(enchant.getKey().getInternalName()).value(enchant.getValue());
				
				Iterator<Entry<String, String>> propertyIterator = properties.entrySet().iterator();
				while (propertyIterator.hasNext()) {
					Entry<String, String> property = propertyIterator.next();
					if (property.getKey().startsWith("enchant."))
						propertyIterator.remove();
				}

				writer.endObject();
			}
		} else {
			boolean beginWriteEnchants = false;
			Iterator<Entry<String, String>> propertyIterator = properties.entrySet().iterator();
			while (propertyIterator.hasNext()) {
				Entry<String, String> property = propertyIterator.next();
				if (property.getKey().startsWith("enchant.")) {
					if (!beginWriteEnchants) {
						beginWriteEnchants = true;
						writer.name("enchants").beginObject();
					}
					writer.name(property.getKey().replace("enchant.", "")).value(property.getValue());
					propertyIterator.remove();
				}
			}
			if (beginWriteEnchants)
				writer.endObject();
		}

		if (data instanceof Loreable) {
			writer.name("lore").beginArray();
			properties.remove("lore");
			List<String> lore = ((Loreable) data).getLore();

			for (String line : lore)
				writer.value(line);

			writer.endArray();
		} else {
			boolean beginWriteLore = false;
			String loreData = properties.remove("lore");
			if (loreData != null) {
				for (String loreLine : loreData.split(ChatColor.COLOR_CHAR + ","))
					if (loreLine != null && !loreLine.isEmpty()) {
						if (!beginWriteLore) {
							beginWriteLore = true;
							writer.name("lore").beginArray();
						}
						writer.value(loreLine);
					}
			}
			if (beginWriteLore)
				writer.endArray();
		}

		if (data instanceof Identifiable) {
			writer.name("id").value(((Identifiable) data).getID());
			properties.remove("id");
		} else {
			String id = properties.remove("id");
			if (id != null && !id.isEmpty())
				writer.name("id").value(id);
		}

		for (Entry<String, String> entry : data.getProperties().entrySet())
			writer.name(entry.getKey()).value(entry.getValue());

		writer.endObject();
	}
}