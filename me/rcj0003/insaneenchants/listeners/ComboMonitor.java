package me.rcj0003.insaneenchants.listeners;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class ComboMonitor {
	private long maxIdleTime;
	private boolean resetComboOnDamage;

	private Map<UUID, ComboData> cache = new HashMap<>();

	public ComboMonitor(long maxIdleTime, boolean resetComboOnDamage) {
		this.maxIdleTime = maxIdleTime;
		this.resetComboOnDamage = resetComboOnDamage;
	}
	
	public void removeComboDataByPlayer(Player player) {
		removeComboDataById(player.getUniqueId());
	}
	
	public void removeComboDataById(UUID id) {
		cache.remove(id);
	}

	public ComboData getComboDataByPlayer(Player player) {
		return getComboDataById(player.getUniqueId());
	}

	public ComboData getComboDataById(UUID id) {
		if (cache.containsKey(id))
			return cache.get(id);
		else {
			ComboData comboData = new ComboData();
			cache.put(id, comboData);
			return comboData;
		}
	}

	public void updateComboData(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
			Player player = (Player) e.getDamager();
			ComboData comboData = getComboDataByPlayer(player);
			if (System.currentTimeMillis() > comboData.getLastAttackTime() + maxIdleTime)
				comboData.resetCombo();
			comboData.updateCombo();
		}

		if (resetComboOnDamage && (e.getEntity() instanceof Player)) {
			getComboDataByPlayer((Player) e.getEntity()).resetCombo();
		}
	}
}