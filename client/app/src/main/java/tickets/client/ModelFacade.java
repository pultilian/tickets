
package tickets.client;

import java.util.Map;

import tickets.common.Lobby;
import tickets.common.UserData;

import tickets.client.model.ClientModelRoot;
import tickets.client.model.observable.*;
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

//----------------------------------------------------------------------------
//	methods

//-------------------------------------------------
//		model root methods

    public void linkObserver(IObserver observer) {
        modelRoot.linkObserver(observer);
        return;
    }

    public Map<String, Lobby> getLobbyList() {
        return modelRoot.getLobbyList();
    }

    public Lobby getLobby() {
        return modelRoot.getCurrentLobby();
    }

//-------------------------------------------------
//		server interface methods
//
//mirror the server interface to the presenters
//calls are made on the ServerProxy via AsyncTask objects

    public void register(UserData userData) {
        modelRoot.setUserData(userData);
        asyncManager.register(userData);
        return;
    }

    public void login(UserData userData) {
        modelRoot.setUserData(userData);
        asyncManager.login(userData);
        return;
    }

    public void joinLobby(String lobbyID) {
        String authToken = modelRoot.getAuthenticationToken();
        asyncManager.joinLobby(lobbyID, authToken);
        return;
    }

    public void createLobby(Lobby lobby) {
        String authToken = modelRoot.getAuthenticationToken();
        asyncManager.createLobby(lobby, authToken);
        return;
    }

    public void logout() {
        String authToken = modelRoot.getAuthenticationToken();
        asyncManager.logout(authToken);
        return;
    }

    public void startGame(String lobbyID) {
        String authToken = modelRoot.getAuthenticationToken();
        asyncManager.startGame(lobbyID, authToken);
        return;
    }

    public void leaveLobby(String lobbyID) {
        String authToken = modelRoot.getAuthenticationToken();
        asyncManager.leaveLobby(lobbyID, authToken);
        return;
    }

    public void addGuest(String lobbyID) {
        String authToken = modelRoot.getAuthenticationToken();
        asyncManager.addGuest(lobbyID, authToken);
        return;
    }

    public void takeTurn(String playerID) {
        String authToken = modelRoot.getAuthenticationToken();
        asyncManager.takeTurn(playerID, authToken);
        return;
    }

    public void getAllLobbies() {
        String authToken = modelRoot.getAuthenticationToken();
        asyncManager.getAllLobbies(authToken);
        return;
    }

    public void getLobbyData(String lobbyID) {
        String authToken = modelRoot.getAuthenticationToken();
        asyncManager.getLobbyData(lobbyID, authToken);
        return;
    }
}
