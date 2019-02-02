package me.rcj0003.insaneenchants.listeners;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import me.rcj0003.insaneenchants.api.EnchantServicePlugin;
import me.rcj0003.insaneenchants.enchant.InsaneComboEnchant;
import me.rcj0003.insaneenchants.itemdata.Enchantable;
import me.rcj0003.insaneenchants.itemdata.ItemData;

public class InsaneComboEnchantListener implements Listener {
	private EnchantServicePlugin enchantServicePlugin;
	private ComboMonitor comboMonitor;

	public InsaneComboEnchantListener(EnchantServicePlugin enchantServicePlugin) {
		this.enchantServicePlugin = enchantServicePlugin;
		this.comboMonitor = new ComboMonitor(750, true);
	}

	private List<ItemStack> getItems(LivingEntity entity, boolean includeHandItem) {
		List<ItemStack> itemList = new ArrayList<>();
		itemList.addAll(Arrays.asList(entity.getEquipment().getArmorContents()));
		if (includeHandItem)
			itemList.add(entity.getEquipment().getItemInHand());
		return itemList;
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onLogoff(PlayerQuitEvent e) {
		comboMonitor.removeComboDataByPlayer(e.getPlayer());
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onEntityAttack(EntityDamageByEntityEvent e) {
		comboMonitor.updateComboData(e);

		if (e.getDamager() instanceof Player) {
			Player player = (Player) e.getDamager();
			ComboData comboData = comboMonitor.getComboDataByPlayer(player);
			
			if (comboData.getChainAmount() <= 1)
				return;
			
			for (ItemStack stack : getItems(player, true)) {
				ItemData itemData = enchantServicePlugin.getEnchantDataFactory().getItemData(stack);
				if (itemData instanceof Enchantable) {
					Enchantable enchantData = (Enchantable) itemData;

					enchantData.getEnchants().keySet().stream()
							.filter(enchant -> enchant instanceof InsaneComboEnchant)
							.map(enchant -> (InsaneComboEnchant) enchant)
							.forEach(enchant -> enchant.onCombo(player, comboData, e, enchantData));
				}
			}
		}
	}
}