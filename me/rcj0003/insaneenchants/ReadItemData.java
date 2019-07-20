package me.rcj0003.insaneenchants;

import java.util.Collections;
import java.util.Map;

import me.rcj0003.insaneenchants.itemdata.ItemData;

@Deprecated
public class ReadItemData implements ItemData {
	private Map<String, String> properties;

	public ReadItemData(Map<String, String> properties) {
		this.properties = properties;
	}

	public void setProperty(String property, String value) {
		throw new UnsupportedOperationException();
	}
	
	public void removeProperty(String property) {
		throw new UnsupportedOperationException();
	}

	public Map<String, String> getProperties() {
		return Collections.unmodifiableMap(properties);
	}

	public String getProperty(String property) {
		return properties.getOrDefault(property, null);
	}
}