package me.rcj0003.insaneenchants;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import me.rcj0003.insaneenchants.itemdata.ItemData;

@Deprecated
public class EmptyItemData implements ItemData {
	private Map<String, String> properties = new HashMap<>();
	
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
}