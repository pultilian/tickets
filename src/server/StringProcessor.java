package server;

import common.IServer;

public class StringProcessor implements IServer {

	//Create singleton
	private static StringProcessor INSTANCE = null;
	public static StringProcessor getInstance() {
		if(INSTANCE == null)
			INSTANCE = new StringProcessor();
		return INSTANCE;
	}

	public String toLowerCase(String input) {
		return input.toLowerCase();
	}

	public String trim(String input) {
		return input.trim();
	}

	public int parseInteger(String input) throws NumberFormatException {
		return Integer.parseInt(input);
	}

}
