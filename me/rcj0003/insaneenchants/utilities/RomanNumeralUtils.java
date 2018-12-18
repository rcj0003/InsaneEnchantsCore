package me.rcj0003.insaneenchants.utilities;

public class RomanNumeralUtils {
	private final static String[] numerals = new String[] { "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX",
			"X" };
	
	public static String toString(int value) {
		switch (value) {
			default:
				return "" + value;
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
				return numerals[value - 1];
		}
	}

	public static int fromString(String numeral) {
		numeral = numeral.trim().replace(" ", "");
		
		for (int i = 0; i < numerals.length; i++)
			if (numerals[i] == numeral)
				return i + 1;
		
		return 0;
	}
}