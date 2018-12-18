package me.rcj0003.insaneenchants.utilities.command;

public class MessageWrapper {
	private String[] no_args;
	private String command_not_found, no_perms, requires_player, usage;
	
	public MessageWrapper(String[] no_args, String command_not_found, String no_perms, String requires_player, String usage) {
		this.no_args = no_args;
		this.command_not_found = command_not_found;
		this.no_perms = no_perms;
		this.requires_player = requires_player;
		this.usage = usage;
	}
	
	public String[] getNoArgumentsMessage() {
		return no_args;
	}
	
	public String getCommandNotFoundMessage() {
		return command_not_found;
	}
	
	public String getNoPermsMessage() {
		return no_perms;
	}
	
	public String getRequiresPlayerMessage() {
		return requires_player;
	}
	
	public String getUsageMessage() {
		return usage;
	}
}