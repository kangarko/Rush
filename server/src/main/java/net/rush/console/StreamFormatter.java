package net.rush.console;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StreamFormatter extends ByteArrayOutputStream {

	private final Logger logger;
	private final Level level;

	public StreamFormatter(Logger logger, Level level) {		
		this.logger = logger;
		this.level = level;
	}

	@Override
	public void flush() throws IOException {		
		synchronized (this) {
			super.flush();
			String record = this.toString();
			super.reset();

			if (record.length() > 0 && !record.equals(System.lineSeparator()))
				logger.log(level, record);
		}
	}
}
