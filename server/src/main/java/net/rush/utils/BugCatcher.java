package net.rush.utils;

public class BugCatcher {

	public static void TraceLog(String msg, boolean fromTop, int elementCount) {			
		System.out.println("[================[ TRACELOG START ]================]");

		try {
			throw new RuntimeException(msg);

		} catch (RuntimeException t) {

			String[] element = formatStackTrace(t.getStackTrace());

			if (fromTop)
				for (int i = 0; i < (elementCount > element.length ? element.length : elementCount); i++)
					print(element[i]);
			
			else
				for (int i = (elementCount > element.length ? element.length : elementCount); i > 0; i--)
					print(element[i]);
			
			/*print(element[3],
					" -> " + element[2],
					"   -> " + element[1] + " (" + msg + ")");*/
			System.out.println("[================[ TRACELOG END ]================]");
		}
	}

	private static String[] formatStackTrace(StackTraceElement... stacks) {
		String[] formattedStacks = new String[stacks.length];

		for (int i = 0; i < stacks.length; i++) {
			StackTraceElement stack = stacks[i];
			String[] classSplitted = stack.getClassName().split("\\.");

			formattedStacks[i] = classSplitted[classSplitted.length - 1] + "." + stack.getMethodName() + "() " + stack.getLineNumber() + ".";
		}

		return formattedStacks;
	}

	private static void print(String... messages) {
		for (String msg : messages)
			System.out.println(msg);
	}
}
