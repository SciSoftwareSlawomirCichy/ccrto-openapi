package org.ccrto.openapi.test.utils;

public class SystemOutLogger {

	public static void log(String method, String message, Object... args) {
		System.out.println("--> [" + method + "] " + String.format(message, args));
	}

}
