
package tickets.server.model.game;

import java.util.List;
import java.util.ArrayList;

import tickets.common.Game;
import tickets.common.RouteColors;
import tickets.common.TrainCard;
import tickets.common.DestinationCard;
import tickets.common.Player;

import tickets.server.model.game.ServerPlayer;
import tickets.server.model.game.TrainCardArea;
import tickets.server.model.game.DestinationDeck;

public class ServerGame extends Game {
	//inherited:
	// public Game(String gameId);
	// public String getGameId();
	// public List<String> getChat();
	// public List<String> getGameHistory();
	// public void addToChat(String message);
	// public void addToHistory(String message);

//--------------

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

	public ServerGame(String gameID) {
		super(gameID);
		List<TrainCard> allTrainCards = initializeTrainCards();
		List<DestinationCard> allDestinationCards = initializeDestinationCards();
		trainCardArea = new TrainCardArea(allTrainCards);
		destinationDeck = new DestinationDeck(allDestinationCards);
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

	private List<DestinationCard> initializeDestinationCards() {
		List<DestinationCard> allDestinationCards = new ArrayList<>();
		// TODO: Add in all the destination cards
		return allDestinationCards;
	}

	//----------------------------------------------------------------------------------------------

	public ServerPlayer getCurrentPlayer() { return players.get(currentPlayerIndex); }

	public String getPlayerID(String authToken) {
		for (ServerPlayer player : players) {
			if (player.getAssociatedAuthToken().equals(authToken)) return player.getPlayerId();
		}
		return null;
	}

	public void nextTurn() {
		currentPlayerIndex++;
		if (currentPlayerIndex == players.size()) currentPlayerIndex = 0;
	}

	public TrainCard drawTrainCard() {
		return trainCardArea.drawCard();
	}

	public TrainCard drawFaceUpTrainCard(int position) {
		return trainCardArea.drawFaceUpCard(position);
	}

	public DestinationCard drawDestinationCard() {
		return destinationDeck.drawCard();
	}

	public boolean discardTrainCard(TrainCard discard) {
		return trainCardArea.discardCard(discard);
	}

	public boolean discardDestinationCard(DestinationCard discard) {
		return destinationDeck.discardCard(discard);
	}


	//----------------------------------------------------------------------------------------------
	//	nested abstract class provides an interface for ServerPlayer and
	//	ServerGame to ineract with each other without circular dependencies

	abstract class IServerPlayer extends Player {
			//inherited:
			// public Player(String playerId, String associatedAuthToken);
			// public String getPlayerId();
			// public String getAssociatedAuthToken();
			// public Faction getPlayerFaction();
			// public void setPlayerFaction(Faction playerFaction);
			// public HandTrainCard getTrainCards();

		IServerPlayer(String playerID, String authToken) {
			super(playerID, authToken);
		}

		//   The player class already has this method
		// private void scorePoints(int points) {
		// 	int currentScore = this.getScore();
		// 	this.setScore(currentScore + points);
		// 	return;
		// }


		//-------------------------------------------------------------------
		//	These functions provide access to the Game to players

		protected TrainCard drawTrainCard_fromGame() {
			return ServerGame.this.drawTrainCard();
		}

		protected TrainCard drawFaceUpCard_fromGame(int position) {
			return ServerGame.this.drawFaceUpTrainCard(position);
		}

		protected boolean claimRoute_fromGame(Route route, IServerPlayer player) {
			// Route route = ServerGame.this.getRoute(route, numWildCards);
			// if (! route.isOwned()) {
			//   route.setOwner(player);
			//	 player.scorePoints(route.getLength());
			//	 return true;
			// }
			// else return false;

			return false;
		}

		protected DestinationCard drawDestCard_fromGame() {
			return ServerGame.this.drawDestinationCard();
		}

		protected void discardDestCard_fromGame(DestinationCard discard) {
			ServerGame.this.discardDestinationCard(discard);
			return;
		}

		protected void addToHistory_fromGame(String msg) {
			ServerGame.this.addToHistory(msg);
			return;
		}

		protected void addToChat_fromGame(String msg) {
			ServerGame.this.addToChat(msg);
			return;
		}

	}


}