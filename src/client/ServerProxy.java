package client;

import common.IServer;
import common.Command;
import communicators.ClientCommunicator;

public class ServerProxy implements IServer {

	//Create singleton
	private static ServerProxy INSTANCE = null;
	public static ServerProxy getInstance() {
		if(INSTANCE == null)
			INSTANCE = new ServerProxy();
		return INSTANCE;
	}

	@Override
	public String toLowerCase(String input) {
		String[] parameterTypeNames = {String.class.getName()};
		Object[] parameters = {input};
		Command command = new Command("toLowerCase", parameterTypeNames, parameters);
		Object result = ClientCommunicator.getInstance().send(command);
		return (String)result;
	}

	@Override
	public String trim(String input) {
		String[] parameterTypeNames = {String.class.getName()};
		Object[] parameters = {input};
		Command command = new Command("trim", parameterTypeNames, parameters);
		Object result = ClientCommunicator.getInstance().send(command);
		return (String)result;
	}

	@Override
	public int parseInteger(String input) throws NumberFormatException {
		String[] parameterTypeNames = {String.class.getName()};
		Object[] parameters = {input};
		Command command = new Command("parseInteger", parameterTypeNames, parameters);
		Object result = ClientCommunicator.getInstance().send(command);
		try {
			return (int)result;
		} catch (ClassCastException e) {
			throw new NumberFormatException();
		}
	}

}
