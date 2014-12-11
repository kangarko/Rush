package net.rush.cmd;

import lombok.RequiredArgsConstructor;
import net.rush.api.model.CommandSender;

@RequiredArgsConstructor
public abstract class Command {

	public final String label;
	public boolean noConsole = false;
	
	public abstract void execute(CommandSender sender, String[] args);
}
