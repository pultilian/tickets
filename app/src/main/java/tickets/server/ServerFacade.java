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
import tickets.common.GameSummary;
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
import tickets.common.TrainCardWrapper;
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
import tickets.common.response.ResumeGameResponse;
import tickets.common.response.ResumeLobbyResponse;
import tickets.common.response.StartGameResponse;
import tickets.common.response.TrainCardResponse;
import tickets.server.dataaccess.DAOFacade;
import tickets.server.model.AllGames;
import tickets.server.model.AllLobbies;
import tickets.server.model.AllUsers;
import tickets.server.model.game.ServerGame;
import tickets.server.model.game.ServerPlayer;

public class ServerFacade implements IServer {

    private List<ClientProxy> clientsInLobbyList;
    private Map<ClientProxy, Lobby> clientsInALobby;
    private Map<ClientProxy, ServerGame> clientsInAGame;

    private int numGameDeltas;
    private int numLobbyDeltas;
    private int deltasBetweenCheckpoints;

    private DAOFacade daoFacade;

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
        numGameDeltas = 0;
        numLobbyDeltas = 0;
        deltasBetweenCheckpoints = 0;
    }

    //----------------------------------------------------------------------------------------------
    // *** STARTUP ***

    public void startup(String persistenceType, int numCommands, boolean wipeDatabase) {
        if (daoFacade != null) {
            return;
        }
        try {
            daoFacade = new DAOFacade(persistenceType);
            deltasBetweenCheckpoints = numCommands;

            if (wipeDatabase) {
                daoFacade.deleteAll();
            }
            loadUsers();
            loadLobbies();
            loadGames();
            fillMaps();
        }
        catch (Exception ex) {
            System.out.println("An error occurred while starting up server plugin");
        }
        return;
    }

    private void loadUsers() throws Exception {
        List<UserData> users = daoFacade.getUsers();
        for (UserData u : users) {
            AllUsers.getInstance().addUser(u);
        }
        return;
    }

    private void loadLobbies() throws Exception {
        List<Lobby> lobbies = daoFacade.getLobbies();
        for (Lobby l : lobbies) {
            // Process all deltas
            List<String> IDs = new ArrayList<>();
            IDs.add(l.getId());
            List<Command> commands = daoFacade.getDeltas("lobby", IDs);
            for (Command command : commands) {
                command.execute(l);
            }

            // Add up-to-date lobby to server model
            AllLobbies.getInstance().addLobby(l);
        }
        return;
    }

    private void loadGames() throws Exception {
        List<ServerGame> games = daoFacade.getGames();
        for (ServerGame g : games) {
            // Process all deltas
            List<String> IDs = new ArrayList<>();
            IDs.add(g.getGameId());
            List<Command> commands = daoFacade.getDeltas("game", IDs);
            for (Command command : commands) {
                command.execute(g);
            }

            // Add up-to-date game to server model
            AllGames.getInstance().addGame(g);
        }
        return;
    }

    private void fillMaps() {
        List<UserData> users = AllUsers.getInstance().getUsers();
        for (UserData u : users) {
            List<ServerGame> userGames = AllGames.getInstance().getServerGamesWithUser(u.getUsername());
            List<Lobby> userLobbies = AllLobbies.getInstance().getLobbiesWithUser(u.getUsername());
            if (userGames.size() > 0) {
                clientsInAGame.put(new ClientProxy(u.getAuthenticationToken()), userGames.get(0));
            }
            else if (userLobbies.size() > 0) {
                clientsInALobby.put(new ClientProxy(u.getAuthenticationToken()), userLobbies.get(0));
            }
            else {
                clientsInLobbyList.add(new ClientProxy(u.getAuthenticationToken()));
            }
        }
        return;
    }

    //----------------------------------------------------------------------------------------------
    // *** LOGIN/REGISTER COMMANDS ***

    @Override
    public LoginResponse login(UserData userData) {
        if (AllUsers.getInstance().verifyLogin(userData.getUsername(), userData.getPassword())){
            String authToken = AllUsers.getInstance().addUser(userData);
            clientsInLobbyList.add(new ClientProxy(authToken));
            List<Lobby> allLobbies = new ArrayList<>(AllLobbies.getInstance().getAllLobbies());
            List<Lobby> currentLobbies = AllLobbies.getInstance().getLobbiesWithUser(userData.getUsername());
            // Remove lobbies that this user has already joined from the available lobbies list.
            for (Lobby lobby : currentLobbies) {
                allLobbies.remove(lobby);
            }

            // Add this user to the database
            List<UserData> user = new ArrayList<>();
            user.add(userData);
            try {
                daoFacade.addUsers(user);
            } catch (Exception e) {
                return new LoginResponse(new Exception("Server error."));
            }

            LoginResponse response = new LoginResponse(
                    "Welcome, " + userData.getUsername(), authToken, allLobbies);
            response.setCurrentLobbies(currentLobbies);
            response.setCurrentGames(AllGames.getInstance().getGamesWithUser(userData.getUsername()));
            return response;
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

            // Add this user to the database
            List<UserData> user = new ArrayList<>();
            user.add(userData);
            try {
                daoFacade.addUsers(user);
            } catch (Exception e) {
                return new LoginResponse(new Exception("Server error."));
            }

            LoginResponse response = new LoginResponse(
                    "Welcome, " + userData.getUsername(), authToken, AllLobbies.getInstance().getAllLobbies());
            response.setCurrentLobbies(AllLobbies.getInstance().getLobbiesWithUser(userData.getUsername()));
            response.setCurrentGames(AllGames.getInstance().getGamesWithUser(userData.getUsername()));
            return response;
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
            Player player = new Player(authToken);
            player.setName(AllUsers.getInstance().getUsername(authToken));
            lobby.addPlayer(player);
            lobby.assignFaction(player);

            // Add command or checkpoint to database
            if (numLobbyDeltas < deltasBetweenCheckpoints) {
                // Add a command
                Command command = new Command("addPlayer",
                        new String[]{Player.class.getName()},
                        new Object[]{player});
                List<String> additionalInfo = new ArrayList<>();
                additionalInfo.add(lobbyID);
                try {
                    daoFacade.addDelta(command, "lobby", additionalInfo);
                    numLobbyDeltas++;
                } catch (Exception e) {
                    return new JoinLobbyResponse(new Exception("Server database error."));
                }
            }
            else {
                // Remove all lobby deltas and replace lobbies
                try {
                    lobbyCheckpoint();
                } catch (Exception e) {
                    return new JoinLobbyResponse(new Exception("Server database error."));
                }
            }

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
        Player player = new Player(authToken);
        player.setName(AllUsers.getInstance().getUsername(authToken));
        lobby.addToHistory(AllUsers.getInstance().getUsername(authToken) +
                " has created the lobby.");
        lobby.addPlayer(player);
        lobby.assignFaction(player);

        // Add the lobby to the database
        List<Lobby> DBlobby = new ArrayList<>();
        DBlobby.add(lobby);
        try {
            daoFacade.addLobbies(DBlobby);
        } catch (Exception e) {
            return new JoinLobbyResponse(new Exception("Server error."));
        }

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
    public ResumeLobbyResponse resumeLobby(String lobbyID, String authToken) {
        if (getProxy(authToken) == null) return new ResumeLobbyResponse(new Exception("You are not an authorized user!"));

        Lobby lobby = AllLobbies.getInstance().getLobby(lobbyID);
        if (lobby == null) return new ResumeLobbyResponse(new Exception("Lobby does not exist."));

        // Remove current client (no need to modify server model or update other clients)
        String name = AllUsers.getInstance().getUsername(authToken);
        Player resumePlayer = lobby.getPlayerWithName(name);
        String resumeAuthToken = resumePlayer.getAssociatedAuthToken();
        clientsInLobbyList.remove(getProxy(authToken));
        ClientProxy resumeClient = getProxy(resumeAuthToken);
        if (resumeClient == null) {
            resumeClient = new ClientProxy(resumeAuthToken);
            clientsInALobby.put(resumeClient, lobby);
        }
        resumeClient.clearCommands();

        return new ResumeLobbyResponse(lobby, resumePlayer, resumeAuthToken);
    }

    @Override
    public ResumeGameResponse resumeGame(String gameID, String authToken) {
        if (getProxy(authToken) == null) return new ResumeGameResponse(new Exception("You are not an authorized user!"));

        ServerGame game = AllGames.getInstance().getGame(gameID);
        if (game == null) return new ResumeGameResponse(new Exception("Game does not exist."));

        // Remove current client (no need to modify server model or update other clients)
        String name = AllUsers.getInstance().getUsername(authToken);
        Player resumePlayer = game.getPlayerWithName(name);
        String resumeAuthToken = resumePlayer.getAssociatedAuthToken();
        clientsInLobbyList.remove(getProxy(authToken));
        ClientProxy resumeClient = getProxy(resumeAuthToken);
        if (resumeClient == null) {
            resumeClient = new ClientProxy(resumeAuthToken);
            clientsInAGame.put(resumeClient, game);
        }
        resumeClient.clearCommands();

        return new ResumeGameResponse(game.getClientGame(), resumePlayer, resumeAuthToken);
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
            ServerGame game = new ServerGame(lobbyID, lobby.getName(), playersInLobby);
            Game clientGame = new Game(game.getGameId(), game.getName());
            List<TrainCard> trainCards = game.getFaceUpCards();
            clientGame.setFaceUpCards(trainCards);
            for (Player player : playersInLobby) {
                PlayerInfo info = player.getInfo();
                clientGame.addPlayer(info);
            }
            AllGames.getInstance().addGame(game);

            // Add the game and players to the database
            List<ServerGame> DBGame = new ArrayList<>();
            DBGame.add(game);
            try {
                daoFacade.removeLobby(lobbyID);
                daoFacade.addGames(DBGame);
                daoFacade.addPlayers(game.getServerPlayers());
            } catch (Exception e) {
                return new StartGameResponse(new Exception("Server error."));
            }

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
                // The current client will receive the updated lobby list instead of this command.
                for (ClientProxy client : clientsInLobbyList) {
                    if (!client.getAuthToken().equals(authToken)) client.removePlayerFromLobbyInList(lobby, player);
                }

                // Add command or checkpoint to database
                if (numLobbyDeltas < deltasBetweenCheckpoints) {
                    // Add a command
                    Command command = new Command("removePlayer",
                            new String[]{Player.class.getName()},
                            new Object[]{player});
                    List<String> additionalInfo = new ArrayList<>();
                    additionalInfo.add(lobbyID);
                    try {
                        daoFacade.addDelta(command, "lobby", additionalInfo);
                        numLobbyDeltas++;
                    } catch (Exception e) {
                        return new LeaveLobbyResponse(new Exception("Server database error."));
                    }
                }
                else {
                    // Remove all lobby deltas and replace lobbies
                    try {
                        lobbyCheckpoint();
                    } catch (Exception e) {
                        return new LeaveLobbyResponse(new Exception("Server database error."));
                    }
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

        // Add command or checkpoint to database
        if (numGameDeltas < deltasBetweenCheckpoints) {
            // Add a command
            Command command = new Command("addToChat",
                    new String[]{String.class.getName()},
                    new Object[]{fullMessage});
            List<String> additionalInfo = new ArrayList<>();
            additionalInfo.add(game.getGameId());
            try {
                daoFacade.addDelta(command, "game", additionalInfo);
                numGameDeltas++;
            } catch (Exception e) {
                return new AddToChatResponse(new Exception("Server database error."));
            }
        }
        else {
            // Remove all game deltas and replace games
            try {
                gameCheckpoint();
            } catch (Exception e) {
                return new AddToChatResponse(new Exception("Server database error."));
            }
        }

        // Update relevant clients
        for (ClientProxy clientInGame : getClientsInGame(game.getGameId())) {
            clientInGame.addChatMessage(fullMessage);
        }

        return new AddToChatResponse();
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // *** In-Game Player Turn Actions ***

    @Override
    public TrainCardResponse drawTrainCard(String authToken) {
        try {
            ServerGame game = getGameForToken(authToken);

            ServerPlayer currentPlayer = game.getCurrentPlayer();
            // Any reason for failing here will be thrown as an exception
            TrainCard drawnCard = game.drawTrainCard(authToken);

            // Add command or checkpoint to database
            if (numGameDeltas < deltasBetweenCheckpoints) {
                // Add a command
                Command command = new Command("drawTrainCard",
                        new String[]{String.class.getName()},
                        new Object[]{authToken});
                List<String> additionalInfo = new ArrayList<>();
                additionalInfo.add(game.getGameId());
                try {
                    daoFacade.addDelta(command, "game", additionalInfo);
                    numGameDeltas++;
                } catch (Exception e) {
                    return new TrainCardResponse(new Exception("Server database error."));
                }
            }
            else {
                // Remove all game deltas and replace games
                try {
                    gameCheckpoint();
                } catch (Exception e) {
                    return new TrainCardResponse(new Exception("Server database error."));
                }
            }

            // Update game history
            String historyMessage = currentPlayer.getName() + " drew a resource card.";
            game.addToHistory(historyMessage);

            try {
                addGameHistoryToDatabase(game, historyMessage);
            } catch (Exception e) {
                return new TrainCardResponse(new Exception("Server database error."));
            }

            // Update other clients in the game
            for (ClientProxy client : getClientsInGame(game.getGameId())) {
                client.addToGameHistory(historyMessage);
                // The current client will receive a train card response rather than this command.
                if (!client.getAuthToken().equals(authToken))
                    client.addPlayerTrainCard();
            }
            // If current player changed, add an end turn
            if (!game.getCurrentPlayer().equals(currentPlayer)) endTurn(game, currentPlayer.getName());
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

            ServerPlayer currentPlayer = game.getCurrentPlayer();
            // Any reason for failing here will be thrown as an exception
            TrainCard drawnCard = game.drawFaceUpCard(position, authToken);
            TrainCard newCard = game.getFaceUpCards().get(position);

            // Add command or checkpoint to database (replace face-up card)
            if (numGameDeltas < deltasBetweenCheckpoints) {
                // Add a command
                Command command = new Command("drawFaceUpCard",
                        new String[]{Integer.class.getName(), String.class.getName()},
                        new Object[]{position, authToken});
                List<String> additionalInfo = new ArrayList<>();
                additionalInfo.add(game.getGameId());
                try {
                    daoFacade.addDelta(command, "game", additionalInfo);
                    numGameDeltas++;
                } catch (Exception e) {
                    return new TrainCardResponse(new Exception("Server database error."));
                }
            }
            else {
                // Remove all game deltas and replace games
                try {
                    gameCheckpoint();
                } catch (Exception e) {
                    return new TrainCardResponse(new Exception("Server database error."));
                }
            }

            // Update game history
            String historyMessage = currentPlayer.getName() + " drew a " +
                    drawnCard.getColor().toString() + " face-up resource card.";
            game.addToHistory(historyMessage);

            try {
                addGameHistoryToDatabase(game, historyMessage);
            } catch (Exception e) {
                return new TrainCardResponse(new Exception("Server database error."));
            }

            // Update other clients in the game
            for (ClientProxy client : getClientsInGame(game.getGameId())) {
                client.addToGameHistory(historyMessage);
                client.replaceFaceUpCard(position, newCard);
                // The current client will receive a train card response rather than this command.
                if (!client.getAuthToken().equals(authToken))
                    client.addPlayerTrainCard();
            }
            // If current player changed, add an end turn
            if (!game.getCurrentPlayer().equals(currentPlayer)) endTurn(game, currentPlayer.getName());
            return new TrainCardResponse(drawnCard);
        }
        catch(Exception ex) {
            return new TrainCardResponse(ex);
        }
    }

    @Override
    public ClaimRouteResponse claimRoute(Route route, TrainCardWrapper cards, String authToken) {
        try {
            ServerGame game = getGameForToken(authToken);

            ServerPlayer currentPlayer = game.getCurrentPlayer();
            // Any reason for failing here will be thrown as an exception
            game.claimRoute(route, cards, authToken);

            // Add command or checkpoint to database
            if (numGameDeltas < deltasBetweenCheckpoints) {
                // Add a command
                Command command = new Command("claimRoute",
                        new String[]{Route.class.getName(), TrainCardWrapper.class.getName(), String.class.getName()},
                        new Object[]{route, cards, authToken});
                List<String> additionalInfo = new ArrayList<>();
                additionalInfo.add(game.getGameId());
                additionalInfo.add(currentPlayer.getName());
                try {
                    daoFacade.addDelta(command, "game", additionalInfo);
                    numGameDeltas++;
                } catch (Exception e) {
                    return new ClaimRouteResponse(new Exception("Server database error."));
                }
            }
            else {
                // Remove all game deltas and replace games
                try {
                    gameCheckpoint();
                } catch (Exception e) {
                    return new ClaimRouteResponse(new Exception("Server database error."));
                }
            }

            //update game history
            String historyMessage = currentPlayer.getName() +
                    " claimed a route " + route.toString();
            game.addToHistory(historyMessage);

            try {
                addGameHistoryToDatabase(game, historyMessage);
            } catch (Exception e) {
                return new ClaimRouteResponse(new Exception("Server database error."));
            }

            // Get color of route to be claimed from train card color
            RouteColors color = null;
            for (TrainCard card : cards.getTrainCards()) {
                if (card.getColor() != RouteColors.Wild) {
                    if (color == null) color = card.getColor();
                }
            }

            //update other clients in the game
            for (ClientProxy client : getClientsInGame(game.getGameId())) {
                client.addToGameHistory(historyMessage);
                // The current client will receive a train card response rather than this command.
                client.addClaimedRoute(route, color, game.getPlayerColor(authToken));
            }

            endTurn(game, currentPlayer.getName());

            return new ClaimRouteResponse(cards); // "Route claimed successfully"
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
            String currentName = game.getCurrentPlayer().getName();
            List<DestinationCard> drawnCards = game.drawDestinationCards(authToken);

            // Add command or checkpoint to database
            if (numGameDeltas < deltasBetweenCheckpoints) {
                ChoiceDestinationCards cards = new ChoiceDestinationCards();
                cards.setDestinationCards(drawnCards);
                // Add a command
                Command command = new Command("drawDestinationCards",
                        new String[]{String.class.getName()},
                        new Object[]{authToken});
                List<String> additionalInfo = new ArrayList<>();
                additionalInfo.add(game.getGameId());
                try {
                    daoFacade.addDelta(command, "game", additionalInfo);
                    numGameDeltas++;
                } catch (Exception e) {
                    return new DestinationCardResponse(new Exception("Server database error."));
                }
            }
            else {
                // Remove all game deltas and replace games
                try {
                    gameCheckpoint();
                } catch (Exception e) {
                    return new DestinationCardResponse(new Exception("Server database error."));
                }
            }

            //update game history
            String historyMessage = AllUsers.getInstance().getUsername(authToken) +
                    " drew destination cards.";
            game.addToHistory(historyMessage);

            try {
                addGameHistoryToDatabase(game, historyMessage);
            } catch (Exception e) {
                return new DestinationCardResponse(new Exception("Server database error."));
            }

            //update other game members
            for (ClientProxy client : getClientsInGame(game.getGameId())) {
                client.addToGameHistory(historyMessage);
            }

            return new DestinationCardResponse(drawnCards);
        }
        catch(Exception ex) {
            return new DestinationCardResponse(ex);
        }
    }

    @Override
    public DestinationCardResponse discardDestinationCard(ChoiceDestinationCards discard, String authToken) {
        try {
            ServerGame game = getGameForToken(authToken);

            ServerPlayer currentPlayer = game.getServerPlayer(authToken);
            // Any reason for failing here will be thrown as an exception
            List<DestinationCard> keptCards = game.discardDestinationCard(discard, authToken);

            // Add command or checkpoint to database
            if (numGameDeltas < deltasBetweenCheckpoints) {
                // Add a command
                Command command = new Command("discardDestinationCard",
                        new String[]{ChoiceDestinationCards.class.getName()},
                        new Object[]{discard});
                List<String> additionalInfo = new ArrayList<>();
                additionalInfo.add(game.getGameId());
                additionalInfo.add(currentPlayer.getName());
                try {
                    daoFacade.addDelta(command, "player", additionalInfo);
                    numGameDeltas++;
                } catch (Exception e) {
                    return new DestinationCardResponse(new Exception("Server database error."));
                }
            }
            else {
                // Remove all game deltas and replace games
                try {
                    gameCheckpoint();
                } catch (Exception e) {
                    return new DestinationCardResponse(new Exception("Server database error."));
                }
            }

            //update game history
            String historyMessage;
            if (discard.getDestinationCards().size() == 2) {
                historyMessage = currentPlayer.getName() +
                        " discarded two destination cards.";
            }
            else if (discard.getDestinationCards().size() == 1) {
                historyMessage = currentPlayer.getName() +
                        " discarded a destination card.";
            }
            else {
                historyMessage = currentPlayer.getName() +
                        " kept all destination cards.";
            }
            game.addToHistory(historyMessage);

            try {
                addGameHistoryToDatabase(game, historyMessage);
            } catch (Exception e) {
                return new DestinationCardResponse(new Exception("Server database error."));
            }

            //update other game members
            for (ClientProxy client : getClientsInGame(game.getGameId())) {
                client.addToGameHistory(historyMessage);
                // The current client will receive a response rather than this command.
                if (!client.getAuthToken().equals(authToken)) {
                    client.addPlayerDestinationCards(currentPlayer.getName(), keptCards.size());
                }
            }

            endTurn(game, currentPlayer.getName());
            return new DestinationCardResponse(keptCards);
        }
        catch(Exception ex) {
            return new DestinationCardResponse(ex);
        }
    }

    //----------------------------------------------------------------------------------------------
    // *** CALLED FROM IN-GAME ***

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
            client.displayEndGame(new GameSummary(playerSummaries));
        }
    }

    //----------------------------------------------------------------------------------------------
    // *** UPDATE CLIENT COMMAND FOR POLLER ***

    @Override
    public ClientUpdate updateClient(String lastReceivedCommandID, String authToken) {
        ClientProxy client = getProxy(authToken);
        if (client == null) return new ClientUpdate(new Exception("You are not an authorized user!"));
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

    private void endTurn(ServerGame game, String playerName) {
        // Update game history
        String historyMessage = playerName + " ended their turn.";
        game.addToHistory(historyMessage);

        // Update other clients in the game
        for (ClientProxy client : getClientsInGame(game.getGameId())) {
            client.addToGameHistory(historyMessage);
            client.endCurrentTurn();
        }
    }

    private void lobbyCheckpoint() throws Exception {
        daoFacade.clearDeltas("lobby");
        List<Lobby> allLobbies = AllLobbies.getInstance().getAllLobbies();
        for (Lobby lobby: allLobbies) {
            daoFacade.removeLobby(lobby.getId());
        }
        daoFacade.addLobbies(allLobbies);
        numLobbyDeltas = 0;
    }

    private void gameCheckpoint() throws Exception {
        daoFacade.clearDeltas("game");
        List<ServerGame> allGames = AllGames.getInstance().getAllGames();
        for (ServerGame game: allGames) {
            daoFacade.removeGame(game.getGameId());
        }
        daoFacade.addGames(allGames);
        numGameDeltas = 0;
    }

    private void addGameHistoryToDatabase(ServerGame game, String message) throws Exception {
        // Add command or checkpoint to database
        if (numGameDeltas < deltasBetweenCheckpoints) {
            // Add a command
            Command command = new Command("addHistoryMessage",
                    new String[]{String.class.getName()},
                    new Object[]{message});
            List<String> additionalInfo = new ArrayList<>();
            additionalInfo.add(game.getGameId());
            daoFacade.addDelta(command, "game", additionalInfo);
            numGameDeltas++;
        }
        else {
            // Remove all game deltas and replace games
            gameCheckpoint();
        }
    }
}
