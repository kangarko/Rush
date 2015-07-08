package net.rush.cmd;

import org.apache.commons.lang3.Validate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.rush.model.CommandSender;

@RequiredArgsConstructor
public abstract class Command {

	@Getter
	private final String label;
	@Getter
	private boolean playerRequired = false;
	
	public abstract void execute(CommandSender sender, String[] args);
	
	public final void setPlayerRequired() {
		Validate.isTrue(!playerRequired);
		
		this.playerRequired = true;
	}
}
