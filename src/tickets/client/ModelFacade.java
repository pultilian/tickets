package tickets.client;

import tickets.common.Lobby;
import tickets.common.UserData;
import tickets.common.response.*;
import tickets.client.model.ClientModelRoot;
import tickets.client.async.*;

public class ModelFacade {
	//Singleton structure
	public static ModelFacade INSTANCE = null;

	private ModelFacade() {
		modelRoot = new ClientModelRoot();
		asyncManager = new AsyncManager(modelRoot);
	}

	public static ModelFacade getInstance() {
		if (INSTANCE == null)
			INSTANCE = new ModelFacade();
		return INSTANCE;
	}
	
	//variables
	private ClientModelRoot modelRoot;
	private AsyncManager asyncManager;

	//methods

	//mirror the server interface to the presenters
	//calls are made on the ServerProxy via AsyncTask objects
	public void register(UserData userData) {
		asyncManager.register(userData);
		return;
	}

	public void login(UserData userData) throws Exception {
		asyncManager.login(userData);
		return;
	}

	public void joinLobby(String id) {
		asyncManager.joinLobby(id);
		return;
	}

	public void createLobby(Lobby lobby) {
		asyncManager.createLobby(lobby);
		return;
	}

	public void logout() {
		asyncManager.logout();
		return;
	}

	public void startGame(Lobby id) {
		asyncManager.startGame(id);
		return;
	}

	public void leaveLobby(UserData user) {
		asyncManager.leaveLobby(user);
		return;
	}

	public void addGuest(Lobby id) {
		asyncManager.addGuest(id);
		return;
	}

	public void takeTurn(String playerId) {
		asyncManager.takeTurn(playerId);
		return;
	}
}
