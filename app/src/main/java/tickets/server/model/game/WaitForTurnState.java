
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
	void state_drawTrainCard() {
		// throw new Exception("It is not your turn");
	}

	@Override
	void state_drawFaceUpCard(int position) {
		// throw new Exception("It is not your turn");
	}

	@Override
	void state_claimRoute(Route route, int numWildCards) {
		// throw new Exception("It is not your turn");
	}

	@Override
	void state_drawDestinationCard() {
		// throw new Exception("It is not your turn");
	}

	@Override
	void state_discardDestinationCard(DestinationCard discard) {
		// throw new Exception("It is not your turn");
	}

	@Override
	void state_endTurn() {
		// throw new Exception("It is not your turn");
	}

	@Override
	void state_addToChat(String msg) {
		// add the message to the chat
		// update the player's ClientProxy
		return;
	}
}