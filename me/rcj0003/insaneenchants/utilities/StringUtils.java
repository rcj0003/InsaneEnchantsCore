package me.rcj0003.insaneenchants.utilities;

import org.bukkit.ChatColor;

public class StringUtils {
	public static String convertColorCodes(String input) {
		return ChatColor.translateAlternateColorCodes('&', input);
	}

	public static String createInvisibleString(String input) {
		String output = "";

		for (char chr : input.toCharArray())
			output += ChatColor.COLOR_CHAR + ("" + chr);

		return output;
	}

	public static String toDisplayCase(String s) {
		final String ACTIONABLE_DELIMITERS = " '-/";

		StringBuilder sb = new StringBuilder();
		boolean capNext = true;

		for (char c : s.toCharArray()) {
			c = (capNext) ? Character.toUpperCase(c) : Character.toLowerCase(c);
			sb.append(c);
			capNext = (ACTIONABLE_DELIMITERS.indexOf((int) c) >= 0); // explicit cast not needed
		}
		return sb.toString();
	}
	
	public static int tryParse(String value) {
		return tryParse(value, -1);
	}
	
	public static int tryParse(String value, int otherwise) {
		try {
			return Integer.parseInt(value);
		}
		catch (Exception e) {
			return 0;
		}
	}
}