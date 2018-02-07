package client.proxy;

import common.Command;
import common.response.*;
import common.IServer;
import common.Lobby;
import common.UserData;
import communicators.ClientCommunicator;

public class ServerProxy implements IServer {
	private static ServerProxy INSTANCE = null;
	private ClientCommunicator clientCom = ClientCommunicator.getInstance();
	private ServerProxy() {}

	public static ServerProxy getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ServerProxy();
		}
		return (INSTANCE);
	}
	
	public LoginResponse login(UserData userData) {
		Object[] parameters = {userData};
		String[] paramTypes = {"UserData"};
		Command command = new Command(parameters, paramTypes, "login");
		Command returnCommand = (Command) clientCom.send("/Command", command);
		return (LoginResponse)returnCommand.getParameters()[0];
	}

	public LoginResponse register(UserData userData) {
		Object[] parameters = {userData};
		String[] paramTypes = {"UserData"};
		Command command = new Command(parameters, paramTypes, "register");
		Command returnCommand = (Command) clientCom.send("/Command", command);
		return (LoginResponse)returnCommand.getParameters()[0];
	}

	public JoinLobbyResponse joinLobby(String lobbyID) {
		Object[] parameters = {lobbyID};
		String[] paramTypes = {"String"};
		Command command = new Command(parameters, paramTypes, "joinLobby");
		Command returnCommand = (Command) clientCom.send("/Command", command);
		return (JoinLobbyResponse) returnCommand.getParameters()[0];
	}
	
	public JoinLobbyResponse createLobby(Lobby lobby) {
		Object[] parameters = {lobby};
		String[] paramTypes = {"Lobby"};
		Command command = new Command(parameters, paramTypes, "createLobby");
		Command returnCommand = (Command) clientCom.send("/Command", command);
		return (JoinLobbyResponse) returnCommand.getParameters()[0];
	}

	public LogoutResponse logout() {
		Object[] parameters = null;
		String[] paramTypes = null;
		Command command = new Command(parameters, paramTypes, "logout");
		Command returnCommand = (Command) clientCom.send("/Command", command);
		return (LogoutResponse)returnCommand.getParameters()[0];
	}

	public StartGameResponse startGame(String lobbyID) {
		Object[] parameters = {lobbyID};
		String[] paramTypes = {"String"};
		Command command = new Command(parameters, paramTypes, "startGame");
		Command returnCommand = (Command) clientCom.send("/Command", command);
		return (StartGameResponse) returnCommand.getParameters()[0];
	}

	public LeaveLobbyResponse leaveLobby(String lobbyID) {
		Object[] parameters = {lobbyID};
		String[] paramTypes = {"String"};
		Command command = new Command(parameters, paramTypes, "leaveLobby");
		Command returnCommand = (Command) clientCom.send("/Command", command);
		return (LeaveLobbyResponse) returnCommand.getParameters()[0];
	}

	public AddGuestResponse addGuest(String lobbyID) {
		Object[] parameters = {lobbyID};
		String[] paramTypes = {"String"};
		Command command = new Command(parameters, paramTypes, "addGuest");
		Command returnCommand = (Command) clientCom.send("/Command", command);
		return (AddGuestResponse)returnCommand.getParameters()[0];
	}

	public PlayerTurnResponse takeTurn(String playerID) {
		Object[] parameters = {playerID};
		String[] paramTypes = {"String"};
		Command command = new Command(parameters, paramTypes, "takeTurn");
		Command returnCommand = (Command) clientCom.send("/Command", command);
		return (PlayerTurnResponse) returnCommand.getParameters()[0];
	}
}
