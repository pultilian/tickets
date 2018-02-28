
package tickets.server.model;

import java.util.List;

import tickets.common.Player;
import tickets.common.TrainCard;
import tickets.common.DestinationCard;


public class ServerPlayer extends Player {
	//inherited:
	// public Player(String playerId, String associatedAuthToken);
	// public String getPlayerId();
	// public String getAssociatedAuthToken();
	// public Faction getPlayerFaction();
	// public void setPlayerFaction(Faction playerFaction);
	// public HandTrainCard getTrainCards();

//--------------

	private ServerGame game;

	public ServerPlayer(String playerID, String authToken, ServerGame game) {
		super(playerID, authToken);
		this.game = game;
	}


	public void drawTrainCard() {
		return;
	}
	public void drawFaceUpTrainCard(int position) {
		return;
	}
	public void playTrainCards(List<TrainCard> cards) {
		return;
	}
	public void drawDestinationCard() {
		return;
	}
	public void discardDestinationCard(DestinationCard discard) {
		return;
	}
}