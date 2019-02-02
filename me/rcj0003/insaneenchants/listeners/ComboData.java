package me.rcj0003.insaneenchants.listeners;

public class ComboData {
	private long lastAttackTime;
	private int chainSize;
	
	public ComboData() {
		this.lastAttackTime = 0;
		this.chainSize = 0;
	}
	
	public void resetCombo() {
		lastAttackTime = 0;
		chainSize = 0;
	}
	
	public void updateCombo() {
		lastAttackTime = System.currentTimeMillis();
		chainSize++;
	}
	
	public long getLastAttackTime() {
		return lastAttackTime;
	}
	
	public int getChainAmount() {
		return chainSize;
	}
}