package tickets.server;

import tickets.common.*;
import tickets.server.model.AllGames;
import tickets.server.model.AllLobbies;
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
            return new LoginResponse("Welcome, " + userData.getUsername(), authToken, AllLobbies.getInstance().getAllLobbies());
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
            return new LoginResponse("Welcome, " + userData.getUsername(), authToken, AllLobbies.getInstance().getAllLobbies());
        }
    }

    @Override
    public JoinLobbyResponse joinLobby(String lobbyID, String authToken) {
        if (getProxy(authToken) == null) return new JoinLobbyResponse(new Exception("You are not an authorized user!"));

        Lobby lobby = AllLobbies.getInstance().getLobby(lobbyID);
        if (lobby == null) return new JoinLobbyResponse(new Exception("Lobby does not exist."));
        else if (lobby.getCurrentMembers() == lobby.getMaxMembers()) return new JoinLobbyResponse(new Exception("Lobby is full."));
        else {
            // Update the server model
            Player player = new Player(UUID.randomUUID().toString(), authToken);
            lobby.addPlayer(player);
            lobby.assignFaction(player);
            lobby.addToHistory(AllUsers.getInstance().getUsername(authToken) + " has joined the lobby.");

            // Move current client
            ClientProxy currentClient = getProxy(authToken);
            clientsInLobbyList.remove(currentClient);
            currentClient.clearCommands();
            clientsInALobby.put(currentClient, AllLobbies.getInstance().getLobby(lobbyID));

            // Update relevant clients
            for (ClientProxy client : clientsInLobbyList) {
                client.addPlayerToLobbyInList(lobby, player);
            }
            for (ClientProxy client : getClientsInLobby(lobbyID)) {
                client.setPlayer(player);
            }
            return new JoinLobbyResponse(lobby, player);
        }
    }

    @Override
    public JoinLobbyResponse createLobby(Lobby lobby, String authToken) {
        if (getProxy(authToken) == null) return new JoinLobbyResponse(new Exception("You are not an authorized user!"));
        if (lobbyNameExists(lobby.getName())) return new JoinLobbyResponse(new Exception("Lobby name already exists."));

        // Update the server model
        AllLobbies.getInstance().addLobby(lobby);
        Player player = new Player(UUID.randomUUID().toString(), authToken);
        lobby.addPlayer(player);
        lobby.addToHistory(AllUsers.getInstance().getUsername(authToken) + " has joined the lobby.");

        // Move current client
        ClientProxy currentClient = getProxy(authToken);
        clientsInLobbyList.remove(currentClient);
        currentClient.clearCommands();
        clientsInALobby.put(currentClient, lobby);

        // Update relevant clients
        for (ClientProxy client : clientsInLobbyList) {
            client.addLobbyToList(lobby);
        }
        return new JoinLobbyResponse(lobby, player);
    }

    @Override
    public LogoutResponse logout(String authToken) {
        if (getProxy(authToken) == null) return new LogoutResponse(new Exception("You are not an authorized user!"));

        ClientProxy client = getProxy(authToken);
        clientsInLobbyList.remove(client);
        return new LogoutResponse("Logout successful");
    }

    @Override
    public StartGameResponse startGame(String lobbyID, String authToken) {
        if (getProxy(authToken) == null) return new StartGameResponse(new Exception("You are not an authorized user!"));

        Lobby lobby = AllLobbies.getInstance().getLobby(lobbyID);
        if (lobby == null) return new StartGameResponse(new Exception("Lobby does not exist."));
        else {
            // Update server model
            AllLobbies.getInstance().removeLobby(lobbyID);
            Game game = new Game(UUID.randomUUID().toString());
            AllGames.getInstance().addGame(game);

            // Update relevant clients and move clients from lobby to game
            for (ClientProxy client : getClientsInLobby(lobbyID)) {
                // The current client will receive a start game response instead of this command.
                if (!client.getAuthToken().equals(authToken)) client.startGame();
                clientsInALobby.remove(client);
                client.clearCommands();
                clientsInAGame.put(client, game);
            }
            for (ClientProxy client: clientsInLobbyList) {
                client.removeLobbyFromList(lobby);
            }

            AllGames.getInstance().addGame(game);
            return new StartGameResponse(game.getGameId());
        }
    }

    @Override
    public LeaveLobbyResponse leaveLobby(String lobbyID, String authToken) {
        if (getProxy(authToken) == null) return new LeaveLobbyResponse(new Exception("You are not an authorized user!"));

        Lobby lobby = AllLobbies.getInstance().getLobby(lobbyID);
        if (lobby == null) return new LeaveLobbyResponse(new Exception("Lobby does not exist."));
        else {
            // Move current client
            ClientProxy currentClient = getProxy(authToken);
            clientsInALobby.remove(currentClient);
            currentClient.clearCommands();
            clientsInLobbyList.add(currentClient);

            // Update the server model and relevant clients
            List<Player> playersForUser = lobby.getPlayersWithAuthToken(authToken);
            for (Player player : playersForUser) {
                lobby.removePlayer(player);
                lobby.unassignFaction(player);

                for (ClientProxy client : getClientsInLobby(lobbyID)) {
                    client.removePlayer(player);
                }
                for (ClientProxy client : clientsInLobbyList) {
                    client.removePlayerFromLobbyInList(lobby, player);
                }
            }
            return new LeaveLobbyResponse("You have left the lobby.", AllLobbies.getInstance().getAllLobbies());
        }
    }

    @Override
    public AddGuestResponse addGuest(String lobbyID, String authToken) {
        if (getProxy(authToken) == null) return new AddGuestResponse(new Exception("You are not an authorized user!"));

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
                client.setPlayer(guest);
            }
            return new AddGuestResponse("Guest added", guest.getPlayerId());
        }
    }

    @Override
    public PlayerTurnResponse takeTurn(String playerID, String authToken) {
        if (getProxy(authToken) == null) return new PlayerTurnResponse(new Exception("You are not an authorized user!"));

        Game game = clientsInAGame.get(getProxy(authToken));
        if (game == null) return new PlayerTurnResponse(new Exception("Game does not exist."));
        else {
            // Update server model (COMING SOON IN THE NEXT PHASE)

            // Update relevant clients
            for (ClientProxy client : getClientsInGame(game.getGameId())) {
                client.addToGameHistory(AllUsers.getInstance().getUsername(authToken) + " ended their turn.");
                // The current client will receive a player turn response instead of this command.
                if (!client.getAuthToken().equals(authToken)) client.endCurrentTurn();
            }

            return new PlayerTurnResponse();
        }
    }

    @Override
    public AddToChatResponse addToChat(String message, String authToken) {
        ClientProxy client = getProxy(authToken);
        if (client == null) return new AddToChatResponse(new Exception("You are not an authorized user!"));

        Game game = clientsInAGame.get(client);
        if (game == null) return new AddToChatResponse(new Exception("Game does not exist."));

        // Update server model
        game.addToChat(message);

        // Update relevant clients
        for (ClientProxy clientInGame : getClientsInGame(game.getGameId())) {
            clientInGame.addChatMessage(message);
        }

        return new AddToChatResponse();
    }

    @Override
    public ClientUpdate updateClient(String lastReceivedCommandID, String authToken) {
        ClientProxy client = getProxy(authToken);
        Queue<Command> commands = client.getUnprocessedCommands();
        Map<Command, String> commandIDs = client.getCommandIDs();

        // Remove commands until the last received command
        while ((lastReceivedCommandID != null) &&
                (commands.peek() != null) &&
                (!commandIDs.get(commands.peek()).equals(lastReceivedCommandID))) {
            commandIDs.remove(commands.peek());
            commands.remove();
        }

        // Update the client proxy
        client.setCommandIDs(commandIDs);
        client.setUnprocessedCommands(commands);

        if (commands.peek() == null) return new ClientUpdate(null, lastReceivedCommandID);
        else {
            Command lastCommand = (Command) commands.toArray()[commands.toArray().length - 1];
            String lastID = commandIDs.get(lastCommand);
            return new ClientUpdate(commands, lastID);
        }
    }

    // PRIVATE HELPER METHODS------------------------------------------------------------------------------------------

    private boolean lobbyNameExists(String lobbyName) {
        for (Lobby lobby : AllLobbies.getInstance().getAllLobbies()) {
            if (lobby.getName().equals(lobbyName)) return true;
        }
        return false;
    }

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

    private List<ClientProxy> getClientsInGame(String gameID) {
        Game game = AllGames.getInstance().getGame(gameID);
        List<ClientProxy> result = new ArrayList<>();

        for (Map.Entry<ClientProxy, Game> entry : clientsInAGame.entrySet()) {
            if (entry.getValue().equals(game)) result.add(entry.getKey());
        }
        return result;
    }
}
