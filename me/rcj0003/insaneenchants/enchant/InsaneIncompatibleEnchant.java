package me.rcj0003.insaneenchants.enchant;

public interface InsaneIncompatibleEnchant extends InsaneEnchant {
	boolean isEnchantCompatible(InsaneEnchant enchant);
}