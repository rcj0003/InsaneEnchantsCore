package me.rcj0003.insaneenchants.itemdata;

import java.util.Map;

public interface ItemData {
	void setProperty(String property, String value);
	void removeProperty(String property);
	
	Map<String, String> getProperties();
	String getProperty(String property);
}