package tickets.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;

import tickets.common.ChoiceDestinationCards;
import tickets.common.Command;
import tickets.common.DestinationCard;
import tickets.common.Game;
import tickets.common.HandTrainCard;
import tickets.common.IServer;
import tickets.common.Lobby;
import tickets.common.Player;
import tickets.common.PlayerColor;
import tickets.common.PlayerInfo;
import tickets.common.PlayerSummary;
import tickets.common.Route;
import tickets.common.RouteColors;
import tickets.common.TrainCard;
import tickets.common.UserData;
import tickets.common.response.AddToChatResponse;
import tickets.common.response.ClaimRouteResponse;
import tickets.common.response.ClientUpdate;
import tickets.common.response.DestinationCardResponse;
import tickets.common.response.JoinLobbyResponse;
import tickets.common.response.LeaveLobbyResponse;
import tickets.common.response.LoginResponse;
import tickets.common.response.LogoutResponse;
import tickets.common.response.Response;
import tickets.common.response.StartGameResponse;
import tickets.common.response.TrainCardResponse;
import tickets.server.model.AllGames;
import tickets.server.model.AllLobbies;
import tickets.server.model.AllUsers;
import tickets.server.model.game.ServerGame;
import tickets.server.model.game.ServerPlayer;

public class ServerFacade implements IServer {

    private List<ClientProxy> clientsInLobbyList;
    private Map<ClientProxy, Lobby> clientsInALobby;
    private Map<ClientProxy, ServerGame> clientsInAGame;

    //----------------------------------------------------------------------------------------------
    // *** SINGLETON PATTERN ***

    private static ServerFacade INSTANCE = null;

    public static ServerFacade getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ServerFacade();
        }
        return INSTANCE;
    }

    private ServerFacade() {
        clientsInLobbyList = new ArrayList<>();
        clientsInALobby = new HashMap<>();
        clientsInAGame = new HashMap<>();
    }

    //----------------------------------------------------------------------------------------------
    // *** LOGIN/REGISTER COMMANDS ***

    @Override
    public LoginResponse login(UserData userData) {
        if (AllUsers.getInstance().verifyLogin(userData.getUsername(), userData.getPassword())){
            String authToken = AllUsers.getInstance().addUser(userData);
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
        else {
            String authToken = AllUsers.getInstance().addUser(userData);
            clientsInLobbyList.add(new ClientProxy(authToken));
            return new LoginResponse("Welcome, " + userData.getUsername(), authToken, AllLobbies.getInstance().getAllLobbies());
        }
    }

    //----------------------------------------------------------------------------------------------
    // *** LOBBY LIST COMMANDS ***

    @Override
    public JoinLobbyResponse joinLobby(String lobbyID, String authToken) {
        if (getProxy(authToken) == null) return new JoinLobbyResponse(new Exception("You are not an authorized user!"));

        Lobby lobby = AllLobbies.getInstance().getLobby(lobbyID);
        if (lobby == null) return new JoinLobbyResponse(new Exception("Lobby does not exist."));
        else if (lobby.getCurrentMembers() == lobby.getMaxMembers()) return new JoinLobbyResponse(new Exception("Lobby is full."));
        else {
            // Update the server model
            Player player = new Player(UUID.randomUUID().toString(), authToken);
            player.setName(AllUsers.getInstance().getUsername(authToken));
            lobby.addPlayer(player);
            lobby.assignFaction(player);

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
                client.addPlayerToLobbyInList(lobby, player);
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
        player.setName(AllUsers.getInstance().getUsername(authToken));
        lobby.addToHistory(AllUsers.getInstance().getUsername(authToken) +
                " has created the lobby.");
        lobby.addPlayer(player);
        lobby.assignFaction(player);

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

    //----------------------------------------------------------------------------------------------
    // *** LOBBY COMMANDS ***

    @Override
    public StartGameResponse startGame(String lobbyID, String authToken) {
        if (getProxy(authToken) == null) return new StartGameResponse(new Exception("You are not an authorized user!"));

        Lobby lobby = AllLobbies.getInstance().getLobby(lobbyID);
        if (lobby == null) return new StartGameResponse(new Exception("Lobby does not exist."));
        else {
            // Update server model
            List<Player> playersInLobby = lobby.getPlayers();
            ServerGame game = new ServerGame(UUID.randomUUID().toString(), playersInLobby);
            Game clientGame = new Game(game.getGameId());
            List<TrainCard> trainCards = game.getFaceUpCards();
            clientGame.setFaceUpCards(trainCards);
            for (Player player : playersInLobby) {
                PlayerInfo info = player.getInfo();
                clientGame.addPlayer(info);
            }
            AllGames.getInstance().addGame(game);

            // Update relevant clients and move clients from lobby to game
            for (ClientProxy client : getClientsInLobby(lobbyID)) {
                // The current client will receive a start game response instead of this command.
                if (!client.getAuthToken().equals(authToken)) {
                    ServerPlayer player = game.getServerPlayer(client.getAuthToken());
                    HandTrainCard playerHand = player.getHandTrainCards();
                    ChoiceDestinationCards destinationCards = new ChoiceDestinationCards();
                    destinationCards.setDestinationCards(player.getDestinationCardOptions());
                    client.startGame(clientGame, playerHand, destinationCards);
                }

                clientsInALobby.remove(client);
                clientsInAGame.put(client, game);
            }
            for (ClientProxy client: clientsInLobbyList) {
                client.removeLobbyFromList(lobby);
            }

            AllLobbies.getInstance().removeLobby(lobbyID);

            ServerPlayer player = game.getServerPlayer(authToken);
            HandTrainCard playerHand = player.getHandTrainCards();
            ChoiceDestinationCards destinationCards = new ChoiceDestinationCards();
            destinationCards.setDestinationCards(player.getDestinationCardOptions());
            return new StartGameResponse(clientGame, playerHand, destinationCards);
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
                    client.removePlayerFromLobbyInList(lobby, player);
                }
                for (ClientProxy client : clientsInLobbyList) {
                    client.removePlayerFromLobbyInList(lobby, player);
                }
            }
            return new LeaveLobbyResponse("You have left the lobby.", AllLobbies.getInstance().getAllLobbies());
        }
    }

    //----------------------------------------------------------------------------------------------
    // *** IN-GAME COMMANDS ***

    @Override
    public AddToChatResponse addToChat(String message, String authToken) {
        ServerGame game;
        try {
            game = getGameForToken(authToken);
        }
        catch(Exception ex) {
            return new AddToChatResponse(ex);
        }
        ServerPlayer player = game.getServerPlayer(authToken);
        String fullMessage = player.getName() + " (" + player.getPlayerFaction().getName() + "): " +
                message;

        // Update server model
        game.addToChat(fullMessage);

        // Update relevant clients
        for (ClientProxy clientInGame : getClientsInGame(game.getGameId())) {
            clientInGame.addChatMessage(fullMessage);
        }

        return new AddToChatResponse();
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // *** In-Game Player Turn Actions ***

    // TODO: Question: do we want game history messages to say player's username, faction, or both?

    @Override
    public TrainCardResponse drawTrainCard(String authToken) {
        try {
            ServerGame game = getGameForToken(authToken);

            // Any reason for failing here will be thrown as an exception
            TrainCard drawnCard = game.drawTrainCard(authToken);

            // Update game history
            String historyMessage = AllUsers.getInstance().getUsername(authToken) + " drew a resource card.";
            game.addToHistory(historyMessage);

            // Update other clients in the game
            for (ClientProxy client : getClientsInGame(game.getGameId())) {
                client.addToGameHistory(historyMessage);
                // The current client will receive a train card response rather than this command.
                if (!client.getAuthToken().equals(authToken))
                    client.addPlayerTrainCard();
            }
            return new TrainCardResponse(drawnCard);
        }
        catch(Exception ex) {
            return new TrainCardResponse(ex);
        }
    }

    @Override
    public TrainCardResponse drawFaceUpCard(int position, String authToken) {
        try {
            ServerGame game = getGameForToken(authToken);

            // Any reason for failing here will be thrown as an exception
            TrainCard drawnCard = game.drawFaceUpCard(position, authToken);
            TrainCard newCard = game.getFaceUpCards().get(position);

            // Update game history
            String historyMessage = AllUsers.getInstance().getUsername(authToken) + " drew a " +
                    drawnCard.getColor().toString() + " face-up resource card.";
            game.addToHistory(historyMessage);

            // Update other clients in the game
            for (ClientProxy client : getClientsInGame(game.getGameId())) {
                client.addToGameHistory(historyMessage);
                client.replaceFaceUpCard(position, newCard);
                // The current client will receive a train card response rather than this command.
                if (!client.getAuthToken().equals(authToken))
                    client.addPlayerTrainCard();
            }
            return new TrainCardResponse(drawnCard);
        }
        catch(Exception ex) {
            return new TrainCardResponse(ex);
        }
    }

    @Override
    public ClaimRouteResponse claimRoute(Route route, List<TrainCard> cards, String authToken) {
        try {
            ServerGame game = getGameForToken(authToken);

            // Any reason for failing here will be thrown as an exception
            game.claimRoute(route, cards, authToken);

            //update game history
            String historyMessage = AllUsers.getInstance().getUsername(authToken) +
                    " claimed the route " + route.toString();
            game.addToHistory(historyMessage);

            // Get color of route to be claimed from train card color
            RouteColors color = null;
            for (TrainCard card : cards) {
                if (card.getColor() != RouteColors.Wild) {
                    if (color == null) color = card.getColor();
                }
            }

            //update other clients in the game
            for (ClientProxy client : getClientsInGame(game.getGameId())) {
                client.addToGameHistory(historyMessage);
                // The current client will receive a train card response rather than this command.
                if (!client.getAuthToken().equals(authToken)) {
                    client.addClaimedRoute(route, color, game.getPlayerColor(authToken));
                }
            }

            // Make return map
            Map<RouteColors, Integer> removeCards = new HashMap<>();
            for (TrainCard card : cards) {
                RouteColors cardColor = card.getColor();
                if (! removeCards.keySet().contains(cardColor)) {
                    removeCards.put(cardColor, 0);
                }
                removeCards.put(cardColor, removeCards.get(cardColor) + 1);
            }
            return new ClaimRouteResponse(removeCards); // "Route claimed successfully"
        }
        catch(Exception ex) {
            return new ClaimRouteResponse(ex);
        }
    }

    @Override
    public DestinationCardResponse drawDestinationCards(String authToken) {
        try {
            ServerGame game = getGameForToken(authToken);

            // Any reason for failing here will be thrown as an exception
            List<DestinationCard> drawnCards = game.drawDestinationCards(authToken);

            //update game history
            String historyMessage = AllUsers.getInstance().getUsername(authToken) +
                    " drew destination cards.";
            game.addToHistory(historyMessage);

            //update other game members
            for (ClientProxy client : getClientsInGame(game.getGameId())) {
                client.addToGameHistory(historyMessage);
                // The current client will receive a destination card response rather than this command.
                if (!client.getAuthToken().equals(authToken)) {
                    client.addPlayerDestinationCards(drawnCards.size());
                }
            }

            return new DestinationCardResponse(drawnCards);
        }
        catch(Exception ex) {
            return new DestinationCardResponse(ex);
        }
    }

    @Override
    public DestinationCardResponse discardDestinationCard(DestinationCard discard, String authToken) {
        try {
            ServerGame game = getGameForToken(authToken);

            // Any reason for failing here will be thrown as an exception
            List<DestinationCard> keptCards = game.discardDestinationCard(discard, authToken);

            if (discard != null) {
                //update game history
                String historyMessage = AllUsers.getInstance().getUsername(authToken) +
                        " discarded a destination card.";
                game.addToHistory(historyMessage);

                //update other game members
                for (ClientProxy client : getClientsInGame(game.getGameId())) {
                    client.addToGameHistory(historyMessage);
                    // The current client will receive a response rather than this command.
                    if (!client.getAuthToken().equals(authToken)) {
                        client.removePlayerDestinationCard();
                    }
                }
            }

            return new DestinationCardResponse(keptCards);
        }
        catch(Exception ex) {
            return new DestinationCardResponse(ex);
        }
    }

    //----------------------------------------------------------------------------------------------
    // *** CALLED FROM IN-GAME ***

    public void endTurn(ServerGame game, String playerName) {
        // Update game history
        String historyMessage = playerName + " ended their turn.";
        game.addToHistory(historyMessage);

        // Update other clients in the game
        for (ClientProxy client : getClientsInGame(game.getGameId())) {
            client.addToGameHistory(historyMessage);
            client.endCurrentTurn();
        }
    }

    public void endGame(ServerGame game) {
        List<PlayerSummary> playerSummaries = new ArrayList<>();
        List<PlayerColor> longestPathPlayers = game.getPlayersWithLongestPath();
        for (ServerPlayer player : game.getServerPlayers()) {
            if (longestPathPlayers.contains(player.getInfo().getFaction().getColor())) {
                playerSummaries.add(player.calculateSummary(true));
            }
            else playerSummaries.add(player.calculateSummary(false));
        }

        // Send command to all clients in game
        for (ClientProxy client : getClientsInGame(game.getGameId())) {
            client.displayEndGame(playerSummaries);
        }
    }
    //----------------------------------------------------------------------------------------------
    // *** UPDATE CLIENT COMMAND FOR POLLER ***

    @Override
    public ClientUpdate updateClient(String lastReceivedCommandID, String authToken) {
        ClientProxy client = getProxy(authToken);
        Queue<Command> commands = client.getUnprocessedCommands();
        Map<Command, String> commandIDs = client.getCommandIDs();

        // Remove commands until the last received command
        Command command = commands.peek();
        String ID = commandIDs.get(command);
        while ((lastReceivedCommandID != null) &&
                (command != null) &&
                Double.parseDouble(ID) <= Double.parseDouble(lastReceivedCommandID)) {
            commands.remove();
            commandIDs.remove(command);

            command = commands.peek();
            ID = commandIDs.get(command);
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

    //----------------------------------------------------------------------------------------------
    // *** PRIVATE HELPER METHODS ***

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

        for (Map.Entry<ClientProxy, ServerGame> entry : clientsInAGame.entrySet()) {
            if (entry.getValue().equals(game)) result.add(entry.getKey());
        }
        return result;
    }

    private ServerGame getGameForToken(String authToken) throws Exception {
        ClientProxy client = getProxy(authToken);
        if (client == null) {
            throw new Exception("You are not an authorized user!");
        }

        ServerGame game = clientsInAGame.get(client);
        if (game == null) {
            throw new Exception("Game does not exist.");
        } 
        return game;
    }
}
