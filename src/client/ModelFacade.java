package client;

import common.UserData;

public class ModelFacade {
	public static ModelFacade SINGLETON = new ModelFacade();
	
	private ModelFacade() { }
	
	public boolean Login(UserData userData) {
		result = ServerProxy.SINGLETON.login(userData);
		return result;
	}
}
