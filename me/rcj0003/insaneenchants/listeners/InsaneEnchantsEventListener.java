package me.rcj0003.insaneenchants.listeners;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.rcj0003.insaneenchants.EnchantedItemData;
import me.rcj0003.insaneenchants.api.EnchantServicePlugin;
import me.rcj0003.insaneenchants.enchant.InsaneDefensiveEnchant;
import me.rcj0003.insaneenchants.enchant.InsaneEnchant;
import me.rcj0003.insaneenchants.enchant.InsaneInteractiveEnchant;
import me.rcj0003.insaneenchants.enchant.InsaneKillEnchant;
import me.rcj0003.insaneenchants.enchant.InsaneMiningEnchant;
import me.rcj0003.insaneenchants.enchant.InsaneOffensiveEnchant;
import me.rcj0003.insaneenchants.enchant.InsaneProjectileEnchant;
import me.rcj0003.insaneenchants.itemdata.Enchantable;
import me.rcj0003.insaneenchants.itemdata.ItemData;
import me.rcj0003.insaneenchants.utilities.StringUtils;

public class InsaneEnchantsEventListener implements Listener {
	private EnchantServicePlugin servicePlugin;

	public InsaneEnchantsEventListener(EnchantServicePlugin servicePlugin) {
		this.servicePlugin = servicePlugin;
	}

	private boolean combineItems(ItemStack stack1, ItemStack stack2) {
		EnchantedItemData data1 = EnchantedItemData.from(servicePlugin.getEnchantDataFactory().getItemData(stack1));
		EnchantedItemData data2 = EnchantedItemData.from(servicePlugin.getEnchantDataFactory().getItemData(stack2));

		boolean updateData = false;

		for (Entry<InsaneEnchant, Integer> entry : data2.getEnchants().entrySet()) {
			InsaneEnchant enchant = entry.getKey();

			if (enchant.isEnchantApplicable(stack1)
					&& (!data1.hasEnchant(enchant) || data1.getEnchantLevel(enchant) < entry.getValue())) {
				data1.addEnchant(enchant, entry.getValue());
				updateData = true;
			}
		}

		if (updateData) {
			servicePlugin.getEnchantDataFactory().updateItem(stack1, data1);

			stack2.setType(Material.AIR);
			stack2.setAmount(0);

			return true;
		}

		return false;
	}

	private List<ItemStack> getItems(LivingEntity entity, boolean includeHandItem) {
		List<ItemStack> itemList = new ArrayList<>();
		itemList.addAll(Arrays.asList(entity.getEquipment().getArmorContents()));
		if (includeHandItem)
			itemList.add(entity.getEquipment().getItemInHand());
		return itemList;
	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onItemClick(InventoryClickEvent e) {
		if (e.getWhoClicked().getGameMode() == GameMode.CREATIVE || e.getCursor().getType() != Material.BOOK)
			return;

		if (e.getCursor().getAmount() != 1) {
			if (e.getCursor().getAmount() > 1)
				e.getWhoClicked()
						.sendMessage(StringUtils.convertColorCodes("&cYou cannot place stacked books onto an item!"));
			return;
		}

		if (e.getCurrentItem().getAmount() != 1) {
			if (e.getCurrentItem().getAmount() > 1)
				e.getWhoClicked()
						.sendMessage(StringUtils.convertColorCodes("&cYou cannot place a book onto a stacked item!"));
			return;
		}

		if (combineItems(e.getCurrentItem(), e.getCursor())) {
			e.setCursor(e.getCurrentItem());
			e.setCurrentItem(null);

			if (e.getWhoClicked() instanceof Player) {
				Player player = (Player) e.getWhoClicked();
				player.updateInventory();
				player.playSound(player.getLocation(), Sound.LEVEL_UP, 25, 1);
			}

			e.getWhoClicked().sendMessage(
					StringUtils.convertColorCodes("&aThe enchant book has successfully been applied to the item!"));
		} else
			e.getWhoClicked().sendMessage(
					StringUtils.convertColorCodes("&cThe enchant book was unable to be applied to the item!"));
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onEntityDamaged(EntityDamageEvent e) {
		if (e.getEntity() instanceof LivingEntity) {
			for (ItemStack stack : getItems((LivingEntity) e.getEntity(), true)) {
				ItemData itemData = servicePlugin.getEnchantDataFactory().getItemData(stack);
				if (itemData instanceof Enchantable) {
					Enchantable enchantData = (Enchantable) itemData;
					enchantData.getEnchants().keySet().stream()
							.filter(enchant -> enchant instanceof InsaneDefensiveEnchant)
							.map(enchant -> (InsaneDefensiveEnchant) enchant)
							.forEach(enchant -> enchant.onDamaged(e, e.getEntity(), enchantData));
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onEntityAttack(EntityDamageByEntityEvent e) {
		if ((e.getDamager() instanceof LivingEntity) && (e.getEntity() instanceof LivingEntity)) {
			for (ItemStack stack : getItems((LivingEntity) e.getDamager(), true)) {
				LivingEntity entity = (LivingEntity) e.getEntity();
				ItemData itemData = servicePlugin.getEnchantDataFactory().getItemData(stack);
				if (itemData instanceof Enchantable) {
					Enchantable enchantData = (Enchantable) itemData;

					if (entity.getHealth() - e.getDamage() > 0)
						enchantData.getEnchants().keySet().stream()
								.filter(enchant -> enchant instanceof InsaneOffensiveEnchant)
								.map(enchant -> (InsaneOffensiveEnchant) enchant)
								.forEach(enchant -> enchant.onAttackEntity(e, stack, enchantData));
					else
						enchantData.getEnchants().keySet().stream()
								.filter(enchant -> enchant instanceof InsaneKillEnchant)
								.map(enchant -> (InsaneKillEnchant) enchant)
								.forEach(enchant -> enchant.onKillEntity(e, stack, enchantData));
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onBreakBlock(BlockBreakEvent e) {
		for (ItemStack stack : getItems(e.getPlayer(), true)) {
			ItemData itemData = servicePlugin.getEnchantDataFactory().getItemData(stack);
			if (itemData instanceof Enchantable) {
				Enchantable enchantData = (Enchantable) itemData;
				enchantData.getEnchants().keySet().stream().filter(enchant -> enchant instanceof InsaneMiningEnchant)
						.map(enchant -> (InsaneMiningEnchant) enchant).forEach(enchant -> enchant
								.onBlockBreak(e.getPlayer(), e.getPlayer().getItemInHand(), enchantData, e.getBlock()));
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onProjectileHit(ProjectileHitEvent e) {
		if (e.getEntity().getShooter() instanceof LivingEntity) {
			for (ItemStack stack : getItems((LivingEntity) e.getEntity().getShooter(), true)) {
				ItemData itemData = servicePlugin.getEnchantDataFactory().getItemData(stack);
				if (itemData instanceof Enchantable) {
					Enchantable enchantData = (Enchantable) itemData;
					enchantData.getEnchants().keySet().stream()
							.filter(enchant -> enchant instanceof InsaneProjectileEnchant)
							.map(enchant -> (InsaneProjectileEnchant) enchant).forEach(enchant -> enchant
									.onProjectileHit(e, (LivingEntity) e.getEntity().getShooter(), enchantData));
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerInteract(PlayerInteractEvent e) {
		ItemData itemData = servicePlugin.getEnchantDataFactory().getItemData(e.getItem());
		if (itemData instanceof Enchantable) {
			Enchantable enchantData = (Enchantable) itemData;
			enchantData.getEnchants().keySet().stream().filter(enchant -> enchant instanceof InsaneInteractiveEnchant)
					.map(enchant -> (InsaneInteractiveEnchant) enchant)
					.forEach(enchant -> enchant.onInteract(e, e.getPlayer(), e.getItem(), enchantData));
		}
	}
}