package net.rush.utils;

public class BugCatcher {

	public static void TraceLog(String msg) {			
		System.out.println("\n");
		try {
			throw new RuntimeException(msg);
		} catch (RuntimeException t) {

			String[] element = formatStackTrace(t.getStackTrace());


			print(element[3],
					" -> " + element[2],
					"   -> " + element[1] + " (" + msg + ")");

		}
	}

	private static String[] formatStackTrace(StackTraceElement... stacks) {
		String[] formattedStacks = new String[stacks.length];

		for (int i = 0; i < stacks.length; i++) {
			StackTraceElement stack = stacks[i];
			String[] splitted = stack.getClassName().split("\\.");

			formattedStacks[i] = splitted[splitted.length - 1] + "." + stack.getMethodName() + "()";
		}

		return formattedStacks;
	}

	private static void print(String... messages) {
		for (String msg : messages)
			System.out.println(msg);
	}
}
