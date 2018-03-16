
package tickets.server.model.game;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import tickets.common.AllDestinationCards;
import tickets.common.Game;
import tickets.common.RouteColors;
import tickets.common.TrainCard;
import tickets.common.DestinationCard;
import tickets.common.Player;
import tickets.common.Route;

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

	private TrainCardArea trainCardArea;
	private DestinationDeck destinationDeck;

	// private List<Route> routes;
	//		-> I'd much prefer if this was a map object that held the cities and routes together
	//		  private Map map;

	//----------------------------------------------------------------------------------------------
	// *** SET-UP METHODS ***

	public ServerGame(String gameID, List<Player> playersFromLobby) {
		super(gameID);

		this.players = new ArrayList<>();
		for (Player p : playersFromLobby) {
			//move players into the game
			players.add(new ServerPlayer(p));
		}

		List<TrainCard> allTrainCards = initializeTrainCards();
		trainCardArea = new TrainCardArea(allTrainCards);
		destinationDeck = new DestinationDeck(AllDestinationCards.getCards());
		players.get(0).startTurn();
	}

	public void initializeAllPlayers() {
	    for (ServerPlayer player : players) {
            List<TrainCard> hand = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                hand.add(trainCardArea.drawCard());
            }
            List<DestinationCard> destinations = destinationDeck.drawCards();
            player.initPlayer(hand, destinations);
        }
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

	//----------------------------------------------------------------------------------------------
    // *** GETTERS ***

    public List<TrainCard> getFaceUpCards() {
	    return new ArrayList<>(Arrays.asList(trainCardArea.getFaceUpCards()));
    }

    public ServerPlayer getServerPlayer(String authToken) {
        for (ServerPlayer player : players) {
            if (player.getAssociatedAuthToken().equals(authToken)) {
                return player;
            }
        }
        return null;
    }

	//----------------------------------------------------------------------------------------------
    // *** PLAYER ACTIONS ***

	public TrainCard drawTrainCard(String authToken) throws Exception {
	    ServerPlayer player = getServerPlayer(authToken);
	    if (player == null) throw new Exception("You are not a member of this game!");

		TrainCard card = trainCardArea.getTopCard();
		if (card == null) throw new Exception("There are no cards left in the deck.");

		String errorMsg = player.drawTrainCard(card);
		if (errorMsg != null) throw new Exception(errorMsg);
		else return trainCardArea.drawCard();
	}

	public TrainCard drawFaceUpCard(int position, String authToken) throws Exception {
        ServerPlayer player = getServerPlayer(authToken);
        if (player == null) throw new Exception("You are not a member of this game!");

        TrainCard card = trainCardArea.getFaceUpCards()[position];
        if (card == null) throw new Exception("There is no card at that position.");

        String errorMsg = player.drawFaceUpCard(card);
        if (errorMsg != null) throw new Exception(errorMsg);
        else return trainCardArea.drawCard();
	}

	public void claimRoute(Route route, String authToken) throws Exception {
        ServerPlayer player = getServerPlayer(authToken);
        if (player == null) throw new Exception("You are not a member of this game!");

        // TODO: Implement check for route already claimed. Something like the code below
        //if (route.getOwner() != null) throw new Exception("That route is already claimed.");

        String errorMsg = player.claimRoute(route);
        if (errorMsg != null) throw new Exception(errorMsg);
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

	public void discardDestinationCard(DestinationCard card, String authToken) throws Exception {
        ServerPlayer player = getServerPlayer(authToken);
        if (player == null) throw new Exception("You are not a member of this game!");

        String errorMsg = player.discardDestinationCard(card);
        if (errorMsg != null) throw new Exception(errorMsg);
        else if (!destinationDeck.discardCard(card))
            throw new Exception("This card is already in the destination deck.");
    }

    public void nextTurn() {
        players.get(currentPlayerIndex).endTurn();
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        players.get(currentPlayerIndex).startTurn();
    }
}