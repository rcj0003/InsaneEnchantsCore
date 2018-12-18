package me.rcj0003.insaneenchants.utilities;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum ItemType {
	Block, Consumable, Item, Tool, Hoe, Shovel, Bow, Axe, Pickaxe, Sword, Helmet, Chestplate, Leggings, Boots;

	public static ItemType getTypeBySlot(int slot) {
		switch (slot) {
			default:
				return Block;
			case 36:
				return Helmet;
			case 37:
				return Chestplate;
			case 38:
				return Leggings;
			case 39:
				return Boots;
		}
	}
	
	public static ItemType getType(ItemStack stack) {
		return getType(stack.getType());
	}

	public static ItemType getType(Material material) {
		if (material.isEdible())
			return Consumable;
		
		if (isBow(material))
			return Bow;

		if (isHelmet(material))
			return Helmet;

		if (isChestplate(material))
			return Chestplate;

		if (isLeggings(material))
			return Leggings;

		if (isBoots(material))
			return Boots;

		if (isSword(material))
			return Sword;

		if (isPickaxe(material))
			return Pickaxe;

		if (isAxe(material))
			return Axe;

		if (isShovel(material))
			return Shovel;

		if (isHoe(material))
			return Hoe;

		if (isTool(material))
			return Tool;

		if (!material.isBlock())
			return Item;

		return Block;
	}

	public static boolean isMaterial(Material material) {
		return !material.isEdible() && !material.isBlock() && !isPickaxe(material) && !isSword(material)
				&& !isArmor(material);
	}

	public static boolean isTool(Material material) {
		switch (material) {
			default:
				return false;
			case DIAMOND_PICKAXE:
			case GOLD_PICKAXE:
			case IRON_PICKAXE:
			case STONE_PICKAXE:
			case WOOD_PICKAXE:
			case DIAMOND_SPADE:
			case GOLD_SPADE:
			case IRON_SPADE:
			case STONE_SPADE:
			case WOOD_SPADE:
			case DIAMOND_AXE:
			case GOLD_AXE:
			case IRON_AXE:
			case STONE_AXE:
			case WOOD_AXE:
			case DIAMOND_HOE:
			case GOLD_HOE:
			case IRON_HOE:
			case STONE_HOE:
			case WOOD_HOE:
			case FLINT_AND_STEEL:
			case MAP:
			case BOW:
				return true;
		}
	}

	public static boolean isShovel(Material material) {
		switch (material) {
			default:
				return false;
			case DIAMOND_SPADE:
			case GOLD_SPADE:
			case IRON_SPADE:
			case STONE_SPADE:
			case WOOD_SPADE:
				return true;
		}
	}

	public static boolean isHoe(Material material) {
		switch (material) {
			default:
				return false;
			case DIAMOND_HOE:
			case GOLD_HOE:
			case IRON_HOE:
			case STONE_HOE:
			case WOOD_HOE:
				return true;
		}
	}

	public static boolean isAxe(Material material) {
		switch (material) {
			default:
				return false;
			case DIAMOND_AXE:
			case GOLD_AXE:
			case IRON_AXE:
			case STONE_AXE:
			case WOOD_AXE:
				return true;
		}
	}

	public static boolean isPickaxe(Material material) {
		switch (material) {
			default:
				return false;
			case DIAMOND_PICKAXE:
			case GOLD_PICKAXE:
			case IRON_PICKAXE:
			case STONE_PICKAXE:
			case WOOD_PICKAXE:
				return true;
		}
	}

	public static boolean isSword(Material material) {
		switch (material) {
			default:
				return false;
			case DIAMOND_SWORD:
			case GOLD_SWORD:
			case IRON_SWORD:
			case STONE_SWORD:
			case WOOD_SWORD:
				return true;
		}
	}

	public static boolean isArmor(Material material) {
		return isHelmet(material) || isChestplate(material) || isLeggings(material) || isBoots(material);
	}

	public static boolean isHelmet(Material material) {
		switch (material) {
			default:
				return false;
			case DIAMOND_HELMET:
			case GOLD_HELMET:
			case IRON_HELMET:
			case CHAINMAIL_HELMET:
			case LEATHER_HELMET:
				return true;
		}
	}

	public static boolean isChestplate(Material material) {
		switch (material) {
			default:
				return false;
			case DIAMOND_CHESTPLATE:
			case GOLD_CHESTPLATE:
			case IRON_CHESTPLATE:
			case CHAINMAIL_CHESTPLATE:
			case LEATHER_CHESTPLATE:
				return true;
		}
	}

	public static boolean isLeggings(Material material) {
		switch (material) {
			default:
				return false;
			case DIAMOND_LEGGINGS:
			case GOLD_LEGGINGS:
			case IRON_LEGGINGS:
			case CHAINMAIL_LEGGINGS:
			case LEATHER_LEGGINGS:
				return true;
		}
	}

	public static boolean isBoots(Material material) {
		switch (material) {
			default:
				return false;
			case DIAMOND_BOOTS:
			case GOLD_BOOTS:
			case IRON_BOOTS:
			case CHAINMAIL_BOOTS:
			case LEATHER_BOOTS:
				return true;
		}
	}
	
	public static boolean isBow(Material material) {
		return material == Material.BOW;
	}
}