package me.rcj0003.insaneenchants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
		Map<String, Integer> enchants = new HashMap<>();
		String id = null;
		List<String> lore = new ArrayList<>();

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
						enchants.put(reader.nextName(), reader.nextInt());

					reader.endObject();
					break;
				case "lore":
					reader.beginArray();

					while (reader.hasNext())
						lore.add(reader.nextString());

					reader.endArray();
					break;
				case "id":
					id = reader.nextString();
					break;
			}
		}

		reader.endObject();

		return new ReadItemData(properties, enchants, id, lore);
	}

	public void write(JsonWriter writer, ItemData data) throws IOException {
		if (data == null) {
			writer.nullValue();
			return;
		}

		writer.beginObject();
		
		for (Entry<String, String> entry : data.getProperties().entrySet())
			writer.name(entry.getKey()).value(entry.getValue());

		if (data instanceof Enchantable) {
			Enchantable enchantData = (Enchantable) data;

			if (enchantData.getEnchants().size() > 0) {
				writer.name("enchants").beginObject();

				for (Entry<InsaneEnchant, Integer> enchant : enchantData.getEnchants().entrySet())
					writer.name(enchant.getKey().getInternalName()).value(enchant.getValue());

				writer.endObject();
			}
		}

		if (data instanceof Loreable) {
			writer.name("lore").beginArray();
			List<String> lore = ((Loreable) data).getLore();

			for (String line : lore)
				writer.value(line);

			writer.endArray();
		}

		if (data instanceof Identifiable)
			writer.name("id").value(((Identifiable) data).getID());

		writer.endObject();
	}
}