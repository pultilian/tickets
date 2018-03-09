
package tickets.server.model.game;

import tickets.common.DestinationCard;
import tickets.common.Route;

import tickets.server.model.game.ServerPlayer;
import tickets.server.model.game.ServerPlayer.PlayerTurnState;


// Part of the state pattern describing a player's overall turn
//
// At this state, the player has been dealt their starting
//	 hand of train cards and their three destination cards.
//	 The player must now choose which of the destination cards
//	 to discard - or to keep all of them.
// The player then waits for their turn to start.
//
class TurnZeroState extends PlayerTurnState {
	TurnZeroState(ServerPlayer player) {
		player.super();
	}

	@Override
	void state_drawTrainCard() {
		// throw new Exception("You must select starting Destination Cards");
	}

	@Override
	void state_drawFaceUpCard(int position) {

	}

	@Override
	void state_claimRoute(Route route, int numWildCards) {

		// throw new Exception("You must select starting Destination Cards");
	}

	@Override
	void state_claimRoute(Route route) {
		// throw new Exception("You must select starting Destination Cards");
	}

	@Override
	void state_drawDestinationCard() {

		// throw new Exception("You must select starting Destination Cards");
	}

	@Override
	void state_discardDestinationCard(DestinationCard discard) {

		//---
		// Discard the specified destination card.
		// Add the others to the player's hand.
		// End the turn.
		//---
	}

	@Override
	void state_endTurn() {

		//---
		// Add all three destination cards to the player's hand.
		// End the turn
		//---
	}

	@Override
	void state_addToChat(String msg) {

		// add the message to the chat
		// update the player's ClientProxy
		return;
	}
}