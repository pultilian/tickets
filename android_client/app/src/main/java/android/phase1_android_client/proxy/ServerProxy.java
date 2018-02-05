package android.phase1_android_client.proxy;


import common.IServer;
import common.response.*;

public class ServerProxy implements IServer {
    public static ServerProxy SINGLETON = new ServerProxy();
    private ServerProxy() {}


    public LoginResponse login(String username, String password) {
        return null;
    }

    public LoginResponse register(String username, String password) {
        return null;
    }

    public JoinLobbyResponse joinLobby(String lobbyID) {
        return null;
    }

    public LogoutResponse logout() {
        return null;
    }

    public StartGameResponse startGame(String lobbyID) {
        return null;
    }

    public LeaveLobbyResponse leaveLobby(String lobbyID) {
        return null;
    }

    public AddGuestResponse addGuest(String lobbyID) {
        return null;
    }

    public PlayerTurnResponse takeTurn(String playerID) {
        return null;
    }
}
