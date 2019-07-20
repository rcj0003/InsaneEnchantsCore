package me.rcj0003.insaneenchants.utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.md_5.bungee.api.ChatColor;

@Deprecated
public class JsonLoreParser {
	private List<String> jsonData = new ArrayList<String>();
	
	public static String translateFromSafeJson(String input) {
		return input.replace("" + (char) 1, "{").replace("" + (char) 2, "}")
				.replace("" + (char) 3, "[").replace("" + (char) 4, "]").replace("" + (char) 5, "\"")
				.replace("" + (char) 6, ":");
	}
	
	public static String translateToSafeJson(String input) {
		return input.replace("{", "" + (char) 1).replace("}", "" + (char) 2).replace("[", "" + (char) 3)
				.replace("]", "" + (char) 4).replace("\"", "" + (char) 5).replace(":", "" + (char) 6);
	}
	
	public boolean hasNext() {
		return jsonData.size() > 0;
	}
	
	public String next() {
		return jsonData.remove(0);
	}
	
	public List<String> getRemaining() {
		return Collections.unmodifiableList(jsonData);
	}
	
	public void parseLore(List<String> lore) {
		for (String line : lore) {
			line = translateFromSafeJson(line);
			if (!line.startsWith(ChatColor.COLOR_CHAR + "{") || !line.endsWith(ChatColor.COLOR_CHAR + "}"))
				continue;
			jsonData.add(line.replace("" + ChatColor.COLOR_CHAR, ""));
		}
	}
}