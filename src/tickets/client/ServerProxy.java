package tickets.client;

import common.Command;
import common.response.*;
import common.IServer;
import common.Lobby;
import common.UserData;
import communicators.ClientCommunicator;

public class ServerProxy implements IServer {
	private static ServerProxy INSTANCE = null;
	private ClientCommunicator clientCommunicator = ClientCommunicator.getInstance();

	public static ServerProxy getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ServerProxy();
		}
		return (INSTANCE);
	}
	
	public LoginResponse login(UserData userData) {
		Object[] parameters = {userData};
		String[] parameterTypes = {UserData.class.getName()};
		Command command = new Command("login", parameterTypes, parameters);
		Object result = clientCommunicator.send(command);
		return (LoginResponse)result;
	}

	public LoginResponse register(UserData userData) {
		Object[] parameters = {userData};
		String[] parameterTypes = {UserData.class.getName()};
		Command command = new Command("register", parameterTypes, parameters);
		Object result = clientCommunicator.send(command);
		return (LoginResponse)result;
	}

	public JoinLobbyResponse joinLobby(String lobbyID) {
		Object[] parameters = {lobbyID};
		String[] parameterTypes = {String.class.getName()};
		Command command = new Command("joinLobby", parameterTypes, parameters);
		Object result = clientCommunicator.send(command);
		return (JoinLobbyResponse)result;
	}
	
	public JoinLobbyResponse createLobby(Lobby lobby) {
		Object[] parameters = {lobby};
		String[] parameterTypes = {Lobby.class.getName()};
		Command command = new Command("createLobby", parameterTypes, parameters);
		Object result = clientCommunicator.send(command);
		return (JoinLobbyResponse)result;
	}

	public LogoutResponse logout() {
		Object[] parameters = null; // These may need to be empty arrays rather than null
		String[] parameterTypes = null;
		Command command = new Command("logout", parameterTypes, parameters);
		Object result = clientCommunicator.send(command);
		return (LogoutResponse)result;
	}

	public StartGameResponse startGame(String lobbyID) {
		Object[] parameters = {lobbyID};
		String[] parameterTypes = {String.class.getName()};
		Command command = new Command("startGame", parameterTypes, parameters);
		Object result = clientCommunicator.send(command);
		return (StartGameResponse)result;
	}

	public LeaveLobbyResponse leaveLobby(String lobbyID) {
		Object[] parameters = {lobbyID};
		String[] parameterTypes = {String.class.getName()};
		Command command = new Command("leaveLobby", parameterTypes, parameters);
		Object result = clientCommunicator.send(command);
		return (LeaveLobbyResponse)result;
	}

	public AddGuestResponse addGuest(String lobbyID) {
		Object[] parameters = {lobbyID};
		String[] parameterTypes = {String.class.getName()};
		Command command = new Command("addGuest", parameterTypes, parameters);
		Object result = clientCommunicator.send(command);
		return (AddGuestResponse)result;
	}

	public PlayerTurnResponse takeTurn(String playerID) {
		Object[] parameters = {playerID};
		String[] parameterTypes = {String.class.getName()};
		Command command = new Command("takeTurn", parameterTypes, parameters);
		Object result = clientCommunicator.send(command);
		return (PlayerTurnResponse)result;
	}
}
