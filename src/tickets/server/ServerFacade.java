package tickets.server;

import tickets.common.Game;
import tickets.server.model.AllLobbies;
import tickets.common.IServer;
import tickets.common.Lobby;
import tickets.common.Player;
import tickets.common.UserData;
import tickets.common.response.*;
import tickets.server.model.AllUsers;

import java.util.*;

public class ServerFacade implements IServer {

    private static ServerFacade INSTANCE = null;

    private List<ClientProxy> clientsInLobbyList;
    private Map<ClientProxy, Lobby> clientsInALobby;
    private Map<ClientProxy, Game> clientsInAGame;

    public static ServerFacade getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ServerFacade();
        }
        return INSTANCE;
    }

    private ServerFacade(){
        clientsInLobbyList = new ArrayList<>();
        clientsInALobby = new HashMap<>();
        clientsInAGame = new HashMap<>();
    }

    @Override
    public LoginResponse login(UserData userData) {
        if (AllUsers.getInstance().verifyLogin(userData.getUsername(), userData.getPassword())){
            String authToken = AllUsers.getInstance().getAuthToken(userData.getUsername());
            clientsInLobbyList.add(new ClientProxy(authToken));
            return new LoginResponse("Welcome, " + userData.getUsername(), authToken);
        }
        else if (!AllUsers.getInstance().userExists(userData.getUsername())){
            return new LoginResponse(new Exception("Username is incorrect."));
        }
        else return new LoginResponse(new Exception("Password is incorrect."));
    }

    @Override
    public LoginResponse register(UserData userData) {
        if (AllUsers.getInstance().userExists(userData.getUsername())){
            return new LoginResponse(new Exception("Username already exists."));
        }
        else{
            String authToken = AllUsers.getInstance().addUser(userData);
            clientsInLobbyList.add(new ClientProxy(authToken));
            return new LoginResponse("Welcome, " + userData.getUsername(), authToken);
        }
    }

    @Override
    public JoinLobbyResponse joinLobby(String lobbyID, String authToken) {
        Lobby lobby = AllLobbies.getInstance().getLobby(lobbyID);
        if (lobby == null) return new JoinLobbyResponse(new Exception("Lobby does not exist."));
        else if (lobby.getCurrentMembers() == lobby.getMaxMembers()) return new JoinLobbyResponse(new Exception("Lobby is full."));
        else {
            // Update the server model
            Player player = new Player(UUID.randomUUID().toString(), authToken);
            lobby.addPlayer(player);
            lobby.addToHistory(AllUsers.getInstance().getUsername(authToken) + " has joined the lobby.");

            // Move current client
            ClientProxy currentClient = getProxy(authToken);
            clientsInLobbyList.remove(currentClient);
            clientsInALobby.put(currentClient, AllLobbies.getInstance().getLobby(lobbyID));

            // Update relevant clients
            for (ClientProxy client : clientsInLobbyList) {
                client.addPlayerToLobbyInList(lobby, player);
            }
            for (ClientProxy client : getClientsInLobby(lobbyID)) {
                client.addPlayer(player);
            }
            return new JoinLobbyResponse(lobbyID, lobby.getHistory());
        }
    }

    @Override
    public JoinLobbyResponse createLobby(Lobby lobby, String authToken) {
        // Update the server model
        AllLobbies.getInstance().addLobby(lobby);

        // Move current client
        ClientProxy currentClient = getProxy(authToken);
        clientsInLobbyList.remove(currentClient);
        clientsInALobby.put(currentClient, lobby);

        // Update relevant clients
        for (ClientProxy client : clientsInLobbyList) {
            client.addLobbyToList(lobby);
        }
        return new JoinLobbyResponse(lobby.getId(), lobby.getHistory());
    }

    @Override
    public LogoutResponse logout(String authToken) {
        ClientProxy client = getProxy(authToken);
        clientsInLobbyList.remove(client);
        return new LogoutResponse("Logout successful");
    }

    @Override
    public StartGameResponse startGame(String lobbyID, String authToken) {
        Lobby lobby = AllLobbies.getInstance().getLobby(lobbyID);
        if (lobby == null) return new StartGameResponse(new Exception("Lobby does not exist."));
        else {
            // Update relevant clients
            for (ClientProxy client : getClientsInLobby(lobbyID)) {
                // The current client will receive a start game response instead of this command.
                if (!client.getAuthToken().equals(authToken)) client.startGame();
            }
            Game game = new Game(UUID.randomUUID().toString());
            return new StartGameResponse(game.getGameId());
        }
    }

    @Override
    public LeaveLobbyResponse leaveLobby(String lobbyID, String authToken) {
        Lobby lobby = AllLobbies.getInstance().getLobby(lobbyID);
        if (lobby == null) return new LeaveLobbyResponse(new Exception("Lobby does not exist."));
        else {
            // Move current client
            ClientProxy currentClient = getProxy(authToken);
            clientsInALobby.remove(currentClient);
            clientsInLobbyList.add(currentClient);

            // Update the server model and relevant clients
            List<Player> playersForUser = lobby.getPlayersWithAuthToken(authToken);
            for (Player player : playersForUser) {
                lobby.removePlayer(player);

                for (ClientProxy client : getClientsInLobby(lobbyID)) {
                    client.removePlayer(player);
                }
                for (ClientProxy client : clientsInLobbyList) {
                    client.removePlayerFromLobbyInList(lobby, player);
                }
            }
            return new LeaveLobbyResponse("You have left the lobby.");
        }
    }

    @Override
    public AddGuestResponse addGuest(String lobbyID, String authToken) {
        Lobby lobby = AllLobbies.getInstance().getLobby(lobbyID);
        if (lobby == null) return new AddGuestResponse(new Exception("Lobby does not exist."));
        else if (lobby.getCurrentMembers() == lobby.getMaxMembers()) return new AddGuestResponse(new Exception("Lobby is full."));
        else {
            // Update the server model
            Player guest = new Player(UUID.randomUUID().toString(), authToken);
            lobby.addPlayer(guest);
            lobby.addToHistory(AllUsers.getInstance().getUsername(authToken) + " has added a guest.");

            // Update relevant clients
            for (ClientProxy client : clientsInLobbyList) {
                client.addPlayerToLobbyInList(lobby, guest);
            }
            for (ClientProxy client : getClientsInLobby(lobbyID)) {
                client.addPlayer(guest);
            }
            return new AddGuestResponse("Guest added", guest.getPlayerId());
        }
    }

    @Override
    public PlayerTurnResponse takeTurn(String playerID, String authToken) {
        return null;
    }

    // PRIVATE HELPER METHODS------------------------------------------------------------------------------------------

    private ClientProxy getProxy(String authToken) {
        for (ClientProxy proxy : clientsInLobbyList) {
            if (proxy.getAuthToken().equals(authToken)) return proxy;
        }
        for (ClientProxy proxy : clientsInALobby.keySet()) {
            if (proxy.getAuthToken().equals(authToken)) return proxy;
        }
        for (ClientProxy proxy : clientsInAGame.keySet()) {
            if (proxy.getAuthToken().equals(authToken)) return proxy;
        }

        return null;
    }

    private List<ClientProxy> getClientsInLobby(String lobbyID) {
        Lobby lobby = AllLobbies.getInstance().getLobby(lobbyID);
        List<ClientProxy> result = new ArrayList<>();

        for (Map.Entry<ClientProxy, Lobby> entry : clientsInALobby.entrySet()) {
            if (entry.getValue().equals(lobby)) result.add(entry.getKey());
        }
        return result;
    }
}
