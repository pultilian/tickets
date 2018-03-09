package tickets.server;

import tickets.common.*;
import tickets.common.DestinationCard;
import tickets.server.model.AllGames;
import tickets.server.model.AllLobbies;
import tickets.common.response.*;
import tickets.server.model.AllUsers;
import tickets.server.model.game.ServerGame;
import tickets.common.Route;
import tickets.server.model.game.ServerPlayer;

import java.util.*;

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
        lobby.addPlayer(player);
        lobby.assignFaction(player);
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
            clientGame.setFaceUpCards(game.getFaceUpCards());
            for (Player player : playersInLobby) {
                PlayerInfo info = new PlayerInfo();
                info.setFaction(player.getPlayerFaction());
                clientGame.addPlayer(player.getPlayerId(), info);
            }
            AllGames.getInstance().addGame(game);

            // Update relevant clients and move clients from lobby to game
            for (ClientProxy client : getClientsInLobby(lobbyID)) {
                // The current client will receive a start game response instead of this command.
                if (!client.getAuthToken().equals(authToken)) {
                    HandTrainCard playerHand = new HandTrainCard();
                    List<DestinationCard> initialDestinationCards = new ArrayList<>();
                    for (int i = 0; i < 4; i++) {
                        playerHand.addCard(game.drawTrainCard());
                    }
                    for (int i = 0; i < 3; i++) {
                        initialDestinationCards.add(game.drawDestinationCard());
                    }
                    ChoiceDestinationCards destinationCards = new ChoiceDestinationCards();
                    destinationCards.setDestinationCards(initialDestinationCards);
                    client.startGame(clientGame, playerHand, destinationCards);
                }
                clientsInALobby.remove(client);
                clientsInAGame.put(client, game);
            }
            for (ClientProxy client: clientsInLobbyList) {
                client.removeLobbyFromList(lobby);
            }

            AllLobbies.getInstance().removeLobby(lobbyID);

            HandTrainCard playerHand = new HandTrainCard();
            List<DestinationCard> initialDestinationCards = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                playerHand.addCard(game.drawTrainCard());
            }
            for (int i = 0; i < 3; i++) {
                initialDestinationCards.add(game.drawDestinationCard());
            }
            ChoiceDestinationCards destinationCards = new ChoiceDestinationCards();
            destinationCards.setDestinationCards(initialDestinationCards);
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
                lobby.addToHistory(AllUsers.getInstance().getUsername(authToken) + " has left the lobby.");
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

        // Update server model
        game.addToChat(message);

        // Update relevant clients
        for (ClientProxy clientInGame : getClientsInGame(game.getGameId())) {
            clientInGame.addChatMessage(message);
        }

        return new AddToChatResponse();
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // *** In-Game Player Turn Actions ***

    // Players take turns by calling action-specific methods
    //   - drawTrainCard
    //   - drawFaceUpCard
    //   - claimRoute
    //   - drawDestinationCards
    //   - discardDestinationCard
    //   - addToChat
    //   - endTurn
    //
    // Each ServerPlayer tracks its progression through the actions available to it during its turn
    // The ServerGame object owns the players playing the game, and makes sure that only one player
    // can be in a current turn state at a time

    @Override
    public TrainCardResponse drawTrainCard(String authToken) {
        ServerGame game;
        try {
            game = getGameForToken(authToken);
            ServerPlayer player = game.getServerPlayer(authToken);
            TrainCard drawnCard = player.takeAction_drawTrainCard();

            //update game history
            String historyMessage = AllUsers.getInstance().getUsername(authToken) + " drew a resource card.";
            game.addToHistory(historyMessage);

            //update other clients in the game
            for (ClientProxy client : getClientsInGame(game.getGameId())) {
                client.addToGameHistory(historyMessage);
                // The current client will receive a train card response rather than this command.
                if (!client.getAuthToken().equals(authToken))
                    client.addPlayerTrainCard(game.getPlayerID(authToken));
            }
            return new TrainCardResponse(drawnCard);
        }
        catch(Exception ex) {
            return new TrainCardResponse(ex);
        }
    }

    @Override
    public TrainCardResponse drawFaceUpCard(int position, String authToken) {
        ServerGame game;
        try {
            game = getGameForToken(authToken);
            ServerPlayer player = game.getServerPlayer(authToken);
            TrainCard drawnCard = player.takeAction_drawFaceUpCard(position);

            //update game history
            String historyMessage = AllUsers.getInstance().getUsername(authToken) +
                    " drew a " + drawnCard.getColor().toString() + " face-up resource card.";
            game.addToHistory(historyMessage);

            //update other clients in the game
            for (ClientProxy client : getClientsInGame(game.getGameId())) {
                client.addToGameHistory(historyMessage);
                // The current client will receive a train card response rather than this command.
                if (!client.getAuthToken().equals(authToken))
                    client.addPlayerTrainCard(game.getPlayerID(authToken));
            }

            return new TrainCardResponse(drawnCard);
        }
        catch(Exception ex) {
            return new TrainCardResponse(ex);
        }
    }

    @Override
    public Response claimRoute(Route route, String authToken) {
        try {
            ServerGame game = getGameForToken(authToken);
            ServerPlayer player = game.getServerPlayer(authToken);
            player.takeAction_claimRoute(route);

            //update game history
            String historyMessage = AllUsers.getInstance().getUsername(authToken) +
                    " has claimed the route " + route.toString();
            game.addToHistory(historyMessage);

            //update other clients in the game
            for (ClientProxy client : getClientsInGame(game.getGameId())) {
                client.addToGameHistory(historyMessage);
                // The current client will receive a train card response rather than this command.
                if (!client.getAuthToken().equals(authToken)) {
                    // client.addClaimedRoute(game.getPlayerID(authToken), route);
                }
            }

            return new Response(); // "Route claimed successfully"
        }
        catch(Exception ex) {
            return new Response(ex);
        }
    }

    @Override
    public DestinationCardResponse drawDestinationCards(String authToken) {
        try {
            ServerGame game = getGameForToken(authToken);
            ServerPlayer player = game.getServerPlayer(authToken);
            List<DestinationCard> drawnCards = player.takeAction_drawDestinationCards();

            //update game history
            String historyMessage = AllUsers.getInstance().getUsername(authToken) +
                    " has drawn two destination cards.";
            game.addToHistory(historyMessage);

            //update other game members
            for (ClientProxy client : getClientsInGame(game.getGameId())) {
                client.addToGameHistory(historyMessage);
                // The current client will receive a destination card response rather than this command.
                if (!client.getAuthToken().equals(authToken)) {
                    // client.addPlayerDestinationCard(game.getPlayerID(authToken));
                    // client.addPlayerDestinationCard(game.getPlayerID(authToken));
                }
            }

            List<DestinationCard> cards = new ArrayList<>();
            cards.add(drawnCards.get(0));
            cards.add(drawnCards.get(1));
            cards.add(drawnCards.get(2));
            return new DestinationCardResponse(cards);
        }
        catch(Exception ex) {
            return new DestinationCardResponse(ex);
        }
    }

    @Override
    public Response discardDestinationCard(DestinationCard discard, String authToken) {
        try {
            ServerGame game = getGameForToken(authToken);
            ServerPlayer player = game.getServerPlayer(authToken);
            player.takeAction_discardDestinationCard(discard);

            //update game history
            String historyMessage = AllUsers.getInstance().getUsername(authToken) +
                    " has discarded a destination card.";
            game.addToHistory(historyMessage);

            //update other game members
            for (ClientProxy client : getClientsInGame(game.getGameId())) {
                client.addToGameHistory(historyMessage);
                // The current client will receive a destination card response rather than this command.
                if (!client.getAuthToken().equals(authToken)) {
                    // client.removePlayerDestinationCard(game.getPlayerID(authToken));
                }
            }

            return new Response(); // "Destination successfully discarded"
        }
        catch(Exception ex) {
            return new Response(ex);
        }
    }

    @Override
    public Response endTurn(String authToken) {
        try {
            ServerGame game = getGameForToken(authToken);
            ServerPlayer player = game.getServerPlayer(authToken);
            player.takeAction_endTurn();

            //update game history
            String historyMessage = AllUsers.getInstance().getUsername(authToken) +
                    " has ended their turn.";
            game.addToHistory(historyMessage);

            //update other clients in the game
            for (ClientProxy client : getClientsInGame(game.getGameId())) {
                client.addToGameHistory(historyMessage);
                if (!client.getAuthToken().equals(authToken)) {
                    // client.endCurrentTurn();
                }
            }

            return new Response(); // "Turn successfully ended"
        }
        catch(Exception ex) {
            return new Response(ex);
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
