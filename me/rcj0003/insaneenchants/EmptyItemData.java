package me.rcj0003.insaneenchants;

import java.util.Collections;
import java.util.Map;

import me.rcj0003.insaneenchants.itemdata.ItemData;

public class EmptyItemData implements ItemData {
	public void setProperty(String property, String value) {
	}
	
	public void removeProperty(String property) {
	}

	public Map<String, String> getProperties() {
		return Collections.emptyMap();
	}

	public String getProperty(String property) {
		return "";
	}
}