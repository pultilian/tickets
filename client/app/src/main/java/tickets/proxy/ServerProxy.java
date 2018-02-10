package tickets.proxy;

import tickets.common.response.*;
import tickets.common.IServer;
import tickets.common.Lobby;
import tickets.common.UserData;

public class ServerProxy implements IServer {
	
	public static ServerProxy SINGLETON = new ServerProxy();
	private ServerProxy() { }
	
	public LoginResponse login(UserData userData) {
		return null;
	}

	public LoginResponse register(UserData userData) {
		return null;
	}

	public JoinLobbyResponse joinLobby(String lobbyID) {
		return null;
	}
	
	public JoinLobbyResponse createLobby(Lobby lobby) {
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
