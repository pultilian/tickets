
package tickets.server.model.game;

import tickets.common.DestinationCard;

import tickets.server.model.game.ServerPlayer.PlayerTurnState;


//package private
class GameLoadingState extends PlayerTurnState {

	GameLoadingState(ServerPlayer player) {
		player.super();
	}

	// Players are currently loading into the game,
	//   so no player actions can be taken

	void drawTrainCard() {
		return;
	}
	void drawFaceUpCard(int position) {
		return;
	}
	void claimRoute(Route route, int numWildCards) {
		return;
	}
	void drawDestinationCard() {
		return;
	}
	void discardDestinationCard(DestinationCard discard) {
		return;
	}
}