
package tickets.server.model.game;

import java.util.List;
import java.util.ArrayList;

import tickets.common.Game;
import tickets.common.TrainCard;
import tickets.common.DestinationCard;

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

	private List<TrainCard> allTrainCards;
	private List<DestinationCard> allDestinationCards;
	private TrainCardArea trainCardArea;
	private DestinationDeck destinationDeck;

	// private List<Route> routes;
	//		-> I'd much prefer if this was a map object that held the cities and routes together
	//		  private Map map;

	public ServerGame(String gameID) {
		super(gameID);
		//set up the game and all cards
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
}