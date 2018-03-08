
package tickets.server.model.game;

import tickets.common.DestinationCard;

import tickets.server.model.game.ServerPlayer;
import tickets.server.model.game.ServerPlayer.PlayerTurnState;


// Part of the state pattern describing a player's overall turn
//
// At this state, the player has drawn two destination cards
//	 and must now choose whether to discard one or zero of them.
//
class PickingDestCardsState extends PlayerTurnState {
	PickingDestCardsState(ServerPlayer player) {
		player.super();
	}

	@Override
	void drawTrainCard() {
		// The player has already drawn destination cards
		return;
	}

	@Override
	void drawFaceUpCard(int position) {
		// The player has already drawn destination cards
		return;
	}

	@Override
	void claimRoute(Route route, int numWildCards) {
		// The player has already drawn destination cards
		return;
	}

	@Override
	void drawDestinationCard() {
		// The player has already drawn destination cards
		return;
	}

	@Override
	void discardDestinationCard(DestinationCard discard) {
		//---
		// Discard the specified destination card.
		// Update the game's history
		// End the turn.
		//---
		return;
	}

	@Override
	void endTurn() {
		//---
		// Keep both of the destination cards that were drawn
		// Update the game's history (?)
		// End the turn
		//---
		return;
	}

	@Override
	void addToChat(String msg) {
		//add the message to the chat
		return;
	}
}