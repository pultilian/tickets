package server;

import common.IServer;
import common.response.*;
import server.model.UserData;

public class ServerFacade implements IServer {

    public static ServerFacade SINGLETON = new ServerFacade();

    private ServerFacade(){}

    @Override
    public LoginResponse login(String username, String password) {
        if (UserData.SINGLETON.verifyLogin(username, password)){
            String authToken = UserData.SINGLETON.createAuthToken(username);
            return new LoginResponse("Welcome, " + username, authToken);
        }
        else return new LoginResponse(new Exception("Username or password is incorrect."));
    }

    @Override
    public LoginResponse register(String username, String password) {
        if (UserData.SINGLETON.getUsername(username) != null){
            return new LoginResponse(new Exception("Username already exists."));
        }
        else{
            UserData.SINGLETON.addUser(username, password);
            String authToken = UserData.SINGLETON.createAuthToken(username);
            return new LoginResponse("Welcome, " + username, authToken);
        }
    }

    @Override
    public JoinLobbyResponse joinLobby(String lobbyID) {
        return null;
    }

    @Override
    public LogoutResponse logout() {
        return null;
    }

    @Override
    public StartGameResponse startGame(String lobbyID) {
        return null;
    }

    @Override
    public LeaveLobbyResponse leaveLobby(String lobbyID) {
        return null;
    }

    @Override
    public AddGuestResponse addGuest(String lobbyID) {
        return null;
    }

    @Override
    public PlayerTurnResponse takeTurn(String playerID) {
        return null;
    }
}
