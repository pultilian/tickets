
package tickets.server.model.game;

import tickets.common.DestinationCard;

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
	void drawTrainCard() {
		//Player must select starting Destination Cards first
		return;
	}

	@Override
	void drawFaceUpCard(int position) {
		//Player must select starting Destination Cards first
		return;
	}

	@Override
	void claimRoute(Route route, int numWildCards) {
		//Player must select starting Destination Cards first
		return;
	}

	@Override
	void drawDestinationCard() {
		//Player must select starting Destination Cards first
		return;
	}

	@Override
	void discardDestinationCard(DestinationCard discard) {
		//---
		// Discard the specified destination card.
		// End the turn.
		//---
		return;
	}

	@Override
	void endTurn() {
		//---
		// Keep all three starting destination cards.
		// End the turn
		//---
		return;
	}

	@Override
	void addToChat(String msg) {
		// add the message to the chat
		return;
	}
}