package tickets.client;

import tickets.common.Command;
import tickets.common.response.*;
import tickets.common.IServer;
import tickets.common.Lobby;
import tickets.common.UserData;

import tickets.client.ClientCommunicator;

public class ServerProxy implements IServer {
    //Singleton structure
    private static ServerProxy INSTANCE = null;

    private ServerProxy() {
        clientCommunicator = ClientCommunicator.getInstance();
    }

    public static ServerProxy getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ServerProxy();
        }
        return (INSTANCE);
    }

    //member variables
    private ClientCommunicator clientCommunicator;

    public LoginResponse login(UserData userData) {
        Object[] parameters = {userData};
        String[] parameterTypes = {UserData.class.getName()};
        Command command = new Command("login", parameterTypes, parameters);
        Object result = clientCommunicator.send(command);
        return (LoginResponse) result;
    }

    public LoginResponse register(UserData userData) {
        Object[] parameters = {userData};
        String[] parameterTypes = {UserData.class.getName()};
        Command command = new Command("register", parameterTypes, parameters);
        Object result = clientCommunicator.send(command);
        return (LoginResponse) result;
    }

    public JoinLobbyResponse joinLobby(String lobbyID, String authToken) {
        Object[] parameters = {lobbyID, authToken};
        String[] parameterTypes = {String.class.getName(), String.class.getName()};
        Command command = new Command("joinLobby", parameterTypes, parameters);
        Object result = clientCommunicator.send(command);
        return (JoinLobbyResponse) result;
    }

    public JoinLobbyResponse createLobby(Lobby lobby, String authToken) {
        Object[] parameters = {lobby, authToken};
        String[] parameterTypes = {Lobby.class.getName(), String.class.getName()};
        Command command = new Command("createLobby", parameterTypes, parameters);
        Object result = clientCommunicator.send(command);
        return (JoinLobbyResponse) result;
    }

    public LogoutResponse logout(String authToken) {
        Object[] parameters = {authToken};
        String[] parameterTypes = {String.class.getName()};
        Command command = new Command("logout", parameterTypes, parameters);
        Object result = clientCommunicator.send(command);
        return (LogoutResponse) result;
    }

    public StartGameResponse startGame(String lobbyID, String authToken) {
        Object[] parameters = {lobbyID, authToken};
        String[] parameterTypes = {String.class.getName(), String.class.getName()};
        Command command = new Command("startGame", parameterTypes, parameters);
        Object result = clientCommunicator.send(command);
        return (StartGameResponse) result;
    }

    public LeaveLobbyResponse leaveLobby(String lobbyID, String authToken) {
        Object[] parameters = {lobbyID, authToken};
        String[] parameterTypes = {String.class.getName(), String.class.getName()};
        Command command = new Command("leaveLobby", parameterTypes, parameters);
        Object result = clientCommunicator.send(command);
        return (LeaveLobbyResponse) result;
    }

    public AddGuestResponse addGuest(String lobbyID, String authToken) {
        Object[] parameters = {lobbyID, authToken};
        String[] parameterTypes = {String.class.getName(), String.class.getName()};
        Command command = new Command("addGuest", parameterTypes, parameters);
        Object result = clientCommunicator.send(command);
        return (AddGuestResponse) result;
    }

    public PlayerTurnResponse takeTurn(String playerID, String authToken) {
        Object[] parameters = {playerID, authToken};
        String[] parameterTypes = {String.class.getName(), String.class.getName()};
        Command command = new Command("takeTurn", parameterTypes, parameters);
        Object result = clientCommunicator.send(command);
        return (PlayerTurnResponse) result;
    }

    public AddToChatResponse addToChat(String message, String authToken) {
        Object[] parameters = {message, authToken};
        String[] parameterTypes = {String.class.getName(), String.class.getName()};
        Command command = new Command("addToChat", parameterTypes, parameters);
        Object result = clientCommunicator.send(command);
        return (AddToChatResponse) result;
    }

    public ClientUpdate updateClient(String lastReceivedCommandID, String authToken) {
        Object[] parameters = {lastReceivedCommandID, authToken};
        String[] parameterTypes = {String.class.getName(), String.class.getName()};
        Command command = new Command("updateClient", parameterTypes, parameters);
        Object result = clientCommunicator.send(command);
        return (ClientUpdate) result;
    }
}
