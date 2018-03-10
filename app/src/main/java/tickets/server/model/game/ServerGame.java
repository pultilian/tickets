
package tickets.server.model.game;

import java.util.List;
import java.util.ArrayList;

import tickets.common.AllDestinationCards;
import tickets.common.Game;
import tickets.common.RouteColors;
import tickets.common.TrainCard;
import tickets.common.DestinationCard;
import tickets.common.Player;
import tickets.common.Route;

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

	public ServerGame(String gameID, List<Player> playersFromLobby) {
		super(gameID);

		this.players = new ArrayList<>();
		for (Player p : playersFromLobby) {
			//move players into the game
			players.add(new ServerPlayer(p, this));
		}

		List<TrainCard> allTrainCards = initializeTrainCards();
		trainCardArea = new TrainCardArea(allTrainCards);
		destinationDeck = new DestinationDeck(AllDestinationCards.getCards());
		players.get(0).startTurn();
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

	public ServerPlayer getCurrentPlayer() { return players.get(currentPlayerIndex); }

	public String getPlayerID(String authToken) {
		for (ServerPlayer player : players) {
			if (player.getAssociatedAuthToken().equals(authToken)) return player.getPlayerId();
		}
		return null;
	}

	public ServerPlayer getServerPlayer(String authToken) {
		for (ServerPlayer player : players) {
			if (player.getAssociatedAuthToken().equals(authToken)) {
				return player;
			}
		}
		return null;
	}

	public void nextTurn() {
		currentPlayerIndex++;
		if (currentPlayerIndex == players.size()) currentPlayerIndex = 0;
		getCurrentPlayer().startTurn();
	}

	public TrainCard drawTrainCard() {
		return trainCardArea.drawCard();
	}

	public TrainCard drawFaceUpCard(int position) {
		return trainCardArea.drawFaceUpCard(position);
	}

	public boolean isFaceUpCardWild(int position) {
		return trainCardArea.isFaceUpCardWild(position);
	}

	public DestinationCard drawDestinationCard() {
		return destinationDeck.drawCard();
	}

	public boolean discardTrainCard(TrainCard discard) {
		return trainCardArea.discardCard(discard);
	}

	public TrainCardArea getTrainCardArea(){
		return this.trainCardArea;
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
			// public void addTrainCardToHand(TrainCard card);
	    // public void addDestinationCardToHand(DestinationCard card);

		IServerPlayer(Player copy) {
			super(copy);
		}

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
		//	Methods defining actions players can take within the game

		public abstract TrainCard takeAction_drawTrainCard() throws Exception;
		public abstract TrainCard takeAction_drawFaceUpCard(int position) throws Exception;
		public abstract void takeAction_claimRoute(Route route) throws Exception;
		public abstract List<DestinationCard> takeAction_drawDestinationCards() throws Exception;
		public abstract void takeAction_discardDestinationCard(DestinationCard discard) throws Exception;
		public abstract void takeAction_endTurn() throws Exception;

		public abstract void takeAction_addToChat(String message);

		//-------------------------------------------------------------------
		//  Signals the player that it is now their turn
		abstract void startTurn();

		//-------------------------------------------------------------------
		//	These methods provide access to the Game to players

		protected TrainCard drawTrainCard_fromGame() {
			return ServerGame.this.drawTrainCard();
		}

		protected TrainCard drawFaceUpCard_fromGame(int position) {
			return ServerGame.this.drawFaceUpCard(position);
		}

		protected boolean isFaceUpCardWild_fromGame(int position) {
			return ServerGame.this.isFaceUpCardWild(position);
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

		protected DestinationCard drawDestinationCard_fromGame() {
			return ServerGame.this.drawDestinationCard();
		}

		protected void discardDestinationCard_fromGame(DestinationCard discard) {
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

		protected void endTurn_fromGame() {
			ServerGame.this.nextTurn();
			return;
		}

	}


}