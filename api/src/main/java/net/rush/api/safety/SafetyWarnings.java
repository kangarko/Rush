package net.rush.api.safety;

import org.apache.commons.lang3.Validate;

abstract class SafetyWarnings {
	
	protected boolean warn = false;
	protected boolean readOnly = false;
	
	protected SafetyWarnings() {		
	}
	
	public abstract SafetyWarnings showWarnings();
	public abstract SafetyWarnings setReadOnly();
	
	protected void warn(String msg) {
		if (warn)
			System.err.println("[" + getClass().getSimpleName() + "] " + msg);
	}
	
	protected void checkAccess() {
		Validate.isTrue(!readOnly, "Unsupported operation, read only!");
	}
}
