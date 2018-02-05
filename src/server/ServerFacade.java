package server;

import common.IServer;
import common.Lobby;
import common.UserData;
import common.response.*;
import server.model.AllUsers;

public class ServerFacade implements IServer {

    public static ServerFacade SINGLETON = new ServerFacade();

    private ServerFacade(){}

    @Override
    public LoginResponse login(UserData userData) {
        if (AllUsers.SINGLETON.verifyLogin(userData.getUsername(), userData.getPassword())){
            String authToken = AllUsers.SINGLETON.createAuthToken(userData.getUsername());
            return new LoginResponse("Welcome, " + userData.getUsername(), authToken);
        }
        else return new LoginResponse(new Exception("Username or password is incorrect."));
    }

    @Override
    public LoginResponse register(UserData userData) {
        if (AllUsers.SINGLETON.getUsername(userData.getUsername()) != null){
            return new LoginResponse(new Exception("Username already exists."));
        }
        else{
            AllUsers.SINGLETON.addUser(userData.getUsername(), userData.getPassword());
            String authToken = AllUsers.SINGLETON.createAuthToken(userData.getUsername());
            return new LoginResponse("Welcome, " + userData.getUsername(), authToken);
        }
    }

    @Override
    public JoinLobbyResponse joinLobby(String lobbyID) {
        return null;
    }

    @Override
    public JoinLobbyResponse createLobby(Lobby lobby) {
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
