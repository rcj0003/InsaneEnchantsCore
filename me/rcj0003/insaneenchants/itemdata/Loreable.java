package me.rcj0003.insaneenchants.itemdata;

import java.util.List;

@Deprecated
public interface Loreable {
	void setLore(List<String> lore);
	List<String> getLore();
}