package server;

import common.IServer;

public class ServerFacade implements IServer {

	//Create singleton
	private static ServerFacade INSTANCE = null;
	public static ServerFacade getInstance() {
		if (INSTANCE == null)
			INSTANCE = new ServerFacade();
		return INSTANCE;
	}

	@Override
	public String toLowerCase(String input) {
		return StringProcessor.getInstance().toLowerCase(input);
	}

	@Override
	public String trim(String input) {
		return StringProcessor.getInstance().trim(input);
	}

	@Override
	public int parseInteger(String input) throws NumberFormatException {
		return StringProcessor.getInstance().parseInteger(input);
	}

}
