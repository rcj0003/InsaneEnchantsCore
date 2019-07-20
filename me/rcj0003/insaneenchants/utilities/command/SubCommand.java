package me.rcj0003.insaneenchants.utilities.command;

public interface SubCommand {
	boolean getRequiresPlayer();
	int getMinimumArguments();
	
	@Deprecated
	String getPermission();
	
	@Deprecated
	boolean hasPermission(CommandUser user);
	
	String getName();
	String[] getDescription();
	String getUsage();
	
	void execute(CommandUser user, String[] arguments);
}