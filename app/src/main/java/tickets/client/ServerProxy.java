package tickets.client;

import tickets.common.Command;
import tickets.common.DestinationCard;
import tickets.common.response.*;
import tickets.common.IServer;
import tickets.common.Lobby;
import tickets.common.UserData;
import tickets.common.Route;

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

    public TrainCardResponse drawTrainCard(String authToken) {
        Object[] parameters = {authToken};
        String[] parameterTypes = {String.class.getName()};
        Command command = new Command("drawTrainCard", parameterTypes, parameters);
        Object result = clientCommunicator.send(command);
        return (TrainCardResponse) result;
    }

    public TrainCardResponse drawFaceUpCard(int position, String authToken) {
        Object[] parameters = {position, authToken};
        String[] parameterTypes = {int.class.getName(), String.class.getName()};
        Command command = new Command("drawFaceUpCard", parameterTypes, parameters);
        Object result = clientCommunicator.send(command);
        return (TrainCardResponse) result;
    }

    public DestinationCardResponse drawDestinationCard(String authToken) {
        Object[] parameters = {authToken};
        String[] parameterTypes = {String.class.getName()};
        Command command = new Command("drawDestinationCard", parameterTypes, parameters);
        Object result = clientCommunicator.send(command);
        return (DestinationCardResponse) result;
    }

    public Response chooseDestinationCards(DestinationCard toDiscard, String authToken) {
        Object[] parameters = {toDiscard, authToken};
        String[] parameterTypes = {DestinationCard.class.getName(), String.class.getName()};
        Command command = new Command("chooseDestinationCards", parameterTypes, parameters);
        Object result = clientCommunicator.send(command);
        return (Response) result;
    }

    public ClientUpdate updateClient(String lastReceivedCommandID, String authToken) {
        Object[] parameters = {lastReceivedCommandID, authToken};
        String[] parameterTypes = {String.class.getName(), String.class.getName()};
        Command command = new Command("updateClient", parameterTypes, parameters);
        Object result = clientCommunicator.send(command);
        return (ClientUpdate) result;
    }


    public Response claimRoute(Route route, String authToken) {
        Object[] parameters = {route, authToken};
        String[] parameterTypes = {Route.class.getName(), String.class.getName()};
        Command command = new Command("claimRoute", parameterTypes, parameters);
        Object result = clientCommunicator.send(command);
        return (Response) result;
    }
    public Response discardDestinationCard(DestinationCard discard, String authToken) {
        Object[] parameters = {discard, authToken};
        String[] parameterTypes = {DestinationCard.class.getName(), String.class.getName()};
        Command command = new Command("discardDestinationCard", parameterTypes, parameters);
        Object result = clientCommunicator.send(command);
        return (Response) result;
    }
    public Response endTurn(String authToken) {
        Object[] parameters = {authToken};
        String[] parameterTypes = {String.class.getName()};
        Command command = new Command("endTurn", parameterTypes, parameters);
        Object result = clientCommunicator.send(command);
        return (Response) result;
    }

}
