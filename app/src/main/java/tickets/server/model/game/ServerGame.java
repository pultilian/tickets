
package tickets.server.model.game;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import tickets.common.AllDestinationCards;
import tickets.common.Game;
import tickets.common.GameMap;
import tickets.common.PlayerColor;
import tickets.common.RouteColors;
import tickets.common.TrainCard;
import tickets.common.DestinationCard;
import tickets.common.Player;
import tickets.common.Route;
import tickets.server.ServerFacade;

public class ServerGame extends Game {

	/*Inherited from Game:
	public Game(String gameId);
	public String getGameId();
	public List<String> getChat();
	public List<String> getGameHistory();
	public void addToChat(String message);
	public void addToHistory(String message);*/

//--------------------------------------------------------------------------------------------------

    //Players are stored in turn order - only index of the current player is stored separately
	private List<ServerPlayer> players;
	private int currentPlayerIndex;

	// The number of players who chose their initial destination cards.
    // Players cannot take any other action until all players are ready.
	private int playersReady;

	private TrainCardArea trainCardArea;
	private DestinationDeck destinationDeck;
	private GameMap map;

	//----------------------------------------------------------------------------------------------
	// *** SET-UP METHODS ***

	public ServerGame(String gameID, String name, List<Player> playersFromLobby) {
		super(gameID, name);

		this.players = new ArrayList<>();
		for (Player p : playersFromLobby) {
			//move players into the game
			players.add(new ServerPlayer(p));
		}

		List<TrainCard> allTrainCards = initializeTrainCards();
		trainCardArea = new TrainCardArea(allTrainCards);
		destinationDeck = new DestinationDeck(AllDestinationCards.getCards());
		initializeAllPlayers();
		map = new GameMap();
		playersReady = 0;
		currentPlayerIndex = 0;
	}

	private List<TrainCard> initializeTrainCards() {
		List<TrainCard> allTrainCards = new ArrayList<>();
		// 12 each of 8 colors, plus 14 wild
		for (int i = 0; i < 12; i++) {
			allTrainCards.add(new TrainCard(RouteColors.Purple));
			allTrainCards.add(new TrainCard(RouteColors.White));
			allTrainCards.add(new TrainCard(RouteColors.Blue));
			allTrainCards.add(new TrainCard(RouteColors.Yellow));
			allTrainCards.add(new TrainCard(RouteColors.Orange));
			allTrainCards.add(new TrainCard(RouteColors.Black));
			allTrainCards.add(new TrainCard(RouteColors.Red));
			allTrainCards.add(new TrainCard(RouteColors.Green));
			allTrainCards.add(new TrainCard(RouteColors.Wild));
		}
		allTrainCards.add(new TrainCard(RouteColors.Wild));
		allTrainCards.add(new TrainCard(RouteColors.Wild));
		return allTrainCards;
	}

    private void initializeAllPlayers() {
        for (ServerPlayer player : players) {
            List<TrainCard> hand = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                hand.add(trainCardArea.drawCard());
            }
            List<DestinationCard> destinations = destinationDeck.drawCards();
            player.initPlayer(hand, destinations);
        }
    }

	//----------------------------------------------------------------------------------------------
    // *** GETTERS ***

    public List<TrainCard> getFaceUpCards() {
	    return new ArrayList<>(Arrays.asList(trainCardArea.getFaceUpCards()));
    }

    public List<ServerPlayer> getServerPlayers() {
	    return players;
    }

    public List<PlayerColor> getPlayersWithLongestPath() {
	    int longestPathSoFar = 0;
        List<PlayerColor> playersWithLongestPath = new ArrayList<>();
        for (ServerPlayer player : players) {
            int playerLongestRoute = player.getLongestRouteLength();
            if (playerLongestRoute > longestPathSoFar) {
                playersWithLongestPath.clear();
                playersWithLongestPath.add(player.getPlayerFaction().getColor());
                longestPathSoFar = playerLongestRoute;
            }
            else if (playerLongestRoute == longestPathSoFar) {
                playersWithLongestPath.add(player.getPlayerFaction().getColor());
            }
        }
	    return playersWithLongestPath;
    }

    public ServerPlayer getServerPlayer(String authToken) {
        for (ServerPlayer player : players) {
            if (player.getAssociatedAuthToken().equals(authToken)) {
                return player;
            }
        }
        return null;
    }

    public Player getPlayerWithName(String name) {
	    for (ServerPlayer serverPlayer : players) {
	        if (serverPlayer.getName().equals(name)) {
	            return new Player(serverPlayer);
            }
        }
        return null;
    }

    public ServerPlayer getCurrentPlayer() {
	    return players.get(currentPlayerIndex);
    }

    public PlayerColor getPlayerColor(String authToken) {
	    for (ServerPlayer player : players) {
	        if (player.getAssociatedAuthToken().equals(authToken)) {
	            return player.getPlayerFaction().getColor();
            }
        }
        return null;
    }

    public GameMap getMap() {
	    return map;
    }

    public Game getClientGame() {
        Game game = new Game(getGameId(), getName());
        for (ServerPlayer player : players) {
            game.addPlayer(player.getInfo());
        }
        game.setMap(map);
        game.setGameHistory(getGameHistory());
        game.setFaceUpCards(getFaceUpCards());
        game.setCurrentTurn(currentPlayerIndex);
        game.setChat(getChat());
        return game;
    }

	//----------------------------------------------------------------------------------------------
    // *** PLAYER ACTIONS ***

	public TrainCard drawTrainCard(String authToken) throws Exception {
	    ServerPlayer player = getServerPlayer(authToken);
	    if (player == null) throw new Exception("You are not a member of this game!");

		TrainCard card = trainCardArea.getTopCard();
		if (card == null) throw new Exception("There are no cards left in the deck.");

		String msg = player.drawTrainCard(card);
		if (msg != null) {
		    if (msg.equals(ServerPlayer.END_TURN)) {
		        startNextTurn();
            }
		    else throw new Exception(msg);
        }
		return trainCardArea.drawCard();
	}

	public TrainCard drawFaceUpCard(int position, String authToken) throws Exception {
        ServerPlayer player = getServerPlayer(authToken);
        if (player == null) throw new Exception("You are not a member of this game!");

        TrainCard card = trainCardArea.getFaceUpCards()[position];
        if (card == null) throw new Exception("There is no card at that position.");

        String msg = player.drawFaceUpCard(card);
        if (msg != null) {
            if (msg.equals(ServerPlayer.END_TURN)) {
                startNextTurn();
            }
            else throw new Exception(msg);
        }
        return trainCardArea.drawFaceUpCard(position);
	}

	public void claimRoute(Route route, List<TrainCard> cards, String authToken) throws Exception {
        ServerPlayer player = getServerPlayer(authToken);
        if (player == null) throw new Exception("You are not a member of this game!");

        // Players cannot claim both routes of a double route if there are fewer than 4 players.
        if (players.size() < 4) {
            if (route.isDouble() && (route.getFirstOwner() != null || route.getSecondOwner() != null))
                throw new Exception("This double route is unavailable (fewer than 4 players)");
        }

        String msg = player.claimRoute(route, cards);
        if (msg != null && !msg.equals(ServerPlayer.LAST_ROUND)) throw new Exception(msg);

        // Success! Now update the server model
        else {
            // Get color of route to be claimed from train card color
            RouteColors color = null;
            for (TrainCard card : cards) {
                player.removeTrainCard(card.getColor());
                if (card.getColor() != RouteColors.Wild) {
                    if (color == null) color = card.getColor();
                }
            }
            map.claimRoute(route.getSrc(), route.getDest(), color, player.getPlayerFaction().getColor());
            startNextTurn();
            // If it's the last round, everyone (including the current player) gets one more turn.
            if (msg != null && msg.equals(ServerPlayer.LAST_ROUND)) player.becomeLastPlayer();
        }
    }

	public List<DestinationCard> drawDestinationCards(String authToken) throws Exception {
        ServerPlayer player = getServerPlayer(authToken);
        if (player == null) throw new Exception("You are not a member of this game!");

        List<DestinationCard> cards = destinationDeck.getTopThreeCards();
        if (cards == null) throw new Exception("There are no destination cards left.");

        String errorMsg = player.drawDestinationCards(cards);
        if (errorMsg != null) throw new Exception(errorMsg);
        else return destinationDeck.drawCards();
	}

	public List<DestinationCard> discardDestinationCard(List<DestinationCard> cards, String authToken) throws Exception {
        ServerPlayer player = getServerPlayer(authToken);
        if (player == null) throw new Exception("You are not a member of this game!");

        String errorMsg = player.discardDestinationCard(cards);
        if (errorMsg != null) throw new Exception(errorMsg);
        else if (cards != null && !destinationDeck.discardCards(cards))
            throw new Exception("This card is already in the destination deck.");

        // Success
        playersReady++;
        // When everyone is ready (at start of game) begin first player's turn.
        if (playersReady == players.size()) {
            players.get(currentPlayerIndex).startTurn();
        }
        else if (playersReady > players.size()) startNextTurn();
        List<DestinationCard> result = new ArrayList<>(player.getDestinationCardOptions());
        player.setDestinationCardOptions(null);
        return result;
    }

    //----------------------------------------------------------------------------------------------
    // *** PRIVATE HELPER METHODS
    private void startNextTurn() {
	    if (players.get(currentPlayerIndex).isLastPlayer()) {
	        ServerFacade.getInstance().endGame(this);
	        return;
        }
	    currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
	    players.get(currentPlayerIndex).startTurn();
    }
}