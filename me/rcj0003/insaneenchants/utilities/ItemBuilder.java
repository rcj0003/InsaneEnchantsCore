package me.rcj0003.insaneenchants.utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilder {

	private ItemStack stack;

	private Material type;
	private int amount;
	private short data;
	private Map<Enchantment, Integer> enchants;
	private String display;
	private List<String> lore;
	private List<ItemFlag> flags;

	public ItemBuilder(ItemStack stack) {
		this(stack.getType(), stack.getAmount(), stack.getDurability(), stack.getEnchantments(),
				stack.getItemMeta().getDisplayName(),
				stack.hasItemMeta() ? stack.getItemMeta().getLore() : new ArrayList<String>(),
				new ArrayList<ItemFlag>(stack.getItemMeta().getItemFlags()));
		this.stack = stack;
	}

	public ItemBuilder(Material type, int amount) {
		this(type, amount, (short) 0, new HashMap<Enchantment, Integer>(), null, new ArrayList<String>(),
				new ArrayList<ItemFlag>());
	}

	public ItemBuilder(Material type, int amount, short data, Map<Enchantment, Integer> enchants, String display,
			List<String> lore, List<ItemFlag> flags) {
		this.type = type;
		this.amount = amount;
		this.data = data;
		this.enchants = enchants;
		this.display = display;
		this.lore = lore;
		this.flags = flags;
	}

	public String getDisplayName() {
		return display;
	}

	public ItemBuilder setDisplayName(String display) {
		this.display = display;
		return this;
	}

	public Material getMaterial() {
		return type;
	}

	public ItemBuilder setMaterial(Material type) {
		this.type = type;
		return this;
	}

	public short getDamage() {
		return data;
	}

	public ItemBuilder setDamage(short data) {
		this.data = data;
		return this;
	}

	public int getAmount() {
		return amount;
	}

	public ItemBuilder setAmount(int amount) {
		this.amount = amount;
		return this;
	}

	public ItemBuilder changeAmount(int change) {
		amount = Math.max(Math.min(64, amount + change), 0);
		return this;
	}

	public Map<Enchantment, Integer> getEnchantments() {
		return enchants;
	}

	public ItemBuilder removeEnchantment(Enchantment enchant) {
		enchants.remove(enchant);
		return this;
	}

	public ItemBuilder addEnchantment(Enchantment enchant, int level) {
		return addEnchantment(enchant, level, false);
	}

	public ItemBuilder addEnchantment(Enchantment enchant, int level, boolean bypass) {
		enchants.put(enchant,
				bypass ? level : Math.max(Math.min(level, enchant.getMaxLevel()), enchant.getStartLevel()));
		return this;
	}

	public List<String> getLore() {
		return lore;
	}

	public ItemBuilder removeLoreLine(int line) {
		lore.remove(line);
		return this;
	}

	public ItemBuilder addLore(String... content) {
		return addLore(true, content);
	}

	public ItemBuilder addLore(boolean condition, String... content) {
		if (condition)
			for (String s : content)
				lore.add(s);
		return this;
	}

	public ItemBuilder setLoreLine(int line, String content) {
		lore.set(line, content);
		return this;
	}

	public ItemBuilder setLore(String... lore) {
		this.lore = new ArrayList<String>();
		this.lore.addAll(new ArrayList<String>(Arrays.asList(lore)));
		return this;
	}

	public ItemBuilder setLore(List<String> lore) {
		this.lore = lore;
		return this;
	}

	public ItemBuilder unsetFlag(ItemFlag flag) {
		flags.remove(flag);
		return this;
	}

	public ItemBuilder setFlag(ItemFlag flag) {
		flags.add(flag);
		return this;
	}

	public List<ItemFlag> getFlags() {
		return flags;
	}

	public ItemStack createItem() {
		if (stack == null)
			stack = new ItemStack(type, amount);
		else {
			stack.setType(type);
			stack.setAmount(amount);
			for (Enchantment e : stack.getEnchantments().keySet())
				stack.removeEnchantment(e);
		}
		stack.setDurability(data);
		stack.addUnsafeEnchantments(enchants);
		ItemMeta meta = stack.getItemMeta();
		if (display != null)
			meta.setDisplayName(StringUtils.convertColorCodes(display));
		if (lore != null && lore.size() > 0)
			meta.setLore(lore.stream().map(e -> StringUtils.convertColorCodes(e)).collect(Collectors.toList()));
		for (ItemFlag f : ItemFlag.values())
			meta.removeItemFlags(f);
		meta.addItemFlags(flags.toArray(new ItemFlag[0]));
		stack.setItemMeta(meta);

		return stack;
	}
}