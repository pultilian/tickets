
package tickets.server.model.game;

import tickets.common.DestinationCard;

import tickets.server.model.game.ServerPlayer;
import tickets.server.model.game.ServerPlayer.PlayerTurnState;


//package private
class TurnStartState extends PlayerTurnState {
	TurnStartState(ServerPlayer player) {
		player.super();
	}

	void drawTrainCard() {}
	void drawFaceUpCard(int position) {}
	void claimRoute(Route route, int numWildCards) {}
	void drawDestinationCard() {}
	void discardDestinationCard(DestinationCard discard) {}
}