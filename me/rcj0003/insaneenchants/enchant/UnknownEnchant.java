package me.rcj0003.insaneenchants.enchant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.rcj0003.insaneenchants.utilities.ItemType;

public class UnknownEnchant extends SimpleInsaneEnchant {
	private String displayName;
	private String internalName;

	public UnknownEnchant(String displayName, String internalName) {
		this.displayName = displayName;
		this.internalName = internalName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getInternalName() {
		return internalName;
	}

	public List<String> getDescriptionList() {
		return Arrays.asList("&7No description was found.");
	}

	public int getMaxLevel() {
		return Integer.MAX_VALUE;
	}

	public double[] getModifiers() {
		return new double[0];
	}

	public double getModifierForLevel(int level) {
		return 0;
	}

	public EnchantRarity getRarity() {
		return EnchantRarity.Common;
	}

	public List<ItemType> getEnchantableTypes() {
		return new ArrayList<ItemType>();
	}
}