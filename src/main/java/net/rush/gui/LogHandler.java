package net.rush.gui;

import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import javax.swing.JTextArea;

public class LogHandler extends Handler {


	private Formatter formatter;
	private JTextArea cmdArea;

	public LogHandler(JTextArea cmdArea) {
		this.cmdArea = cmdArea;
		
		formatter = new Formatter() {
			
			@Override
			public String format(LogRecord record) {
				return trim("[" + record.getLevel().getName() + "] " + record.getMessage() + "\n");
			}
		};
		
		setFormatter(formatter);
	}

	private String trim(String str) {
		return str.replaceAll("(&|§)([a-f0-9k-or])", "");
	}
	
	public void close() {
	}

	public void flush() {
	}

	public void publish(LogRecord record) {
		cmdArea.append(formatter.format(record));
		cmdArea.setCaretPosition(cmdArea.getDocument().getLength());
	}
}
