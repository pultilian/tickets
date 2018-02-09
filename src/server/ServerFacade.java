package server;

import common.IServer;
import common.Lobby;
import common.UserData;
import common.response.*;
import server.model.AllUsers;

import java.util.ArrayList;
import java.util.List;

public class ServerFacade implements IServer {

    private static ServerFacade INSTANCE = null;

    private List<ClientProxy> clientProxies;

    public static ServerFacade getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ServerFacade();
        }
        return INSTANCE;
    }

    private ServerFacade(){
        clientProxies = new ArrayList<>();
    }

    @Override
    public LoginResponse login(UserData userData) {
        if (!AllUsers.getInstance().userExists(userData.getUsername())){
            return new LoginResponse(new Exception("Username is incorrect."));
        }
        if (AllUsers.getInstance().verifyLogin(userData.getUsername(), userData.getPassword())){
            String authToken = AllUsers.getInstance().createAuthToken(userData.getUsername());
            clientProxies.add(new ClientProxy(authToken));
            return new LoginResponse("Welcome, " + userData.getUsername(), authToken);
        }
        else return new LoginResponse(new Exception("Password is incorrect."));
    }

    @Override
    public LoginResponse register(UserData userData) {
        if (AllUsers.getInstance().userExists(userData.getUsername())){
            return new LoginResponse(new Exception("Username already exists."));
        }
        else{
            AllUsers.getInstance().addUser(userData.getUsername(), userData.getPassword());
            String authToken = AllUsers.getInstance().createAuthToken(userData.getUsername());
            clientProxies.add(new ClientProxy(authToken));
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
