
package tickets.server.model.game;

import tickets.common.DestinationCard;

import tickets.server.model.game.ServerPlayer;
import tickets.server.model.game.ServerPlayer.PlayerTurnState;


// Part of the state pattern describing a player's overall turn
//
// At this state, the player is waiting for their turn to start.
//	 The player cannot interact with the game, but can still
//	 send chat messages.
//
class WaitForTurnState extends PlayerTurnState {
	WaitForTurnState(ServerPlayer player) {
		player.super();
	}

	@Override
	void drawTrainCard() {
		// It is not your turn
		return;
	}

	@Override
	void drawFaceUpCard(int position) {
		// It is not your turn
		return;
	}

	@Override
	void claimRoute(Route route, int numWildCards) {
		// It is not your turn
		return;
	}

	@Override
	void drawDestinationCard() {
		// It is not your turn
		return;
	}

	@Override
	void discardDestinationCard(DestinationCard discard) {
		// It is not your turn
		return;
	}

	@Override
	void endTurn() {
		// It is not your turn
		return;
	}

	@Override
	void addToChat(String msg) {
		// Add the message to the chat
		return;
	}
}